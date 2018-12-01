package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import messages.UserQueryMinionBasicInfo;

public class DBModel {

	DBConnector dbcon = null;
	String databaseName;

	public DBModel(String databaseName) {
		dbcon = new DBConnector();
		this.databaseName = databaseName;
	}

	public int newMinion(String code) {

		int minionId = 0;

		try {
			Connection con = dbcon.connect();
			PreparedStatement ps = con.prepareStatement("INSERT INTO " + this.databaseName + "(minionCode) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, code);
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				minionId = rs.getInt(1);
			}

			con.close();
			
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("> Error inserting records: " + se.getMessage());
		}
		
		return minionId;
	}
	
	public boolean minionLogin(int id, String code) {
		
		boolean result = false;
		
		try {
			Connection con = dbcon.connect();
			PreparedStatement ps = con.prepareStatement("SELECT (minionId) FROM " + this.databaseName + " WHERE minionID=(?) AND minionCode=(?)");
			ps.setInt(1, id);
			ps.setString(2, code);
			ResultSet rs = ps.executeQuery();

			int res = 0;
			if (rs.next()) {
				res = rs.getInt(1);
			}
			
			if(res == id) {
				result = true;
			}

			con.close();
			
		} catch (SQLException se) {
			System.out.println("> Error inserting records: " + se.getMessage());
		}
		
		return result;

	}
	
	public ArrayList<Integer> getMinionList(){
		
		try {
			Connection con = dbcon.connect();
			String query = "SELECT minionId FROM " + this.databaseName;
			ResultSet rs = con.createStatement().executeQuery(query);
			ArrayList<Integer> result = new ArrayList<>();
			while(rs.next()) {
				result.add(rs.getInt(1));
			}
			con.close();
			return result;

		}catch(Exception e) {
			System.out.println(e);
			return new ArrayList<Integer>();
		}
		
	}
	
	public boolean saveMinionBasicInfo(UserQueryMinionBasicInfo msg) {
		
		boolean ok = false;

		try {
			Connection con = dbcon.connect();
			PreparedStatement ps = con.prepareStatement("UPDATE " + this.databaseName +
					" SET ram=?, cpu=?, hostname=?, privateIp=?, publicIP=? WHERE minionId=? AND minionCode=?");
			ps.setString(1, msg.getRAM());
			ps.setString(2, msg.getCPULoad());
			ps.setString(3, msg.getHostname());
			ps.setString(4, msg.getIP());
			ps.setString(5, msg.getPublicIP() == null? " " : msg.getPublicIP());
			ps.setInt(6, msg.getMinionId());
			ps.setString(7, msg.getMinionCode());
			int result = ps.executeUpdate();
			
			if(result > 0)
				ok = true;

			con.close();
			
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("> Error inserting records: " + se.getMessage());
		}
		
		return ok;
		
	}
	
	public boolean getMinionBasicInfo(UserQueryMinionBasicInfo msg) {

		boolean result = false;
		
		try {
			Connection con = dbcon.connect();
			String sql ="SELECT ram, cpu, hostname, privateIp, publicIp, tag FROM " 
					+ this.databaseName + " WHERE minionID=?" ;
			System.out.println(sql);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, msg.getMinionId());
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				result = true;
				msg.setRAM(rs.getString(1));
				msg.setCPU(rs.getString(2));
				msg.setHostName(rs.getString(3));
				msg.setIP(rs.getString(4));
				msg.setPublicIP(rs.getString(5));
				msg.setTag(rs.getString(6));
			}
			
			con.close();
			
		} catch (SQLException se) {
			System.out.println("> Error querying basic info records: " + se.getMessage());
			se.printStackTrace();
		}
		
		return result;

	}
}

