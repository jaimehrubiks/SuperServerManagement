package master;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

import master.MasterOptions;
import network.TCPSocket;

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
