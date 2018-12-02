package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import db.DBConnector;
import errors.ConnectionException;
import messages.Message;

public abstract class TCPSocket {

	private DBConnector db;

	private static final int port = 8000;
	private static final String hostname = "localhost";
	protected Socket comSocket;
	protected ObjectOutputStream txBuffer;
	protected ObjectInputStream rxBuffer;

	public TCPSocket() throws ConnectionException {
		this(hostname, port);
	}

	public TCPSocket(String hostname, int port) throws ConnectionException {
		try {
			comSocket = new Socket();
			comSocket.connect(new InetSocketAddress(hostname, port), 2000);
			txBuffer = new ObjectOutputStream(comSocket.getOutputStream());
			rxBuffer = new ObjectInputStream(comSocket.getInputStream());
		} catch (IllegalArgumentException iae) {
			throw new ConnectionException("*Port number must be between 0 and 65535, inclusive. Use a port > 1023");
		} catch (UnknownHostException uhe) {
			closeConnection();
			throw new ConnectionException("*IP address could not be determined from given hostname");
		} catch (IOException ioe) {
			closeConnection();
			throw new ConnectionException("*Error connecting to given server. Is server running?");
		} catch (Exception e) {
			closeConnection();
			System.out.println("*UnknownError creating TCP socket");
		}
	}

	public TCPSocket(Socket comSocket) {
		try {
			this.comSocket = comSocket;
			txBuffer = new ObjectOutputStream(comSocket.getOutputStream());
			rxBuffer = new ObjectInputStream(comSocket.getInputStream());
		} catch (UnknownHostException uhe) {
			System.out.println(uhe.toString());
			closeConnection();
		} catch (IOException ioe) {
			System.out.println(ioe.toString());
			closeConnection();
		}
	}

	public Message receiveMessage() {
		Message m = null;
		try {
			m = (Message) rxBuffer.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return m;
	}
	
	public void sendMessage(Message m) throws Exception {
		txBuffer.writeObject(m);
		txBuffer.flush();
	}

	public final void closeConnection() {
		try {
			if (rxBuffer != null)
				rxBuffer.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		try {
			if (txBuffer != null)
				txBuffer.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		try {
			if (comSocket != null)
				comSocket.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	public void checkConnection() throws ConnectionException {
		boolean check = comSocket.isClosed();

		if (check == true) {
			closeConnection();
			throw new ConnectionException("Connection is closed");
		}

	}

}
