package master;

import java.io.IOException;
import java.net.ServerSocket;

import master.MasterOptions;
import network.TCPSocket;

public class Master {

	//private TCPListener tcplistener;
	MasterOptions mo;

	public Master() {
		launchClientThread();
	}

	public void launchClientThread() {
        Thread t = new Thread(new ClientThread());
        t.start();
	}
	
	

}
