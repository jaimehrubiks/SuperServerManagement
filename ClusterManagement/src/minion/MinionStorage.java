package minion;

/*
 * JAIME HIDALGO.
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author Jaime Hidalgo García
 */
public class MinionStorage {

	private boolean isRegistered = false;

	private int minionId;
	private String minionCode;
	
	private String fileName;
	
    public MinionStorage(String name) {
		this.fileName = name;
    }
    
    private BufferedReader openRxStreams() throws IOException{
        FileReader      fr = new FileReader     (fileName);
        BufferedReader  br = new BufferedReader (fr);
        return br;
    }
    
    private PrintWriter openWxStreams() throws IOException{
        FileWriter      fw = new FileWriter     (fileName);
        BufferedWriter  bw = new BufferedWriter (fw);
        PrintWriter     pw = new PrintWriter    (bw, false);
        return pw;
    }
    
    public void readSettings(){
    	ArrayList<String> settings = new ArrayList<>();
    	String tmp;

        try(BufferedReader br = openRxStreams() ){
            
            while(  (tmp = br.readLine()) != null  )
            	settings.add(tmp);
            
            if(settings.size() == 2) {
            	minionId = Integer.parseInt(settings.get(0));
            	minionCode = settings.get(1);
            }else {
            	System.out.println("Minion is not registered.");
            	isRegistered = false;
            }
                
        }catch(IOException e){
            System.out.println(e.toString());
			System.out.println("Minion is not registered.");
        }
    	
    }
    
    public void saveSettings(){
        try(PrintWriter pw = openWxStreams() ){
            
        	pw.println(minionId);
        	pw.println(minionCode);
            
        }catch(IOException e){
            System.out.println(e.toString());
        }
    }

	/**
	 * @return the isRegistered
	 */
	public boolean isRegistered() {
		return isRegistered;
	}

	/**
	 * @param isRegistered the isRegistered to set
	 */
	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}

	/**
	 * @return the minionId
	 */
	public int getMinionId() {
		return minionId;
	}

	/**
	 * @param minionId the minionId to set
	 */
	public void setMinionId(int minionId) {
		this.minionId = minionId;
	}

	/**
	 * @return the minionCode
	 */
	public String getMinionCode() {
		return minionCode;
	}

	/**
	 * @param minionCode the minionCode to set
	 */
	public void setMinionCode(String minionCode) {
		this.minionCode = minionCode;
	}
    
}
