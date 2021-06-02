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

	private static final String ATTRIBUT1 = "name";
	private static final String ATTRIBUT2 = "email";

	private String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //"org.postgresql.Driver"

	public DataBaseUser() throws Exception{

		Encryption encrypt = new Encryption();
		this.url = encrypt.getu();
		this.username = encrypt.getg();
		this.password = encrypt.getp();

	}


	public String connect(){
		String connected= "try to make connection!";

		Connection conn = null;

		try{
			Class.forName(JDBC_DRIVER); 
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			connected = "Connection established!";	    

		}catch (Exception e){
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

	public boolean INSERT(String username, String email){
		boolean flag = false;

		if(!EXIST(username)){
			String query = "INSERT INTO user(name, email) VALUES(?, ?)";
			set(query, username, email);
			flag = true;
		}

		return flag;
	}


	public boolean DELETE(String username){
		boolean flag = false;

		if(EXIST(username)){
			String query ="DELETE FROM user WHERE name=?";
			set(query, username, null);
			flag = true;
		}
		return flag;
	}


	public ArrayList<Map<String,String>> SELECT(String username){
		ArrayList<Map<String,String>> params = new ArrayList<Map<String, String>>();

		Map<String,String> p = new HashMap<String,String>();
		p.put(ATTRIBUT1,"Unknown");
		p.put(ATTRIBUT2,"Default");
		params.add(p);

		if(EXIST(username)){
			String query ="SELECT * FROM user WHERE name=?";
			params = get(query, username);
		}
		return params;
	}

	public ArrayList<Map<String,String>> SELECTALL(){
		String query ="SELECT * FROM user";
		return get(query, null);
	}

	//public void UPDATE_User(String username){}


	public void set(String query, String username, String email){
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

		}catch (Exception e) {
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

			}catch (Exception e){
				Logger.getLogger(DataBaseUser.class.getName()).log(Level.SEVERE, null, e);
			}

		}        


    }

	public ArrayList<Map<String,String>> get(String query, String username){
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
				p.put(ATTRIBUT1,rs.getString(ATTRIBUT1));
				p.put(ATTRIBUT2,rs.getString(ATTRIBUT2));
				params.add(p);
            		}
		}catch (Exception e){
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


	public boolean EXIST(String name){
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

			if (rs.next() ){
				exist=true;
			}
						 

		}catch (Exception e) {
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
