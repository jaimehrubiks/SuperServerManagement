package minion;

import db.DBConnector;
import messages.CmdQuery;
import messages.CmdType;
import messages.Message;
import messages.MessageType;
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
			try {
				socket.sendMessage(m);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("> Logging message.");
			//m.toDatabase(new DBConnector());
			break;
		case MINION_PROCESS_LIST:
			CmdQuery query=(CmdQuery) msg;
			System.out.println("> Minion has been requested Minion Process List");
			query.gatherInformation();
			try {
				socket.sendMessage(query);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			System.out.println("Prueba");
			System.out.println("Unknown message received.");
			break;
		}

		System.out.println("> Ending job");

	}

}
