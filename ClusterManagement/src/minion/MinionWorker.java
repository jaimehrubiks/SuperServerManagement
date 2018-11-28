package minion;

import messages.Message;

public class MinionWorker implements Runnable {
	
	private Message msg;

	public MinionWorker(Message receiveMessage) {
		msg = receiveMessage;
	}

	@Override
	public void run() {
		
		System.out.println("Working on a received message.");
		// check message type
		// cast message
		// do operations (usually, it will be just a call to msg.gatherInformation()), it can also be nothing (ex. keep alive/echo)
		// send message back
		// nothing (let this thread die)
		System.out.println("Ending job.");
		
		
		// de momento implementar un if para userqueryminionbasicinfo, y para keepalive

	}

}
