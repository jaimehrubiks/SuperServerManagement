package messages;

import java.io.Serializable;
import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

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
		hostName = "hostname";
		//tag = "servidor de amazon"; este en realidad seria en otra funcion aparte, lo mete el master, no el minion.
	}
	
	public String getCPULoad() {
		
		OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
		String cpu_load;
		cpu_load = String.format("%.2f",osBean.getSystemLoadAverage());
		System.out.println(cpu_load);
		return cpu_load;
	}
	
	public String getRAMUsage() {
		
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

}
