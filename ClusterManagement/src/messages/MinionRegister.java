package messages;

import java.io.Serializable;
import java.util.Date;

public class MinionRegister extends Message implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	private boolean correct = false;
	
	public MinionRegister() {
		this.msgType = MessageType.MINION_REGISTER;
		this.setMsgType(msgType);
		this.setDate(new Date());
	}
	
	public MinionRegister(int minionId) {
		this.msgType = MessageType.MINION_REGISTER;
		this.setMsgType(msgType);
		this.setDate(new Date());
		this.setMinionId(minionId);
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
	
	public String toString() {
		String s = String.format(">Correct: %b\n", correct);
		return super.toString()+s;
	}

}
