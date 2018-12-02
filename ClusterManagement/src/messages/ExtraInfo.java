package messages;

public class ExtraInfo extends Message implements MinionGatherable{
	
	private ExtraInfoType ET;
	private String info;

	public ExtraInfo(ExtraInfoType et) {
		this.ET=et;
	}
	
	public String getInfo() {
		return this.info;
	}

	@Override
	public void gatherInformation() {
		// TODO Auto-generated method stub
		
	}

}
