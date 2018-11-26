package user;

import errors.ConnectionException;
import messages.Message;

public class User {

	private GUI gui;
	TCPUserClient socket;
	
	public User() {
		try {
			socket = new TCPUserClient("localhost", 8000);
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Test() {
		System.out.println("Running user in test mode");
		Message m = new Message();
		socket.sendMessage(m);
	}
	
}
