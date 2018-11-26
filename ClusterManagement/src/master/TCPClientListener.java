package master;

import java.io.IOException;
import java.net.Socket;

import errors.ConnectionException;
import messages.Message;
import network.TCPSocket;

public class TCPClientListener extends TCPSocket {

	public final static int port = 8000;

	//public TCPClientListener() throws ConnectionException {
		//super(port);
	//}
	
	public TCPClientListener(Socket socket) {
		super(socket);
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
