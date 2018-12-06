package messages;

import java.util.List;

public class AdminQueryMinionLogs extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int logId;
	private int minionId;
	private String messageType;
	private String timestamp;
	private String message;
	private List<AdminQueryMinionLogs> array;
	
	public AdminQueryMinionLogs() {
		
		this.msgType = MessageType.ADMIN_QUERY_MINION_LOGS;
		
	}

	public int getLogId() {
		return logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	public int getMinionId() {
		return minionId;
	}

	public void setMinionId(int minionId) {
		this.minionId = minionId;
	}


	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<AdminQueryMinionLogs> getArray() {
		return array;
	}

	public void setArray(List<AdminQueryMinionLogs> array) {
		this.array = array;
	}


	
	
	
}