//
//package models;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import records.BankRecords;
//
////$ /usr/local/Cellar/mysql/8.0.12/bin/mysql -h "www.papademas.net" -u db510 -p --port=3307 -v 510labs
//
//
///*
// * This class is the model of our application and allows to make different specific queries with the databse.
// */
//public class DaoModel {
//
//	// Declare DB objects
//	DBConnect conn = null;
//	Statement stmt = null;
//	String databaseName;
//
//	// constructor
//	public DaoModel(String databaseName) { // create db object instance
//		conn = new DBConnect();
//		this.databaseName = databaseName;
//	}
//
//	/*
//	 * This method creates a new table and defines its structure
//	 */
//	public void createTable() {
//		try {
//
//			// Open a connection
//			System.out.println("Connecting to database to create Table...");
//			stmt = conn.connect().createStatement();
//			System.out.println("Connected database successfully...");
//
//			// Execute create query
//			System.out.println("Creating table in given database...");
//
//			String sql = "CREATE TABLE " + this.databaseName + " (pid INTEGER not NULL AUTO_INCREMENT, "
//					+ " id VARCHAR(10), " + " income numeric(8,2), " + " pep VARCHAR(3), " + " PRIMARY KEY ( pid ))";
//			stmt.executeUpdate(sql);
//
//			System.out.println("Created table in given database...");
//			conn.connect().close(); // close db connection
//
//		} catch (SQLException se) { // Handle errors for JDBC
//			System.out.println("> Error creating table: " + se.getMessage());
//		}
//
//	}
//
//	/*
//	 * This method takes a BankRecords array and populates a table with such number of rows.
//	 * In this specific case, we use prepared statements, which benefit from many advantages such as sql protection in web applications
//	 */
//	public void insertRecordsPrep(BankRecords[] robjs) {
//		try {
//			// Execute a query
//			System.out.println("Inserting records into the table...");
//			Connection con = conn.connect();
//			PreparedStatement pstmt = con.prepareStatement("INSERT INTO " + this.databaseName+ "(pid, id, income, pep) VALUES(?,?,?,?)");
//
//			int[] resultstatus= {0,0};
//
//			// Include all object data to the database table
//			for (int i = 0; i < robjs.length; ++i) {
//				
//					pstmt.setInt(1, i+1);
//					pstmt.setString(2, robjs[i].getId());
//					pstmt.setDouble(3, robjs[i].getIncome());
//					pstmt.setString(4, robjs[i].getPep().toString());
//				try {
//					pstmt.executeUpdate();
//					resultstatus[0]++;
//				}catch(SQLException se) {
//					resultstatus[1]++;
//				}
//
//			}
//			con.close();
//			System.out.format("A total of %d entries where inserted. A total of %d entries where rejected: id already in the database\n",resultstatus[0],resultstatus[1]);
//			//System.out.println(">" + (robjs.length) + " values inserted.");
//		} catch (SQLException se) {
//			System.out.println("> Error inserting records: " + se.getMessage());
//		}
//	}// INSERT INTO METHOD
//
//
//	/*
//	 * This method takes a BankRecords array and populates a table with such number of rows.
//	 */
//	public void insertRecords(BankRecords[] robjs) {
//		try {
//			// Execute a query
//			System.out.println("Inserting records into the table...");
//			stmt = conn.connect().createStatement();
//			String sql = null;
//			int[] resultstatus= {0,0};
//
//			// Include all object data to the database table
//			for (int i = 0; i < robjs.length; ++i) {
//
//				// finish string assignment to insert all object data
//				// (id, income, pep) into your database table
//
//				try {
//					sql = "INSERT INTO " + this.databaseName + "(pid, id, income, pep ) " + "VALUES (' " + (1+i)+ " ', ' "
//							+ robjs[i].getId() + " ', ' " + robjs[i].getIncome() + "','" + robjs[i].getPep() + "')";
//					stmt.executeUpdate(sql);
//					resultstatus[0]++;
//				}catch(SQLException se) {
//					resultstatus[1]++;
//				}
//
//			}
//			System.out.format("A total of %d entries where inserted. A total of %d entries where rejected: id already in the database\n",resultstatus[0],resultstatus[1]);
//			conn.connect().close();
//		} catch (SQLException se) {
//			se.printStackTrace();
//		}
//	}// INSERT INTO METHOD
//
//	/*
//	 * This method queries the database and gets a resultSet, which is an specific object that can be use to retrieve the real data.
//	 */
//	public ResultSet retrieveRecords() {
//
//		ResultSet rs = null;
//
//		try {
//			System.out.println("Querying records...");
//			stmt = conn.connect().createStatement();
//			String sql = "SELECT pid,id,income,pep from " + this.databaseName +" order by pep desc";
//			rs = stmt.executeQuery(sql);
//			conn.connect().close();
//			System.out.println("Query completed.");
//		} catch (SQLException e) {
//			System.out.println("Error querying data: "+ e.getMessage());
//		}
//		
//		return rs;
//	}
//
//	/*
//	 * This method makes a request to the database for an specific query which involves conditionals.
//	 * We use this to count the number of results and get specific statistics
//	 */
//	public ResultSet retrieveRecordsLoan() {
//
//		ResultSet rs = null;
//
//		try {
//			System.out.println("Querying records...");
//			stmt = conn.connect().createStatement();
//			String sql = "SELECT pid,id,income,pep from " + this.databaseName +" where pep='NO' AND income>='30000' order by income desc";
//			rs = stmt.executeQuery(sql);
//			conn.connect().close();
//			System.out.println("Query completed.");
//		} catch (SQLException e) {
//			System.out.println("Error querying data: "+ e.getMessage());
//		}
//		
//		try {
//			StringBuilder sb = new StringBuilder();
//			int total = 0;
//			while(rs.next()) {
//				sb.append(rs.getInt(1)+",");
//				total++;
//			}
//			System.out.println("The customer id's that can ask for a loan are: "+sb.toString());
//			System.out.println("A total of "+total+" customers can ask for it.");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return rs;
//	}
//
//}
