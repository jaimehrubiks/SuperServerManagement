package master;

import java.net.Socket;

import db.DBConnector;
import messages.Message;
import network.TCPSocket;

public class TCPMinionListener extends TCPSocket {

	public final static int port = 8001;
	DBConnector dbcon;

	public TCPMinionListener(Socket socket) {
		super(socket);
		dbcon = new DBConnector();
	}

	@Override
	public Message receiveMessage() {
		Message m = super.receiveMessage();
		m.toDatabase(dbcon, "minion");
		return m;
	}

	public String getPublicIp() {
		return comSocket.getRemoteSocketAddress().toString();
	}

}
