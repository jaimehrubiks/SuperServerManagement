package master;

import java.net.Socket;

import network.TCPSocket;

public class TCPMinionListener extends TCPSocket {

	public final static int port = 8001;

	//public TCPClientListener() throws ConnectionException {
		//super(port);
	//}
	
	public TCPMinionListener(Socket socket) {
		super(socket);
	}
	
	public String getPublicIp() {
		return comSocket.getRemoteSocketAddress().toString();
	}

//	@Override
//	public Message receiveMessage() throws Exception {
//		Message m = null;
//		try {
//			m = (Message) rxBuffer.readObject();
//			return m;
//		} catch (ClassNotFoundException | IOException e) {
//			throw e;
//		}
//	}

}
