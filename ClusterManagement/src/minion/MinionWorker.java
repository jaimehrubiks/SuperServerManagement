package minion;

import db.DBConnector;
import messages.Message;
import messages.UserQueryMinionBasicInfo;

public class MinionWorker implements Runnable {
	
	private Message msg;
	private TCPMinionClient socket;
	private MinionStorage ms;

	public MinionWorker(Message receiveMessage, TCPMinionClient socket, MinionStorage ms) {
		System.out.println("Thread object is spawned. Now it should run().");
		this.socket = socket;
		this.ms = ms;
		msg = receiveMessage;
	}

	@Override
	public void run() {
		
		System.out.println("A message has been received.");
		
		switch(msg.getMsgType()) {
			case USER_QUERY_BASICINFO:
				UserQueryMinionBasicInfo m = (UserQueryMinionBasicInfo) msg;
				System.out.println("> Minion has been requested UserQueryMinionBasicInfo");
				m.gatherInformation();
				System.out.println("Inserting minion code into message: ");
				System.out.println(ms.getMinionCode());
				m.setMinionCode(ms.getMinionCode());
				System.out.println("> Sending response back.");
				socket.sendMessage(m);
				//System.out.println("> Logging message.");
				//m.toDatabase(new DBConnector());
				break;
			default:
				System.out.println("Unknown message received.");
				break;
		}
		
		System.out.println("> Ending job");
		
	}

}
