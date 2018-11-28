package messages;

import java.io.Serializable;

public class MinionRegister extends Message implements Serializable {
	
	private boolean correct = false;
	
	public MinionRegister() {
		this.msgType = MessageType.MINION_REGISTER;
	}

	/**
	 * @return the correct
	 */
	public boolean isCorrect() {
		return correct;
	}

	/**
	 * @param correct the correct to set
	 */
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

}
