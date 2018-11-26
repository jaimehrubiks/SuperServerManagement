package messages;

public class BasicInfo extends Message implements MinionGatherable{
	
	private int CPU;
	private String publicIP;
	private String RAM;
	
	@Override
	public void gatherInformation() {
		
	}

}
