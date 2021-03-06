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

public class DataBaseInvit{
	
	private String url;
	private String username;
	private String password;

	private String JDBC_DRIVER= "com.mysql.cj.jdbc.Driver";

	public DataBaseInvit() throws Exception{

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
			   Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);

		}finally{
			try{
				if (conn != null){
					 conn.close();
				}
			}catch (Exception e){
			 	Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}
		}  
		return connected;

    }//end connect


	public void INSERT(String ID, String film, int totalscore ){
		String query = "INSERT INTO Invits(ID, film, score, totalscore) VALUES(?, ?, 0,?)";

		Connection conn = null;
		PreparedStatement pst = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			pst = conn.prepareStatement(query);

			pst.setString(1, ID);
			pst.setString(2, film); //film
			pst.setInt(3, totalscore); //totalscore

            		pst.executeUpdate();			 

		}catch (Exception e) {
			 Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
		}finally{
			try{
			 	if (conn != null){
					conn.close();
				}
			}catch (Exception e) {
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
			 	if (pst != null){
					pst.close();
				}
			}catch (Exception e){
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}

		}        


    }//end 

	public ArrayList<String> SELECT(String ID, String film){
		String query ="SELECT *  FROM Invits WHERE ID=? AND film=?";

		ArrayList<String> params = new ArrayList();

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try{ 
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			pst = conn.prepareStatement(query);

			pst.setString(1, ID);
			pst.setString(2, film);

			rs = pst.executeQuery();

			while (rs.next()) {

				params.add(rs.getString(1)); //ID
				params.add(rs.getString(2)); //film
				params.add(rs.getString(3)); //score
				params.add(rs.getString(4)); //totalscore
            		}
						 

		}catch (Exception e) {
			 Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
		}finally{
			try{
			 	if (conn != null){
					conn.close();
				}
			}catch (Exception e) {
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
			 	if (pst != null){
					pst.close();
				}
			}catch (Exception e){
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
			 	if (rs != null){
					rs.close();
				}
			}catch (Exception e){
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}
		}        
		
		return params;

    }//end 


	public ArrayList<ArrayList<String>> SELECT(String ID){
		String query ="SELECT *  FROM Invits WHERE ID=?";

		ArrayList<ArrayList<String>> params = new ArrayList();

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try{ 
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			pst = conn.prepareStatement(query);

			pst.setString(1, ID);

			rs = pst.executeQuery();

			while (rs.next()) {

				ArrayList<String> param = new ArrayList();

				param.add(rs.getString(1)); //ID
				param.add(rs.getString(2)); //film
				param.add(rs.getString(3)); //score
				param.add(rs.getString(4)); //totalscore

				params.add(param);
            		}
						 

		}catch (Exception e) {
			 Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
		}finally{
			try{
			 	if (conn != null){
					conn.close();
				}
			}catch (Exception e) {
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
			 	if (pst != null){
					pst.close();
				}
			}catch (Exception e){
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
			 	if (rs != null){
					rs.close();
				}
			}catch (Exception e){
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}
		}        
		
		return params;

    }//end 


	public ArrayList<String> SELECT_films(String ID){
		String query ="SELECT film  FROM Invits WHERE ID=?";

		ArrayList<String> params = new ArrayList();

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try{ 
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			pst = conn.prepareStatement(query);

			pst.setString(1, ID);

			rs = pst.executeQuery();

			while (rs.next()) {

				params.add(rs.getString(1)); //ID
				/*params.add(rs.getString(2)); //film
				params.add(rs.getString(3)); //score
				params.add(rs.getString(4)); //totalscore*/
            		}
						 

		}catch (Exception e){
			 Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
		}finally{
			try{
			 	if (conn != null){
					conn.close();
				}
			}catch (Exception e) {
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
			 	if (pst != null){
					pst.close();
				}
			}catch (Exception e){
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
			 	if (rs != null){
					rs.close();
				}
			}catch (Exception e){
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}
		}         
		
		return params;

    }//end

	public ArrayList<Integer> SELECT_scores(String ID, String param){
		//String query ="SELECT "+param+"  FROM Invits WHERE ID=?";
		String query ="SELECT score  FROM Invits WHERE ID=?";

		if(param.equals("totalscore")){
			query ="SELECT totalscore FROM Invits WHERE ID=?";
		}

		ArrayList<Integer> params = new ArrayList();

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try{ 
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			pst = conn.prepareStatement(query);

			pst.setString(1, ID);

			rs = pst.executeQuery();

			while (rs.next()) {

				params.add(rs.getInt(1)); //ID
				/*params.add(rs.getString(2)); //film
				params.add(rs.getString(3)); //score
				params.add(rs.getString(4)); //totalscore*/
            		}
						 

		}catch (Exception e){
			 Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
		}finally{
			try{
			 	if (conn != null){
					conn.close();
				}
			}catch (Exception e) {
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
			 	if (pst != null){
					pst.close();
				}
			}catch (Exception e){
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
			 	if (rs != null){
					rs.close();
				}
			}catch (Exception e){
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}
		}       
		
		return params;

    }//end


	public void DELETE(String ID){
		String query = "DELETE FROM Invits WHERE ID=?";

		Connection conn = null;
		PreparedStatement pst = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			pst = conn.prepareStatement(query);

			pst.setString(1, ID);
            		pst.executeUpdate();			 

		}catch (Exception e) {
			 Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
		}finally{
			try{
			 	if (conn != null){
					conn.close();
				}
			}catch (Exception e) {
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
			 	if (pst != null){
					pst.close();
				}
			}catch (Exception e){
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}
		}        


    }//end 


	public boolean EXIST(String ID){
		boolean exist = false;

		String query ="SELECT ID  FROM Invits WHERE ID=?";

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try{  
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			pst = conn.prepareStatement(query);

			pst.setString(1, ID);

			rs = pst.executeQuery();

			if (rs.next()){
    				exist=true;
			}
						 

		}catch (Exception e) {
			 Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
		}finally{
			try{
			 	if (conn != null){
					conn.close();
				}
			}catch (Exception e) {
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
			 	if (pst != null){
					pst.close();
				}
			}catch (Exception e){
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
			 	if (rs != null){
					rs.close();
				}
			}catch (Exception e){
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}
		}

		return exist;     


    }//end 

	public void UPDATE(String ID, String film, int score_updt){

		String query = "UPDATE Invits SET score = ?, totalscore = ? WHERE ID=? AND film=?";

		ArrayList<String> params = this.SELECT(ID, film);

		Connection conn = null;
		PreparedStatement pst = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			pst = conn.prepareStatement(query);


			pst.setInt(1, Integer.valueOf(params.get(2))+score_updt); //score
			pst.setInt(2, Integer.valueOf(params.get(3))-1); //totalscore

			pst.setString(3, ID);
			pst.setString(4, film);

            		pst.executeUpdate();			 

		}catch (Exception e) {
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
		}finally{

			try{
			 	if (conn != null){
					conn.close();
				}
			}catch (Exception e) {
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
			 	if (pst != null){
					pst.close();
				}
			}catch (Exception e){
				Logger.getLogger(DataBaseInvit.class.getName()).log(Level.SEVERE, null, e);
			}

		}       


    }//end 


}//end class
