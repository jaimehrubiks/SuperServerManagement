package messages;

import java.io.Serializable;
import java.util.ArrayList;

public class UserQueryMinionList extends Message implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//TODO: DATOS PARA METER MINIONS, de momento:
	ArrayList minionList;
	
	public UserQueryMinionList() {
		this.msgType = MessageType.USER_QUERY_MINIONLIST;
		//minionList = new ArrayList();
	}
	
	public void setMinionList(ArrayList minionList) {
		this.minionList = minionList;
	}

}
