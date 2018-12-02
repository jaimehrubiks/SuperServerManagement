package master;

import java.net.Socket;

import db.DBConnector;
import messages.Message;
import network.TCPSocket;

public class TCPClientListener extends TCPSocket {

	public final static int port = 8000;
	DBConnector dbcon;

	public TCPClientListener(Socket socket) {
		super(socket);
		dbcon = new DBConnector();
	}

	@Override
	public Message receiveMessage() {
		Message m = super.receiveMessage();
		m.toDatabase(dbcon, "user");
		return m;
	}

}
