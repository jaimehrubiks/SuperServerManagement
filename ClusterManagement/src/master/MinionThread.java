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
		db = new DBModel("superservermanagement");
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
		
		Message m = socket.receiveMessage();
		
		if(m.getMsgType() == MessageType.MINION_REGISTER) {
			MinionRegister message = (MinionRegister) m;
			minionRegister(message);
		}
		else if(m.getMsgType() == MessageType.MINION_LOGIN) {
			MinionLogin message = (MinionLogin) m;
			minionLogin(message);
		}
		
		// TODO: EN LOS DEMÁS, HAY QUE ELIMINAR EL MINION CODE ANTES DE DEVOLVER EL MENSAJE AL USER
		
	
	}
	
	private void minionRegister(MinionRegister message) {
		// crear random
		String minionCode = String.valueOf(Math.random());
		// insertar en la base de datos un minion y recoger el id (primary auto increment)
		int minionId = db.newMinion(minionCode);
		// insertar en el mensaje la password
		if(minionId > 0) {
			message.setMinionId(minionId);
			message.setMinionCode(minionCode);
			message.setCorrect(true);
		} // If < 0 something wrong happened. correct bit will be false
		// devolver el mensaje
		socket.sendMessage(message);
	}
	
	private void minionLogin(MinionLogin message) {
		boolean ok = db.minionLogin(message.getMinionId(), message.getMinionCode());
		message.setOk(ok);
		socket.sendMessage(message);
		if(ok) {
			Master.connectedMinions.put(message.getMinionId(), this);
			working();
		}
	}
	
	private void working() {
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
