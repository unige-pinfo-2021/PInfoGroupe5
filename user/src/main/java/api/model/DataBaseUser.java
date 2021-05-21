package api.model;

import java.util.*;
import java.util.Properties;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
/*import java.util.logging.Level;
import java.util.logging.Logger;*/

public class DataBaseUser{
	
	private String url;
	private String username;
	private String password;

	private String JDBC_DRIVER= "org.postgresql.Driver"; //"com.mysql.cj.jdbc.Driver"

	public DataBaseUser(String path){
		Properties props =  new DataBaseUserProperties().readProperties(path);

		this.url=props.getProperty("db.url");
		this.username=props.getProperty("db.user");
		this.password=props.getProperty("db.passwd");

		/*this.url="";
		this.username="";
		this.password="";*/

	}//end constructor


	public String try_connect(){
		String connected= "try to make connection!";

		Connection conn = null;

		try{
			Class.forName(JDBC_DRIVER); 
			/*Connection*/ conn = DriverManager.getConnection(this.url, this.username, this.password);
			    if (conn != null) {
				System.out.println("\n"+"Connection established!");
				connected = "Connection established!";

			    } else {
				System.out.println("Failed to make connection!");
				connected = "Failed to make connection!";
			    }

		} catch (SQLException e) {
			 System.err.format("SQL State: %s\n%s"+"\n", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			   e.printStackTrace();

		}finally{
			try{
				conn.close();
			}catch (Exception e) {
			 e.printStackTrace();
			}
		}  
		return connected;

    }//end connect

	
	public void INSERT_User(String username){
		String query = "INSERT INTO users(username) VALUES(?)";
		set_User(query, username);
	}//end INSERT_User


	public void DELETE_User(String username){
		String query ="DELETE FROM Users WHERE username=?";
		set_User(query, username);
	}//end INSERT_User


	public ArrayList<String> SELECT_User(String username){
		//String query ="SELECT *  FROM Users";
		String query ="SELECT username  FROM Users WHERE username="+"'"+username+"'";
		return get_User(query, username);
	}//end INSERT_User


	//public void UPDATE_User(String username){}


	public void set_User(String query, String username){
		Connection conn = null;

		try {
			Class.forName(JDBC_DRIVER);
			/*Connection*/ conn = DriverManager.getConnection(this.url, this.username, this.password);
			PreparedStatement pst = conn.prepareStatement(query);

			pst.setString(1, username);
            		pst.executeUpdate();			 

		}catch (SQLException e) {
			System.err.format("SQL State: %s\n%s"+"\n", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			 e.printStackTrace();
		}finally{
			try{
				conn.close();
			}catch (Exception e) {
			 e.printStackTrace();
			}
		}        


    }//end set_User

	public ArrayList<String> get_User(String query, String name){
		ArrayList<String> params = new ArrayList();

		Connection conn = null;

		try{ 
			Class.forName(JDBC_DRIVER);
			/*Connection*/ conn = DriverManager.getConnection(this.url, this.username, this.password);
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				//System.out.print(rs+"\n");
            			
				//System.out.print("\n"+rs.getString(1));
				params.add(rs.getString(1));
            		}
						 

		}catch (SQLException e) {
			System.err.format("SQL State: %s\n%s"+"\n", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			 e.printStackTrace();
		}finally{
			try{
				conn.close();
			}catch (Exception e) {
			 e.printStackTrace();
			}
		}        
		
		return params;

    }//end get_User


	public boolean EXIST_User(String name){
		boolean exist = false;

		String query ="SELECT username  FROM Users WHERE username="+"'"+name+"'";

		Connection conn = null;

		try{  
			Class.forName(JDBC_DRIVER);
			/*Connection*/ conn = DriverManager.getConnection(this.url, this.username, this.password);
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			if (!rs.next() ){
    				//System.out.println("no data");
			}else{
				exist=true;
				//System.out.println("data exist");
			}
						 

		}catch (SQLException e) {
			System.err.format("SQL State: %s\n%s"+"\n", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			 e.printStackTrace();
		}finally{
			try{
				conn.close();
			}catch (Exception e) {
			 e.printStackTrace();
			}
		}

		return exist;     


    }//end get_User


}//end class
