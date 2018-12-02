package master;

import java.util.HashMap;

public class Master {

	//private TCPListener tcplistener;
	MasterOptions mo;
	
	public static HashMap<Integer, MinionThread> connectedMinions;

	public Master() {
		connectedMinions = new HashMap<>();
		launchClientThread();
		launchMinionThread();
	}

	public void launchClientThread() {
        Thread t = new Thread(new ClientThread());
        t.start();
	}
	
	public void launchMinionThread() {
        Thread t = new Thread(new MinionThread());
        t.start();
	}
	

}
