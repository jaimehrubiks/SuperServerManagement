package master;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import db.DBModel;
import messages.Message;
import messages.MessageType;
import messages.UserQueryMinionBasicInfo;
import messages.UserQueryMinionList;

public class ClientThread implements Runnable {

	public static final int listener = 0;
	public static final int worker = 1;

	private static final int port = 8000;

	private TCPClientListener socket;
	private int mode;
	private DBModel db;

	public ClientThread() {
		this.mode = listener;
	}

	public ClientThread(Socket accept) {
		this.mode = worker;
		socket = new TCPClientListener(accept);
		db = new DBModel("ssm_minions");
	}

	@Override
	public void run() {

		if (mode == ClientThread.listener) {
			listener();
		} else if (mode == ClientThread.worker) {
			worker();
		}

	}

	public void listener() {
		boolean listening = true;

		try (ServerSocket serverSocket = new ServerSocket(ClientThread.port)) {

			while (listening) {
				Thread t = new Thread(new ClientThread(serverSocket.accept()));
				t.start();
			}

		} catch (IOException e) {
			System.err.println("Could not listen on port " + ClientThread.port
					+ " .Check port availability and internet connection");
			System.exit(-1);
		}
	}

	public void worker() {

		// Get new message
		Message m = socket.receiveMessage();
		System.out.println("User connected.");

		// Check message type and take actions
		switch (m.getMsgType()) {
		case USER_QUERY_MINIONLIST:
			queryMinionList((UserQueryMinionList) m);
			break;
		case USER_QUERY_BASICINFO:
			queryMinionBasicInfo((UserQueryMinionBasicInfo) m);
			break;
		default:
			break;
		}

		// Close connection
		System.out.println("User disconnects.");
		socket.closeConnection();

	}

	private void queryMinionList(UserQueryMinionList m) {
		System.out.println("> User asking for user minion list");
		m.setMinionList(db.getMinionList());
		System.out.println("> Sending this message back:");
		System.out.println(m.toString());
		try {
			socket.sendMessage(m);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void queryMinionBasicInfo(UserQueryMinionBasicInfo m) {

		System.out.println("A user has requested queryBasicMinionInfo.");


		MinionThread mt = null;
		try {
			TCPMinionListener minionTCP;
			if (Master.connectedMinions.containsKey(m.getMinionId())) {
				System.out.println("Minion is online.");
				mt = Master.connectedMinions.get(m.getMinionId());
				minionTCP = mt.getTCP();
				System.out.println("Sending message to minion.");
				minionTCP.sendMessage(m);
				System.out.println("Waiting for the minion's response.");
				Message ans = minionTCP.receiveMessage();
				if (ans.getMsgType() == MessageType.USER_QUERY_BASICINFO) {
					System.out.println("Message received from minion.");
					m = (UserQueryMinionBasicInfo) ans;
					System.out.println(m.toString());
					m.setPublicIP(minionTCP.getPublicIp());
					boolean result = db.saveMinionBasicInfo(m);
					if (result) {
						System.out.println("Auth OK. Data saved to db.");
						m.setOnline(true);
					} else {
						System.out.println("Auth not OK. Data not saved to db.");
						m.setOnline(false);
					}
				} else {
					return;
				}
			} else {
				System.out.println("Minion is not online.");
				m.setOnline(false);
			}
		} catch (Exception e) {
			System.out.println("Minion no longer is connected.");
			if(mt!=null)
				mt.die();
			m.setOnline(false);
		}

		//
		boolean exists = db.getMinionBasicInfo(m);
		if (exists) {
			try {
				socket.sendMessage(m);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	
}
