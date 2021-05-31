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

public class DataBaseUGroup{
	
	private String url;
	private String username;
	private String password;

	private String JDBC_DRIVER= "org.postgresql.Driver"; //"com.mysql.cj.jdbc.Driver"

	public DataBaseUGroup(String path){
		Properties props =  new DataBaseProperties().readProperties(path);

		this.url=props.getProperty("db.url");
		this.username=props.getProperty("db.user");
		this.password=props.getProperty("db.passwd");

		/*this.url="jdbc:mysql://129.194.10.130:3306/tinderfilmBD";
		this.username = "groupe5";
		this.password = "12345";*/

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


	public void INSERT(String ID, String username){
		String query = "INSERT INTO UGroups(ID, username) VALUES(?, ?)";

		Connection conn = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			PreparedStatement pst = conn.prepareStatement(query);

			pst.setString(1, ID);
			pst.setString(2, username); 

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


    }//end 

	public ArrayList<String> SELECT(String ID, boolean param){
		//String query ="SELECT *  FROM UGroups WHERE ID=? ";
		String query ="SELECT username  FROM UGroups WHERE ID="+"'"+ID+"'";

		if(param){
			query ="SELECT ID  FROM UGroups WHERE username="+"'"+ID+"'";
		}

		ArrayList<String> params = new ArrayList();

		Connection conn = null;

		try{ 
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			PreparedStatement pst = conn.prepareStatement(query);

			//pst.setString(1, ID);
            		//pst.executeUpdate();

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				params.add(rs.getString(1)); //ID

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

    }//end 



	public void DELETE(String ID){
		String query = "DELETE FROM UGroups WHERE ID=?";

		Connection conn = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			PreparedStatement pst = conn.prepareStatement(query);

			pst.setString(1, ID);
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


    }//end 


	public void DELETE(String ID, String username){
		String query = "DELETE FROM UGroups WHERE ID=? AND username=?";

		Connection conn = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			PreparedStatement pst = conn.prepareStatement(query);

			pst.setString(1, ID);
			pst.setString(2, username);
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


    }//end 


	public boolean EXIST(String ID){
		boolean exist = false;

		String query ="SELECT ID  FROM UGroups WHERE ID="+"'"+ID+"'";

		Connection conn = null;

		try{  
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
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


    }//end 


}//end class
