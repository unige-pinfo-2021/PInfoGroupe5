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

	private String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //"org.postgresql.Driver"

	public DataBaseUser(String path){
		//Properties props =  new DataBaseUserProperties().readProperties(path);

		//this.url=props.getProperty("db.url");
		//this.username=props.getProperty("db.user");
		//this.password=props.getProperty("db.passwd");

		this.url="jdbc:mysql://129.194.10.130:3306/tinderfilmBD";
		this.username = "groupe5";
		this.password = "12345";

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

	
	public void INSERT_User(String username, String email){
		String query = "INSERT INTO user(name, email) VALUES('" + username + "','" + email +"');";
		set_User(query);
	}//end INSERT_User


	public void DELETE_User(String username){
		String query ="DELETE FROM user WHERE name='" + username + "';";
		set_User(query);
	}//end INSERT_User


	public ArrayList<Map<String,String>> SELECT_User(String username){
		//String query ="SELECT *  FROM Users";
		String query ="SELECT * FROM user WHERE name="+"'"+username+"';";
		return get_User(query);
	}//end INSERT_User

	public ArrayList<Map<String,String>> SELECT_AllUser(){
		String query ="SELECT * FROM user;";
		return get_User(query);
	}//end INSERT_User

	//public void UPDATE_User(String username){}


	public void set_User(String query){
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

	public ArrayList<Map<String,String>> get_User(String query){
		ArrayList<Map<String,String>> params = new ArrayList();
		Connection conn = null;
		try{
			Class.forName(JDBC_DRIVER); 
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				//System.out.print(rs+"\n");
				Map<String,String> p = new HashMap<String,String>();
				//System.out.print("\n"+rs.getString(1));
				p.put("name",rs.getString("name"));
				p.put("email",rs.getString("email"));
				params.add(p);
            		}
		}catch (SQLException e) {
			System.err.format("SQL State: %s\n%s"+"\n", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
		finally{
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

		String query ="SELECT name  FROM Users WHERE name="+"'"+name+"';";

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
