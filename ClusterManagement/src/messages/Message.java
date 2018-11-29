package messages;
import java.io.Serializable;
import java.util.Date;

import db.DBConnector;

public class Message implements Loggable, Serializable{
	
	private static final long serialVersionUID = 1L;

	private Date date;
	private String minionCode;

	protected int minionId;
	protected MessageType msgType;
	
	public Message() {
		date = new Date();
		msgType = MessageType.MESSAGE;
	}
	
	@Override
	public void toDatabase(DBConnector db){
		
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getMinionId() {
		return minionId;
	}

	public void setMinionId(int minionId) {
		this.minionId = minionId;
	}

	public String getMinionCode() {
		return minionCode;
	}

	public void setMinionCode(String minionCode) {
		this.minionCode = minionCode;
	}

	public MessageType getMsgType() {
		return msgType;
	}

	public void setMsgType(MessageType msgType) {
		this.msgType = msgType;
	}
	
	@Override
	public String toString() {
		String s = String.format("Message\n--------\nDate: %s\nMessage Type: %s\nMinion ID: %s\nMinion PWD: %s\n--------\n", 
				date, msgType, minionId, minionCode);
		return s;
	}
	
	
}
