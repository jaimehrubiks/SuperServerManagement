package messages;

public class AdminQueryUserLogs {

	private int logId;
	private int userId;
	private String messageType;
	private String timestamp;
	private String message;
	private AdminQueryUserLogs[] array;
	
	public AdminQueryUserLogs() {
		
	}

	public int getLogId() {
		return logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public AdminQueryUserLogs[] getArray() {
		return array;
	}

	public void setArray(AdminQueryUserLogs[] array) {
		this.array = array;
	}
	
	
}
