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
				if(conn != null){
					conn.close();
				 }
			}catch (Exception e) {
			 	e.printStackTrace();
			}
		} 

    }//end connect

	// ajoute un groupe
	public void INSERT_Group(String groupName, String admin, String invitation)
	{
		// vérifie l'existence
		if(!this.EXIST_Groupe(groupName))
		{
			String query = "INSERT INTO groupInfo (groupName, admin, invitation) VALUES(?,?,?);";
			String valeursInput[] = {groupName,admin,invitation};
			String typesInput[] = {"string","string","string"};
	
			this.setBD(query, valeursInput, typesInput);
		}

	}//end INSERT_User

	// vérifie l'existance d'un groupe
	public boolean EXIST_Groupe(String groupName)
	{
		
		String query ="SELECT groupName FROM groupInfo WHERE groupName= ?;";
		String attributs[] = {"groupName"};
		String typesOutput[] = {"string"};
		String valeursInput[] = {groupName};
		String typesInput[] = {"string"};

		ArrayList<Map<String,String>> reponse = this.getBD(query, attributs, typesOutput, valeursInput, typesInput);

		return reponse.size() > 0;     


    }

	// retourne les infos sur ce groupe
	public ArrayList<Map<String,String>> SELECT_group(String groupName)
	{
		String query ="SELECT * FROM groupInfo WHERE groupName= ?;";
		String attributs[] = {"groupName","admin","invitation"};
		String typesOutput[] = {"string","string","string"};
		String valeursInput[] = {groupName};
		String typesInput[] = {"string"};

		ArrayList<Map<String,String>> reponse = this.getBD(query, attributs, typesOutput, valeursInput, typesInput);

		return reponse;     
	}

	// retourne les infos sur tous les groupes
	public ArrayList<Map<String,String>> SELECT_groupAll()
	{
		String query ="SELECT * FROM groupInfo;";
		String attributs[] = {"groupName","admin","invitation"};
		String typesOutput[] = {"string","string","string"};
		String valeursInput[] = {};
		String typesInput[] = {};

		ArrayList<Map<String,String>> reponse = this.getBD(query, attributs, typesOutput, valeursInput, typesInput);

		return reponse;     
	}

	// efface toutes les données relatives au groupe dans la base de donnée
	public void DELETE_Group(String groupName)
	{
		String query ="DELETE FROM groupInfo WHERE groupName = ?;";
		String valeursInput[] = {groupName};
		String typesInput[] = {"string"};

		this.setBD(query, valeursInput, typesInput);

		this.DELETE_GroupUsers(groupName);
		
	}

	// efface les utilisateurs du groupe plus les scores associés au groupe
	public void DELETE_GroupUsers(String groupName)
	{
		String query ="DELETE FROM groupUsers WHERE groupName = ?;";
		String valeursInput[] = {groupName};
		String typesInput[] = {"string"};

		this.setBD(query, valeursInput, typesInput);

		this.DELETE_FilmAll(groupName);
	}



	// ajoute un utilisateur au groupe
	public void INSERT_User(String groupName,String userName)
	{
		// vérifie l'existance
		if(!this.EXIST_User(groupName, userName) && this.EXIST_Groupe(groupName))
		{
			String query = "INSERT INTO groupUsers (groupName, userName) VALUES(?,?);";

			String valeursInput[] = {groupName,userName};
			String typesInput[] = {"string","string"};
	
			this.setBD(query, valeursInput, typesInput);
		}

	}

	// récupère la liste des utilisateurs d'un groupe
	public ArrayList<Map<String,String>> SELECT_users(String groupName)
	{
		String query ="SELECT userName FROM groupUsers WHERE groupName= ?;";
		String attributs[] = {"userName"};
		String typesOutput[] = {"string"};
		String valeursInput[] = {groupName};
		String typesInput[] = {"string"};

		ArrayList<Map<String,String>> reponse = this.getBD(query, attributs, typesOutput, valeursInput, typesInput);

		return reponse;     
	}

	// enlève un utilisateur du groupe
	public void DELETE_GroupUser(String groupName,String userName)
	{
		String query ="DELETE FROM groupUsers WHERE groupName = ? AND userName= ?;";
		String valeursInput[] = {groupName,userName};
		String typesInput[] = {"string","string"};

		this.setBD(query, valeursInput, typesInput);
	}

	// renvoie tous les groupes d'un utilisateur
	public ArrayList<Map<String,String>> SELECT_userGroups(String userName)
	{
		String query ="SELECT groupName FROM groupUsers WHERE userName= ?;";
		String attributs[] = {"groupName"};
		String typesOutput[] = {"string"};
		String valeursInput[] = {userName};
		String typesInput[] = {"string"};

		return this.getBD(query, attributs, typesOutput, valeursInput, typesInput);
	}
	// on vérifie si un utilisateur existe déjà dans un groupe
	public boolean EXIST_User(String groupName, String userName)
	{
		
		String query ="SELECT groupName FROM groupUsers WHERE groupName= ? AND userName= ?;";
		String attributs[] = {"groupName"};
		String typesOutput[] = {"string"};
		String valeursInput[] = {groupName,userName};
		String typesInput[] = {"string","string"};

		ArrayList<Map<String,String>> reponse = this.getBD(query, attributs, typesOutput, valeursInput, typesInput);
		
		return reponse.size() > 0;     


    }

	// on ajoute un nouveau film
	public void Insert_Film(String groupName, Integer idFilm)
	{
		String query = "INSERT INTO groupScores (groupName, filmID, score ) VALUES (?,?,0);";

		String valeursInput[] = {groupName,Integer.toString(idFilm)};
		String typesInput[] = {"string","int"};

		this.setBD(query, valeursInput, typesInput);
	}

	// retourne les id des films du groupe
	public ArrayList<Map<String,String>> SELECT_Film(String groupName)
	{
		String query ="SELECT filmID FROM groupScores WHERE groupName= ?;";
		String attributs[] = {"filmID"};
		String typesOutput[] = {"int"};
		String valeursInput[] = {groupName};
		String typesInput[] = {"string"};

		ArrayList<Map<String,String>> reponse = this.getBD(query, attributs, typesOutput, valeursInput, typesInput);

		return reponse;     
	}

	// retourne les scores des films du groupe associé avec leur film dans l'ordre décroissant des scores
	public ArrayList<Map<String,String>> SELECT_Score(String groupName)
	{
		String query ="SELECT filmID, score FROM groupScores WHERE groupName= ? ORDER BY score DESC;";
		String attributs[] = {"filmID","score"};
		String typesOutput[] = {"int","int"};
		String valeursInput[] = {groupName};
		String typesInput[] = {"string"};

		ArrayList<Map<String,String>> reponse = this.getBD(query, attributs, typesOutput, valeursInput, typesInput);

		return reponse;     
	}

	public void incrementScore(String groupName, int idFilm,boolean increment ) 
	{
		String query = "SELECT score FROM groupScores WHERE groupName= ? AND filmID= ? FOR UPDATE;";
		query += "UPDATE groupScores SET score = score ";
		if(increment)
		{
			query += " + 1;";
		}
		else
		{
			query += " - 1";
		}

		
		String valeursInput[] = {groupName,Integer.toString(idFilm)};
		String typesInput[] = {"string","int"};

		this.setBD(query, valeursInput, typesInput);
	}

	// efface les films du groupe
	public void DELETE_FilmAll(String groupName)
	{
		String query = "DELETE FROM groupScores WHERE groupName= ?;";

		String valeursInput[] = {groupName};
		String typesInput[] = {"string"};

		this.setBD(query, valeursInput, typesInput);		
	}

	// efface un film du groupe
	public void DELETE_Film(String groupName,Integer id)
	{
		String query = "DELETE FROM groupScores WHERE groupName= ? and filmID= ?;";

		String valeursInput[] = {groupName, Integer.toString(id)};
		String typesInput[] = {"string","int"};

		this.setBD(query, valeursInput, typesInput);		
	}	

	public ArrayList<Map<String,String>> SELECT_Vote(String groupName)
	{
		String query ="SELECT * FROM groupScores WHERE groupName= ? ;";
		String attributs[] = {"groupName","userName","filmID","vote"};
		String typesOutput[] = {"string","string","int","int"};
		String valeursInput[] = {groupName};
		String typesInput[] = {"string"};

		ArrayList<Map<String,String>> reponse = this.getBD(query, attributs, typesOutput, valeursInput, typesInput);

		return reponse;  
	}

	// ajoute le vote d'un utilisateur
	public void INSERT_Vote(String groupName,String userName,int filmID, int vote)
	{
		String query = "INSERT INTO userVote(groupName, userName, filmID, vote) VALUES (?,?,?,?);";
		
		String valeursInput[] = {groupName,userName,Integer.toString(filmID),Integer.toString(vote)};
		String typesInput[] = {"string","string","int","int"};

		this.setBD(query, valeursInput, typesInput);		
	}

	// enleve un vote
	public void DELETE_Vote(String groupName,String userName,int filmID)
	{
		String query = "DELETE FROM userVote WHERE groupName=? AND userName=? AND filmID= ?;";
		
		String valeursInput[] = {groupName,userName,Integer.toString(filmID)};
		String typesInput[] = {"string","string","int"};

		this.setBD(query, valeursInput, typesInput);		
	}

	// vérifie si un utilisateur, au sein d'un groupe a voté pour un film
	public boolean EXISTE_Vote(String groupName, String userName, int filmID)
	{
		String query ="SELECT * FROM groupScores WHERE groupName= ? AND userName=? AND filmID=?;";
		String attributs[] = {"groupName","userName","filmID","vote"};
		String typesOutput[] = {"string","string","int","int"};
		String valeursInput[] = {groupName,userName,Integer.toString(filmID)};
		String typesInput[] = {"string","string","int"};

		ArrayList<Map<String,String>> reponse = this.getBD(query, attributs, typesOutput, valeursInput, typesInput);

		return reponse.size() > 0; 
	}

	

	// methode pour écrire dans la base de donnée
	public void setBD(String query,String valeursInput[], String typesInput[])
	 {
		 
		Connection conn = null;
		PreparedStatement pst = null;

		try
		{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			/*PreparedStatement*/ pst = conn.prepareStatement(query);
			// met les valeurs dans le code sql
			for(int index=0; index < valeursInput.length ; index++)
			{
				if(typesInput[index] !=null  &&  typesInput[index].equals("string"))
				{
					pst.setString(index + 1, valeursInput[index]);
				}
				else if(typesInput[index]!=null && typesInput.equals("int"))
				{
					pst.setInt(index + 1, Integer.parseInt(valeursInput[index]));
				}
			}
            pst.executeUpdate();			 

		}catch (SQLException e) {
			System.err.format("SQL State: %s\n%s"+"\n", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			 e.printStackTrace();
		}finally{
			try{
				if(conn != null){
					conn.close();
				}
			}catch (Exception e) {
			 e.printStackTrace();
			}

			try{
				if(pst != null){
					pst.close();
				}
			}catch (Exception e) {
			 e.printStackTrace();
			}
		}        


    }//end set_User

	// methode pour lire dans la base de donnée
	// retourne un tableau de chaque ligne retournée. Une ligne est représenté par un tableau associative de clé
	// le nom de l'attribut
	public ArrayList<Map<String,String>> getBD(String query,String attributs[],String typesOutput[],String valeursInput[], String typesInput[])
	{
		ArrayList<Map<String,String>> params = new ArrayList();
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try 
		{
			Class.forName(JDBC_DRIVER); 
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			/*PreparedStatement*/ pst = conn.prepareStatement(query);

			// on ajoute les valeurs dans le code SQL
			for(int index = 0; index < typesInput.length; index++)
			{
				// selon le type de valeur, on passe par une fonction spécifique
				if(typesInput[index]!=null && typesInput[index].equals("string"))
				{
					pst.setString(index + 1, valeursInput[index]);
				}
				else if(typesInput[index]!=null && typesInput[index].equals("int"))
				{
					pst.setInt(index + 1,Integer.parseInt(valeursInput[index]));
				}
			}

			/*ResultSet*/ rs = pst.executeQuery();

			while (rs.next()) 
			{
				Map<String,String> p = new HashMap<String,String>();
				// on ajoute chaque attribut voulu d'une ligne
				for(int i = 0; i< typesOutput.length;i++)
				{
					// selon le type de valeur, on passe par une fonction spécifique
					if(typesOutput[i] !=null && typesOutput[i].equals("string"))
					{
						p.put(attributs[i],rs.getString(attributs[i]));
					}
					else if(typesOutput[i] !=null && typesOutput[i].equals("int"))
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
				if(conn != null){
					conn.close();
				}
			}catch (Exception e) {
			 e.printStackTrace();
			}

			try{
				if(pst != null){
					pst.close();
				}
			}catch (Exception e) {
			 e.printStackTrace();
			}

			try{
				if(rs != null){
				rs.close();
				}
			}catch (Exception e) {
			 e.printStackTrace();
			}
		}       
		
		return params;

    }//end get_User



}//end class
