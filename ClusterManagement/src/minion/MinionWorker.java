package minion;

import messages.Message;
import messages.UserQueryMinionBasicInfo;

public class MinionWorker implements Runnable {
	
	private Message msg;
	private TCPMinionClient socket;

	public MinionWorker(Message receiveMessage, TCPMinionClient socket) {
		System.out.println("Thread object is spawned. Now it should run().");
		this.socket = socket;
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
				System.out.println("> Sending response back.");
				socket.sendMessage(m);
				break;
			default:
				System.out.println("Unknown message received.");
				break;
		}
		
		System.out.println("> Ending job");
		
	}

}
