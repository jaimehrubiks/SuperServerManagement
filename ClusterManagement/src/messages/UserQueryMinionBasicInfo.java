package messages;

import java.io.Serializable;

public class UserQueryMinionBasicInfo extends Message implements Serializable, MinionGatherable {
	
	private String CPU;
	private String IP;
	private String RAM;
	private String publicIP;
	private String hostName;
	private String tag;
	
	public UserQueryMinionBasicInfo(int id) {
		this.msgType = MessageType.USER_QUERY_BASICINFO;
		this.minionId = id;
	}

	@Override
	public void gatherInformation() {
		// TODO: IMPLEMENT THIS METHOD
		CPU = "56%";
		IP = "192.168.0.1";
		RAM = "45434M";
		hostName = "hostname";
		//tag = "servidor de amazon"; este en realidad seria en otra funcion aparte, lo mete el master, no el minion.
	}
	
	public void setPublicIP(String publicIP) {
		this.publicIP = publicIP;
	}
	

}
