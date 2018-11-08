package Messages;

public class CmdAnswers extends Message{

	private CmdType cmdtype;
	private String answer;
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String getAnswer() {
		return this.answer;
	}
}
