package master;

import java.io.IOException;
import java.net.Socket;

import errors.ConnectionException;
import messages.Message;
import network.TCPSocket;

public class TCPMinionListener extends TCPSocket {

	private static final int port = 8001;

	public TCPMinionListener(Socket socket) throws ConnectionException {
		super(socket);
	}

//	@Override
//	public Message receiveMessage() {
//		try {
//			Message m = (Message) rxBuffer.readObject();
//			System.out.println("Message received: " + m.toString());
//		} catch (ClassNotFoundException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}

}
