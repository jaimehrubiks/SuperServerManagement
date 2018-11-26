package messages;
import java.util.Date;

import db.DBConnector;

public class Message implements Loggable{
	
	private Date date;
	private int minionId;
	private String minionCode;
	private MessageType msgType;
	
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
	
	
}
