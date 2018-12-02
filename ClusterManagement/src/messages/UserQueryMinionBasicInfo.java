package messages;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.sun.management.OperatingSystemMXBean;

import db.DBConnector;

public class UserQueryMinionBasicInfo extends Message implements Serializable, MinionGatherable {
	
	private static final long serialVersionUID = 1L;

	
	private String CPU;
	private String IP;
	private String RAM;
	private String publicIP;
	private String hostName;
	private String tag;
	private boolean online;
	
	public UserQueryMinionBasicInfo(int id) {
		this.msgType = MessageType.USER_QUERY_BASICINFO;
		this.minionId = id;
	}
	

	@Override
	public void gatherInformation() {
		// TODO: IMPLEMENT THIS METHOD
		//CPU = "56%";
		CPU = getCPULoad();
		IP = "192.168.0.1";
		//RAM = "45434M";
		RAM = getRAMUsage();
		hostName = get_Hostname();
		//tag = "servidor de amazon"; este en realidad seria en otra funcion aparte, lo mete el master, no el minion.
	}
	
	
	public String get_Hostname() {
		
		String real_hostname = null;
		
		try {
		      InetAddress inetAddress = InetAddress.getLocalHost();
		      System.out.println("IP Address: "+inetAddress.getHostAddress());
		      System.out.println("Hostname: "+inetAddress.getHostName());
		      real_hostname = inetAddress.getHostName();
		    }catch(UnknownHostException unknownHostException){
		      unknownHostException.printStackTrace();
		    }
		
		return real_hostname;
	}
	
	public String getCPULoad() {
		
		OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
		String cpu_load;
		cpu_load = String.format("%.2f",osBean.getSystemLoadAverage());
		System.out.println(cpu_load);
		return cpu_load;
	}
	
	private String getRAMUsage() {
		
		OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
		long RAM_total, RAM_used;
		double RAM_percentages;
		RAM_total = osBean.getTotalPhysicalMemorySize();
		RAM_used = osBean.getFreePhysicalMemorySize();
		System.out.println(RAM_used);
		System.out.println(RAM_total);
		RAM_percentages = ((double)RAM_used/RAM_total)*100;
		String RAM_percent;
		RAM_percent = String.format("%.2f", RAM_percentages);
		System.out.println(RAM_percent);
		return RAM_percent;
	}
	
	public void setPublicIP(String publicIP) {
		this.publicIP = publicIP;
	}

	/**
	 * @return the online
	 */
	public boolean isOnline() {
		return online;
	}

	public String getHostname() {
		return hostName;
	}
	public String getTag() {
		return tag;
	}
	public String getPublicIP(){
		return publicIP;
	}
	public String getIP() {
		return IP;
	}
	/**
	 * @param online the online to set
	 */
	public void setOnline(boolean online) {
		this.online = online;
	}
	
	
	@Override
	public String toString() {
		String s = String.format("> Online: %b\n> Tag: %s\n> Hostname: %s\n> Public IP: %s\n> private IP: %s\n> CPU: %s\n> RAM: %s\n",
				online, tag, hostName, publicIP, IP, CPU, RAM);
		return super.toString()+s;
	}

	/**
	 * @return the cPU
	 */
	public String getCPU() {
		return CPU;
	}

	/**
	 * @param cPU the cPU to set
	 */
	public void setCPU(String cPU) {
		CPU = cPU;
	}

	/**
	 * @return the rAM
	 */
	public String getRAM() {
		return RAM;
	}

	/**
	 * @param rAM the rAM to set
	 */
	public void setRAM(String rAM) {
		RAM = rAM;
	}

	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * @param hostName the hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * @param iP the iP to set
	 */
	public void setIP(String iP) {
		IP = iP;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

}
