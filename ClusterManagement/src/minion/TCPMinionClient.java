package minion;

import errors.ConnectionException;
import messages.CmdType;
import messages.ExtraInfoType;
import network.TCPSocket;

public class TCPMinionClient extends TCPSocket{
	
	public TCPMinionClient(String hostname, int port) throws ConnectionException {
		super(hostname, port);
	}

	public void getMinionList() {
		
	}
	
	public void updateMinion(int minionId) {
		
	}
	
	public void query(ExtraInfoType et, int minionId) {
		
	}
	
	public void cmd(CmdType cmdt, int minionId) {
		
	}

//	@Override
//	public Message receiveMessage() throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
}
