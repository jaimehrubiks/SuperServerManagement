package master;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import db.DBModel;
import messages.Message;
import messages.MessageType;
import messages.MinionRegister;
import messages.MinionLogin;

public class MinionThread implements Runnable {

	public static final int listener = 0;
	public static final int worker = 1;

	private static final int port = 8001;

	private TCPMinionListener socket;
	private DBModel db;
	private int mode;
	private boolean working = false;

	public MinionThread() {
		this.mode = listener;
	}

	public MinionThread(Socket accept) {
		this.mode = worker;
		socket = new TCPMinionListener(accept);
		db = new DBModel("minions");
	}

	@Override
	public void run() {

		if (mode == MinionThread.listener) {
			listener();
		} else if (mode == MinionThread.worker) {
			worker();
		}

	}

	public void listener() {
		boolean listening = true;

		try (ServerSocket serverSocket = new ServerSocket(MinionThread.port)) {

			while (listening) {
				Thread t = new Thread(new MinionThread(serverSocket.accept()));
				t.start();
			}

		} catch (IOException e) {
			System.err.println("Could not listen on port " + MinionThread.port
					+ " .Check port availability and internet connection");
			System.exit(-1);
		}
	}

	public void worker() {
		
		System.out.println("A new minion is trying to connect.");
		Message m = socket.receiveMessage();
		
		if(m.getMsgType() == MessageType.MINION_REGISTER) {
			MinionRegister message = (MinionRegister) m;
			minionRegister(message);
		}
		else if(m.getMsgType() == MessageType.MINION_LOGIN) {
			MinionLogin message = (MinionLogin) m;
			minionLogin(message);
		}
		socket.closeConnection();
		
		// TODO: EN LOS DEMÁS, HAY QUE ELIMINAR EL MINION CODE ANTES DE DEVOLVER EL MENSAJE AL USER
		
	
	}
	
	private void minionRegister(MinionRegister message) {
		System.out.println("Minion register message received.");
		System.out.println(message.toString());
		// crear random
		String minionCode = String.valueOf(Math.random());
		// insertar en la base de datos un minion y recoger el id (primary auto increment)
		int minionId = db.newMinion(minionCode);
		// insertar en el mensaje la password
		if(minionId > 0) {
			message.setMinionId(minionId);
			message.setMinionCode(minionCode);
			message.setCorrect(true);
			System.out.println("Minion registered correctly. Sending message back.");
		} // If < 0 something wrong happened. correct bit will be false
		else {
			System.out.println("Minion registration failed. Server error. Sending message with correct bit to false.");
		}
		// devolver el mensaje
		socket.sendMessage(message);
		System.out.println("Message Sent. Closing connection.");
	}
	
	private void minionLogin(MinionLogin message) {
		System.out.println("Minion login message received.");
		System.out.println(message.toString());
		boolean ok = db.minionLogin(message.getMinionId(), message.getMinionCode());
		message.setOk(ok);
		if(ok) {
			System.out.println("Login is successful.");
			Master.connectedMinions.put(message.getMinionId(), this);
			System.out.println("Replying back.");
			socket.sendMessage(message);
			System.out.println("Message sent.");
			working();
		}else {
			System.out.println("Login unsuccessful.");
			socket.sendMessage(message);
		}
	}
	
	private void working() {
		System.out.println("A minion is now connected. Thread sleeping.");
		working = true;
		// Sleeping until some event happens (like finishing this thread)
		while(working) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Alternatively, we could just empty this method, and delete this thread, as it is not
		//  necessary for the minion (in that case we would need to change "this" by other object, in minionLogin method)
		
	}
	
	public TCPMinionListener getTCP() {
		return socket;
	}
	
}
