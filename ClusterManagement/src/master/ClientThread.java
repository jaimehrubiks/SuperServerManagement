package master;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import messages.Message;

public class ClientThread implements Runnable {

	public static final int listener = 0;
	public static final int worker = 1;

	private static final int port = 8000;

	private TCPClientListener socket;
	private int mode;

	public ClientThread() {
		this.mode = listener;
	}

	public ClientThread(Socket accept) {
		this.mode = worker;
		socket = new TCPClientListener(accept);
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
		
		boolean listening = true;

		while(listening) {
			
			/*
			 * En realidad, en la del client, no habría un bucle, sólo se hace lo siguiente:
			 * 1. recibir mensaje
			 * 2. pedir mensaje al minion
			 * 3. recibir mensaje
			 * 4. responder mensaje
			 * 
			 * 
			 */
			try {
				System.out.println("Listening for a new message.");
				Message m = socket.receiveMessage();
				System.out.println("Got message: " + m.toString());
				System.out.println(m.getMsgType());
			} catch (Exception e) {
				listening = false;
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
