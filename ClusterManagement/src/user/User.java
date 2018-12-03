package user;

import db.DBConnector;
import errors.ConnectionException;
import messages.CmdQuery;
import messages.CmdType;
import messages.Message;
import messages.MessageType;
import messages.UserQueryMinionBasicInfo;
import messages.UserQueryMinionList;

public class User {

	//private GUI gui;
	TCPUserClient socket;

	public User() {
		//Test();
	}

	public void newConnection() {
		if(socket != null) {
			socket.closeConnection();
		}
		try {
			socket = new TCPUserClient("localhost", 8000);
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public UserQueryMinionList getMinionList() {

		newConnection();
		// TEST 1: GET MINION LIST
		System.out.println("Testing command: UserQueryMinionList");
		UserQueryMinionList query1 = new UserQueryMinionList();
		try {
			socket.sendMessage(query1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Message send. Waiting for response.");
		Message message1 = socket.receiveMessage();
		if(message1.getMsgType() != MessageType.USER_QUERY_MINIONLIST) {
			System.out.println("Error receiving message.");
			System.exit(1);
		}
		query1 = (UserQueryMinionList) message1;
		System.out.println("> Logging message...");
//		query1.toDatabase(new DBConnector());
		System.out.println("Correct Answer received: ");
		System.out.println(query1.toString());
		return query1;
	}
	public CmdQuery getProcessList(int id) {
		newConnection();
		System.out.println("Testing command: ProcessList");
		CmdQuery query=new CmdQuery(CmdType.MINION_PROCESS_LIST,MessageType.MINION_PROCESS_LIST,id);
		try {
			socket.sendMessage(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Message send. Wainting for response");
		Message message2=socket.receiveMessage();
		if (message2.getMsgType()!=MessageType.MINION_PROCESS_LIST) {
			System.out.println("Erro receiving message");
			
		}
		query = (CmdQuery) message2;
		System.out.println("Correct Answer received: ");
		return query;
	}
	public UserQueryMinionBasicInfo getBasicInfo(int id) {
		newConnection();
		System.out.println("Testing command: UserQueryMinionBasicInfo");
		UserQueryMinionBasicInfo query2 = new UserQueryMinionBasicInfo(id);
		System.out.println("> Logging message...");
//		query2.toDatabase(new DBConnector());
		try {
			socket.sendMessage(query2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Message send. Waiting for response.");
		Message message2 = socket.receiveMessage();
		if(message2.getMsgType() != MessageType.USER_QUERY_BASICINFO) {
			System.out.println("Error receiving message.");
			System.exit(1);
		}
		query2 = (UserQueryMinionBasicInfo) message2;
		System.out.println("Correct Answer received: ");
		System.out.println(query2.toString());
		return query2;

	}
	public void Test() { // TEST FUNCTION. ONLY FOR TESTING.
		System.out.println("Running user in test mode");

		newConnection();
		// TEST 1: GET MINION LIST
		System.out.println("Testing command: UserQueryMinionList");
		UserQueryMinionList query1 = new UserQueryMinionList();
		try {
			socket.sendMessage(query1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Message send. Waiting for response.");
		Message message1 = socket.receiveMessage();
		if(message1.getMsgType() != MessageType.USER_QUERY_MINIONLIST) {
			System.out.println("Error receiving message.");
			System.exit(1);
		}
		query1 = (UserQueryMinionList) message1;
		System.out.println("Correct Answer received: ");
		System.out.println(query1.toString());

		newConnection();
		//		// TEST 2: GET BASIC INFO FROM LATEST MINION ON THE LIST
		System.out.println("Testing command: UserQueryMinionBasicInfo");
		UserQueryMinionBasicInfo query2 = new UserQueryMinionBasicInfo(query1.getMinionList().get(query1.getMinionList().size()-1));
		try {
			socket.sendMessage(query2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Message send. Waiting for response.");
		Message message2 = socket.receiveMessage();
		if(message2.getMsgType() != MessageType.USER_QUERY_BASICINFO) {
			System.out.println("Error receiving message.");
			System.exit(1);
		}
		query2 = (UserQueryMinionBasicInfo) message2;
		System.out.println("Correct Answer received: ");
		System.out.println(query2.toString());

	}

}
