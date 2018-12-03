package master;

import java.util.concurrent.ConcurrentHashMap;

public class Master {

	//private TCPListener tcplistener;
	MasterOptions mo;
	
	public static ConcurrentHashMap<Integer, MinionThread> connectedMinions;

	public Master() {
		connectedMinions = new ConcurrentHashMap<>();
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
