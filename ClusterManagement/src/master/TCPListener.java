package master;

import db.DBConnector;

public abstract class TCPListener {
	
	private DBConnector db;
	
	public abstract void receiveMessage();
	

}
