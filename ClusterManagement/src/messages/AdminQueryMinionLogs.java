package messages;

public class AdminQueryMinionLogs extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int logId;
	private int minionId;
	private String msgType;
	private String timestamp;
	private String message;
	private AdminQueryMinionLogs[] array;
	
	public AdminQueryMinionLogs() {
		
		
		
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
		return msgType;
	}

	public void setMessageType(String msgType) {
		this.msgType = msgType;
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

	public AdminQueryMinionLogs[] getArray() {
		return array;
	}

	public void setArray(AdminQueryMinionLogs[] array) {
		this.array = array;
	}
	
	
	
}
