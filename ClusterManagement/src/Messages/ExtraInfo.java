package Messages;

public class ExtraInfo extends Message implements MinionGatherable{
	
	private ExtraInfoType ET;
	private String info;
	
	public String getInfo() {
		return this.info;
	}

	@Override
	public void gatherInformation() {
		// TODO Auto-generated method stub
		
	}

}
