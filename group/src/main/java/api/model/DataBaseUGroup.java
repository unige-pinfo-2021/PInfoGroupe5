package api.model;

import java.util.*;
import java.util.Properties;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseUGroup{
	
	private String url;
	private String username;
	private String password;

	private String JDBC_DRIVER= "com.mysql.cj.jdbc.Driver";

	public DataBaseUGroup() throws Exception{

		Encryption encrypt = new Encryption();
		this.url = encrypt.getu();
		this.username = encrypt.getg();
		this.password = encrypt.getp();

	}//end constructor


	public String connect(){
		String connected= "try to make connection!";

		Connection conn = null;

		try{
			Class.forName(JDBC_DRIVER); 
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			connected = "Connection established!";

		}catch (Exception e) {
			   Logger.getLogger(DataBaseUGroup.class.getName()).log(Level.SEVERE, null, e);

		}finally{
			try{
				if (conn != null){
					 conn.close();
				}
			}catch (Exception e){
			 	Logger.getLogger(DataBaseUGroup.class.getName()).log(Level.SEVERE, null, e);
			}
		}  
		return connected;

    }//end connect


	public void INSERT(String ID, String username){
		String query = "INSERT INTO UGroups(ID, username) VALUES(?, ?)";

		Connection conn = null;
		PreparedStatement pst = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			pst = conn.prepareStatement(query);

			pst.setString(1, ID);
			pst.setString(2, username); 

            		pst.executeUpdate();			 

		}catch (Exception e) {
			Logger.getLogger(DataBaseUGroup.class.getName()).log(Level.SEVERE, null, e);
		}finally{
			try{
				if(conn != null){
					conn.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseUGroup.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
				if(pst != null){
					pst.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseUGroup.class.getName()).log(Level.SEVERE, null, e);
			}

		}        


    }//end 

	public ArrayList<String> SELECT(String ID, boolean param){
		//String query ="SELECT *  FROM UGroups WHERE ID=? ";
		String query ="SELECT username  FROM UGroups WHERE ID=?";

		if(param){
			query ="SELECT ID  FROM UGroups WHERE username="+"'"+ID+"'";
		}

		ArrayList<String> params = new ArrayList();

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try{ 
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			pst = conn.prepareStatement(query);

			pst.setString(1, ID);
            		//pst.executeUpdate();

			rs = pst.executeQuery();

			while (rs.next()) {

				params.add(rs.getString(1)); //ID

            		}
						 

		}catch (Exception e) {
			Logger.getLogger(DataBaseUGroup.class.getName()).log(Level.SEVERE, null, e);
		}finally{
			try{
				if(conn != null){
					conn.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseUGroup.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
				if(pst != null){
					pst.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseUGroup.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
				if(rs != null){
					rs.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseUGroup.class.getName()).log(Level.SEVERE, null, e);
			}

		}        
		
		return params;

    }//end 



	public void DELETE(String ID){
		String query = "DELETE FROM UGroups WHERE ID=?";

		Connection conn = null;
		PreparedStatement pst = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			pst = conn.prepareStatement(query);

			pst.setString(1, ID);
            		pst.executeUpdate();			 

		}catch (Exception e) {
			Logger.getLogger(DataBaseUGroup.class.getName()).log(Level.SEVERE, null, e);
		}finally{
			try{
				if(conn != null){
					conn.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseUGroup.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
				if(pst != null){
					pst.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseUGroup.class.getName()).log(Level.SEVERE, null, e);
			}

		}        


    }//end 


	public void DELETE(String ID, String username){
		String query = "DELETE FROM UGroups WHERE ID=? AND username=?";

		Connection conn = null;
		PreparedStatement pst = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			pst = conn.prepareStatement(query);

			pst.setString(1, ID);
			pst.setString(2, username);
            		pst.executeUpdate();			 

		}catch (Exception e) {
			Logger.getLogger(DataBaseUGroup.class.getName()).log(Level.SEVERE, null, e);
		}finally{
			try{
				if(conn != null){
					conn.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseUGroup.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
				if(pst != null){
					pst.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseUGroup.class.getName()).log(Level.SEVERE, null, e);
			}

		}       


    }//end 


	public boolean EXIST(String ID){
		boolean exist = false;

		String query ="SELECT ID  FROM UGroups WHERE ID=?";

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{  
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();

			if (rs.next() ){
				exist=true;
			}
						 

		}catch (Exception e) {
			Logger.getLogger(DataBaseUGroup.class.getName()).log(Level.SEVERE, null, e);
		}finally{
			try{
				if(conn != null){
					conn.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseUGroup.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
				if(pst != null){
					pst.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseUGroup.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
				if(rs != null){
					rs.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseUGroup.class.getName()).log(Level.SEVERE, null, e);
			}

		}

		return exist;     


    }//end 


}//end class
