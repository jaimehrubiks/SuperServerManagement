package messages;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DBConnector;

public class MinionLogin extends Message implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	private boolean ok;
	
	public MinionLogin() {
		this.msgType = MessageType.MINION_LOGIN;
		this.ok = false;
	}

	
	/**
	 * @return the ok
	 */
	public boolean isOk() {
		return ok;
	}

	/**
	 * @param ok the ok to set
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	
	public String toString() {
		String s = String.format(">Ok: %b\n", ok);
		return super.toString()+s;
	}

}
