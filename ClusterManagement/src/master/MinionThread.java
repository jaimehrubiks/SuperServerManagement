package master;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import messages.Message;
import messages.MessageType;
import messages.MinionRegister;
import messages.MinionLogin;

public class MinionThread implements Runnable {

	public static final int listener = 0;
	public static final int worker = 1;

	private static final int port = 8001;

	private TCPMinionListener socket;
	private int mode;

	public MinionThread() {
		this.mode = listener;
	}

	public MinionThread(Socket accept) {
		this.mode = worker;
		socket = new TCPMinionListener(accept);
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
		
	
	}
	
	private void minionRegister(MinionRegister message) {
		// crear random
		String pwd = String.valueOf(Math.random());
		// insertar en la base de datos un minion y recoger el id (primary auto increment)
		int id = 3; // TODO cambiar
		// insertar en el mensaje la password
		message.setMinionId(id);
		message.setMinionCode(pwd);
		// devolver el mensaje
		socket.sendMessage(message);
	}
	
	private void minionLogin(MinionLogin message) {
		// comprobar en la base de datos (where id = y where password =
		message.setOk(true);
		// y si está mal message.setOk(false);
		socket.sendMessage(message);
	}
	
}
