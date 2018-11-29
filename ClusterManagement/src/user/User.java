package user;

import errors.ConnectionException;
import messages.Message;
import messages.MessageType;
import messages.UserQueryMinionList;

public class User {

	private GUI gui;
	TCPUserClient socket;
	
	public User() {
		try {
			socket = new TCPUserClient("localhost", 8000);
			Test();
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Test() { // TEST FUNCTION. ONLY FOR TESTING.
		System.out.println("Running user in test mode");
		System.out.println("Testing command: UserQueryMinionList");
		UserQueryMinionList query1 = new UserQueryMinionList();
		socket.sendMessage(query1);
		System.out.println("Message send. Waiting for response.");
		Message message1 = socket.receiveMessage();
		if(message1.getMsgType() != MessageType.USER_QUERY_MINIONLIST) {
			System.out.println("Error receiving message.");
			System.exit(1);
		}
		query1 = (UserQueryMinionList) message1;
		System.out.println("Correct Answer received: ");
		System.out.println(message1.toString());
		
	}
	
}
