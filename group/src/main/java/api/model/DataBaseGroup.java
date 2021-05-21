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

public class DataBaseGroup{
	
	private String url;
	private String username;
	private String password;

	private String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //"org.postgresql.Driver"

	public DataBaseGroup(String path){
		//Properties props =  new DataBaseUserProperties().readProperties(path);

		//this.url=props.getProperty("db.url");
		//this.username=props.getProperty("db.user");
		//this.password=props.getProperty("db.passwd");
		this.url="jdbc:mysql://129.194.10.130:3306/tinderfilmBD";
		this.username = "groupe5";
		this.password = "12345";
	}//end constructor


	public void try_connect(){

		
		Connection conn = null;
		try{
			Class.forName(JDBC_DRIVER); 
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			if (conn != null) 
			{
				System.out.println("\n"+"Connection established!");

			} 
			else 
			{
				System.out.println("Failed to make connection!");
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

    }//end connect

	
	public void INSERT_Group(String groupeName, String admin, String invitation){
		if(!this.EXIST_Groupe(groupeName))
		{
			String query = "INSERT INTO groupe (groupeName, admin, invitation) VALUES('"+groupeName+"','"+admin+"','"+invitation+"');";
			this.setBD(query);
		}

	}//end INSERT_User

	

	public void INSERT_User(String groupeName,String userName)
	{
		if(!this.EXIST_User(groupeName, userName))
		{
			String query = "INSERT INTO groupeUsers (groupeName, userName) VALUES('" + groupeName + "', '" + userName +"' );";
			this.setBD(query);
		}

	}

	public void DELETE_Group(String groupeName)
	{
		String query ="DELETE FROM groupe WHERE groupeName = '" + groupeName + "' ;";
		this.setBD(query);
		query ="DELETE FROM groupeUsers WHERE groupeName = '" + groupeName + "' ;";
		this.setBD(query);
		query ="DELETE FROM groupeScores WHERE groupeName = '" + groupeName + "' ;";
		this.setBD(query);
	}

	public void DELETE_GroupFilmScore(String groupeName)
	{
		String query ="DELETE FROM groupeScores WHERE groupeName = '" + groupeName + "';";
		this.setBD(query);
	}

	public void DELETE_GroupUsers(String groupName){
		String query ="DELETE FROM groupeUsers WHERE groupeName = '" + groupName + "';";
		this.setBD(query);
	}

	public void DELETE_GroupUser(String groupName,String userName){
		String query ="DELETE FROM groupeUsers WHERE groupeName = '" + groupName + "' AND userName='" + userName +"';";
		this.setBD(query);
	}

	public void Insert_Film(String groupeName, int idFilm)
	{
		Integer id = idFilm;
		String query = "INSERT INTO groupeScores (groupeName, filmID, score ) VALUES ('"+groupeName+"',"+ id.toString()+",0);";
		this.setBD(query);
	}

	public void DELETE_Film(String groupeName)
	{
		String query = "DELETE FROM groupeScores WHERE groupeName='"+groupeName+"';";
		this.setBD(query);		
	}

	 public void setBD(String query)
	 {
		 
		Connection conn = null;

		try
		{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			PreparedStatement pst = conn.prepareStatement(query);
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

	public ArrayList<Map<String,String>> getBD(String query,String attributs[],String types[],int size)
	{
		ArrayList<Map<String,String>> params = new ArrayList();
		
		Connection conn = null;

		try 
		{
			Class.forName(JDBC_DRIVER); 
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) 
			{
				
				Map<String,String> p = new HashMap<String,String>();
				// on ajoute chaque attribut voulu de la ligne
				for(int i = 0; i<size;i++)
				{
					if(types[i] == "string")
					{
						p.put(attributs[i],rs.getString(attributs[i]));
					}

					if(types[i] == "int")
					{
						// permet de passer de int à string
						Integer a = rs.getInt(attributs[i]);
						p.put(attributs[i],a.toString());
					}
					
				}
				// on ajoute la ligne
				params.add(p);
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

	// on vérifie si un groupe existe déjà
	public boolean EXIST_Groupe(String groupeName)
	{
		boolean exist = false;

		String query ="SELECT groupeName FROM groupe WHERE groupeName='"+groupeName+"';";
		Connection conn = null;

		try 
		{
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


    }//end get_User

	// on vérifie si un utilisateur existe déjà dans un groupe
	public boolean EXIST_User(String groupeName, String userName)
	{
		boolean exist = false;

		String query ="SELECT * FROM groupeUsers WHERE groupeName='"+groupeName+"' AND userName='" + userName+"';";

		Connection conn = null;

		try 
		{
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


    }//end get_User


}//end class
