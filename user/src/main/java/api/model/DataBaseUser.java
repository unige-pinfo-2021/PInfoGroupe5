package api.model;

import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseUser{
	
	private String url;
	private String username;
	private String password;

	private String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //"org.postgresql.Driver"

	public DataBaseUser() throws Exception{

		Encryption encrypt = new Encryption();
		this.url = encrypt.getu();
		this.username = encrypt.getg();
		this.password = encrypt.getp();

	}//end constructor


	public String try_connect(){
		String connected= "try to make connection!";

		Connection conn = null;

		try{
			Class.forName(JDBC_DRIVER); 
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			connected = "Connection established!";

			    

		} catch (SQLException e) {
			 System.err.format("SQL State: %s\n%s"+"\n", e.getSQLState(), e.getMessage());
			 connected = "Failed to make connection!";

		} catch (Exception e) {
			   Logger.getLogger(DataBaseUser.class.getName()).log(Level.SEVERE, null, e);
			   connected = "Failed to make connection!";
		}finally{
			try{
				if(conn != null){
					conn.close();
				}

			}catch (Exception e){
				Logger.getLogger(DataBaseUser.class.getName()).log(Level.SEVERE, null, e);
			}
		}  
		return connected;

    } 

	
	public void INSERT_User(String username, String email){
		String query = "INSERT INTO user(name, email) VALUES(?, ?)";
		set_User(query, username, email);
	}


	public void DELETE_User(String username){
		String query ="DELETE FROM user WHERE name=?";
		set_User(query, username, null);
	}


	public ArrayList<Map<String,String>> SELECT_User(String username){
		String query ="SELECT * FROM user WHERE name=?";
		return get_User(query, username);
	}

	public ArrayList<Map<String,String>> SELECT_AllUser(){
		String query ="SELECT * FROM user";
		return get_User(query, null);
	}

	//public void UPDATE_User(String username){}


	public void set_User(String query, String username, String email){
		Connection conn = null;
		PreparedStatement pst = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			pst = conn.prepareStatement(query);

			pst.setString(1, username);

			if(email != null){
				pst.setString(2, email);
			}

            		pst.executeUpdate();			 

		}catch (SQLException e) {
			System.err.format("SQL State: %s\n%s"+"\n", e.getSQLState(), e.getMessage());

		} catch (Exception e) {
			Logger.getLogger(DataBaseUser.class.getName()).log(Level.SEVERE, null, e);

		}finally{
			try{
				if(conn != null){
					conn.close();
				}

			}catch (Exception e){
				Logger.getLogger(DataBaseUser.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
				if(pst != null){
					pst.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseUser.class.getName()).log(Level.SEVERE, null, e);
			}
		}        


    }

	public ArrayList<Map<String,String>> get_User(String query, String username){
		ArrayList<Map<String,String>> params = new ArrayList<Map<String, String>>();

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try{
			Class.forName(JDBC_DRIVER); 
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			pst = conn.prepareStatement(query);

			if(username != null){
				pst.setString(1, username);
			}

			rs = pst.executeQuery();

			while (rs.next()) {
				Map<String,String> p = new HashMap<String,String>();
				p.put("name",rs.getString("name"));
				p.put("email",rs.getString("email"));
				params.add(p);
            		}
		}catch (SQLException e) {
			System.err.format("SQL State: %s\n%s"+"\n", e.getSQLState(), e.getMessage());

		} catch (Exception e){
			Logger.getLogger(DataBaseUser.class.getName()).log(Level.SEVERE, null, e);
		}
		
		finally{
			try{
				if(conn != null){
					conn.close();
				}
			}catch (Exception e) {
				Logger.getLogger(DataBaseUser.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
				if(pst != null){
					pst.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseUser.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
				if(rs != null){
				rs.close();
				}
			}catch (Exception e) {
				Logger.getLogger(DataBaseUser.class.getName()).log(Level.SEVERE, null, e);
			}
		}        
		
		return params;

    }


	public boolean EXIST_User(String name){
		boolean exist = false;

		String query ="SELECT name  FROM user WHERE name=?";

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try{  
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			pst = conn.prepareStatement(query);

			pst.setString(1, name);

			rs = pst.executeQuery();

			if (!rs.next() ){
    				//System.out.println("no data");
			}else{
				exist=true;
				//System.out.println("data exist");
			}
						 

		}catch (SQLException e) {
			System.err.format("SQL State: %s\n%s"+"\n", e.getSQLState(), e.getMessage());

		} catch (Exception e) {
			Logger.getLogger(DataBaseUser.class.getName()).log(Level.SEVERE, null, e);

		}finally{
			try{
				if(conn != null){
					conn.close();
				}
			}catch (Exception e) {
				Logger.getLogger(DataBaseUser.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
				if(pst != null){
					pst.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseUser.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
				if(rs != null){
					rs.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseUser.class.getName()).log(Level.SEVERE, null, e);
			}
		}

		return exist;     


    }


}//end class
