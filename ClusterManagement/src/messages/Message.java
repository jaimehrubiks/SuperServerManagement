package messages;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import db.DBConnector;
import db.DBModel;

public class Message implements Loggable, Serializable{
	
	private static final long serialVersionUID = 1L;

	private Date date;
	private String minionCode;

	protected int minionId;
	protected int userId;
	protected MessageType msgType;
	
	public Message() {
		date = new Date();
		msgType = MessageType.MESSAGE;
	}
	
	@Override
	public void toDatabase(DBConnector db, String sender) {
		
		if(sender == "minion") {
			try {
				
				System.out.println("Connecting to DB to log a [Minion] message...");
				System.out.println("Minion ID: " + this.minionId);
				System.out.println("Date: " + this.getDate().toString());
				System.out.println("Message Type: " + this.msgType.toString());

				Connection con = db.connect();
				String databaseName = "ssm_logs_minion";
				PreparedStatement ps = con.prepareStatement("INSERT INTO " + databaseName + "(minionId, messageType, date, message) VALUES(?,?,?,?)");
				if(this.minionId==0)
					ps.setObject(1, null);
				else
					ps.setInt(1, this.minionId);
				ps.setString(2, this.msgType.toString());
				ps.setString(3, this.getDate().toString());
				ps.setString(4, this.toString());
				ps.executeUpdate();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error logging message to database: " + e.getMessage());
			}
		}
		else if (sender == "user"){
			try {
				
				System.out.println("Connecting to DB to log a [User] message...");
				System.out.println("Date: " + this.getDate().toString());
				System.out.println("Message Type: " + this.msgType.toString());
				System.out.println("Message: " + this.toString());
				
				Connection con = db.connect();
				String databaseName = "ssm_logs_user";
				PreparedStatement ps = con.prepareStatement("INSERT INTO " + databaseName + " (userId, messageType, date, message) VALUES(?,?,?,?)");
				if(this.userId==0)
					ps.setObject(1, null);
				else
					ps.setInt(1, this.userId);
				ps.setString(2, this.msgType.toString());
				ps.setString(3, this.getDate().toString());
				ps.setString(4, this.toString());
				ps.executeUpdate();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error logging message to database: " + e.getMessage());
			}
		}
		
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
