package messages;

import java.util.List;

public class AdminQueryUserLogs extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int logId;
	private int userId;
	private String messageType;
	private String timestamp;
	private String message;
	private List<AdminQueryUserLogs> array;
	
	public AdminQueryUserLogs() {
		
		this.msgType = MessageType.ADMIN_QUERY_USER_LOGS;
		
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

	public List<AdminQueryUserLogs> getArray() {
		return array;
	}

	public void setArray(List<AdminQueryUserLogs> array) {
		this.array = array;
	}


	
}
