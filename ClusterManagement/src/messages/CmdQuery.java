package messages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CmdQuery extends Message implements MinionGatherable{
	
	private CmdType cmdtype;
	private ArrayList<String> processList= new ArrayList<String>();
	
	public CmdQuery(CmdType cmdType, MessageType messagetype,int id) {
		this.cmdtype=cmdType;
		this.msgType=messagetype;
		this.minionId=id;
	}
	
	public void getProcessList() {
		String system=System.getProperty("os.name").toLowerCase();
		Process p;
		String line;
			
		
		try {
			if(system.indexOf("win")>=0) {
				System.out.println("Windows");
			      p = Runtime.getRuntime().exec("tasklist.exe");
			}
			else {
				p = Runtime.getRuntime().exec("ps aux");
			}
			BufferedReader input=new BufferedReader(new InputStreamReader(p.getInputStream()));
			while((line=input.readLine()) !=null) {
				processList.add(line);
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void gatherInformation() {
		// TODO Auto-generated method stub
		getProcessList();
	}
	@Override
	public String toString() {
		String message = "<html>";
		for (String string : processList) {
			message=message+string+"<br>";
		}
		message=message+"</html>";
		return message;
		
	}

}
