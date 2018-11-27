package messages;

import java.io.Serializable;

public class MinionLogin extends Message implements Serializable {
	
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

}
