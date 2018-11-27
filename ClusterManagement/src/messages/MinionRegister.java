package messages;

import java.io.Serializable;

public class MinionRegister extends Message implements Serializable {
	
	public MinionRegister() {
		this.msgType = MessageType.MINION_REGISTER;
	}

}
