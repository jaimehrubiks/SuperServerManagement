package messages;

public class UserLogin extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private boolean isOk;
	private boolean isAdmin;

	public UserLogin(String user, String pass) {
		
		this.setMsgType(MessageType.USER_LOGIN);
		this.username = user;
		this.password = pass;
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}
	
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public String toString() {
		String response = "Login Message\n-------\nDate: " + this.getDate() + "\nMessage Type: " + this.getMsgType() + "\nUser Id: " + this.getUserId() + "\nLogin Status: " + this.isOk() + "\nAdmin Status" + this.isAdmin();
		return response;
		
	}
	
}
