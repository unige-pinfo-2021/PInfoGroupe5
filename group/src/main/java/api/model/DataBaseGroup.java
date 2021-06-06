package api.model;

import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseGroup{
	
	private String url;
	private String username;
	private String password;

	private String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //"org.postgresql.Driver"

	public DataBaseGroup() throws Exception{

		Encryption encrypt = new Encryption();
		this.url = encrypt.getu();
		this.username = encrypt.getg();
		this.password = encrypt.getp();


	}//end constructor


	public void connect(){

		Connection conn = null;
		try{
			Class.forName(JDBC_DRIVER); 
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			
		}catch (Exception e) {
			   Logger.getLogger(DataBaseGroup.class.getName()).log(Level.SEVERE, null, e);

		}finally{
			try{
				if(conn != null){
					conn.close();
				 }
			}catch (Exception e) {
				Logger.getLogger(DataBaseGroup.class.getName()).log(Level.SEVERE, null, e);
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
		String query2 = "UPDATE groupScores SET score = score ";
		if(increment)
		{
			query2 += " + 1";
		}
		else
		{
			query2 += " - 1";
		}

		query2 += " WHERE groupName= ? AND filmID= ?;";

		String q[] ={query,query2};
		String valeursInput[] = {groupName,Integer.toString(idFilm)};
		String typesInput[] = {"string","int"};
		

		ArrayList<String[]> valeursInputs = new ArrayList<String[]>();
		valeursInputs.add(valeursInput);
		valeursInputs.add(valeursInput);
		ArrayList<String[]> typesInputs = new ArrayList<String[]>();
		typesInputs.add(typesInput);
		typesInputs.add(typesInput);
		String typeQuery[] = {"get","set"};

		this.setBDTransaction(q, valeursInputs, typesInputs,typeQuery);
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

	// obtenir les votes des utilisateurs du group
	public ArrayList<Map<String,String>> SELECT_Vote(String groupName)
	{
		String query ="SELECT * FROM userVote WHERE groupName= ? ;";
		String attributs[] = {"groupName","userName","filmID","vote"};
		String typesOutput[] = {"string","string","int","int"};
		String valeursInput[] = {groupName};
		String typesInput[] = {"string"};

		ArrayList<Map<String,String>> reponse = this.getBD(query, attributs, typesOutput, valeursInput, typesInput);

		return reponse;  
	}

	// obtenir le vote d'un utilisateur pour un film
	public int SELECT_Vote(String groupName,String userName,int filmID )
	{
		String query ="SELECT vote FROM userVote WHERE groupName= ? AND userName=? AND filmID=? ;";
		String attributs[] = {"vote"};
		String typesOutput[] = {"int"};
		String valeursInput[] = {groupName,userName,Integer.toString(filmID)};
		String typesInput[] = {"string","string","int"};

		ArrayList<Map<String,String>> reponse = this.getBD(query, attributs, typesOutput, valeursInput, typesInput);

		int retour = Integer.parseInt(reponse.get(0).get("vote"));
		
		return retour;  
	}	

	// ajoute le vote d'un utilisateur
	public void INSERT_Vote(String groupName,String userName,int filmID, int vote)
	{
		// si un le même utilisateur, dans le même groupe a voté pour le même film
		// son ancien vote est effacé
		if(EXISTE_Vote(groupName,userName,filmID))
		{
			this.DELETE_Vote(groupName, userName, filmID);
		}

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
		String query ="SELECT * FROM userVote WHERE groupName= ? AND userName=? AND filmID=?;";
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
			pst = conn.prepareStatement(query);
			// met les valeurs dans le code sql
			for(int index=0; index < valeursInput.length ; index++)
			{
				if(typesInput[index] !=null  &&  typesInput[index].equals("string"))
				{
					pst.setString(index + 1, valeursInput[index]);
				}
				else if(typesInput[index]!=null && typesInput[index].equals("int"))
				{
					pst.setInt(index + 1, Integer.parseInt(valeursInput[index]));
				}
			}

				pst.executeUpdate();			 

		}catch (Exception e) {
			Logger.getLogger(DataBaseGroup.class.getName()).log(Level.SEVERE, null, e);

		}finally{
			try{
				if(conn != null){
					conn.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseGroup.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
				if(pst != null){
					pst.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseGroup.class.getName()).log(Level.SEVERE, null, e);
			}

		}        


    }//end 

	// permet de faire une transaction de sql
	public void setBDTransaction(String query[],ArrayList<String[]> valeursInputs, ArrayList<String[]> typesInputs,String typeQuery[])
	 {
		 
		Connection conn = null;
		PreparedStatement pst[] = new PreparedStatement[query.length];
		for (int i=0; i< query.length; i++)
		{
			pst[i] = null;
		}

		try
		{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			conn.setAutoCommit(false);
			for(int i=0; i < query.length; i++)
			{
				pst[i] = conn.prepareStatement(query[i]);
				for(int index=0; index < valeursInputs.get(i).length ; index++)
				{
					if(typesInputs.get(i)[index] !=null  &&  typesInputs.get(i)[index].equals("string"))
					{
						pst[i].setString(index + 1, valeursInputs.get(i)[index]);
					}
					else if(typesInputs.get(index)!=null && typesInputs.get(i)[index].equals("int"))
					{
						pst[i].setInt(index + 1, Integer.parseInt(valeursInputs.get(i)[index]));
					}
				}
				if(typeQuery[i].equals("set"))
				{
					pst[i].executeUpdate();
				}
				if(typeQuery[i].equals("get"))
				{
					pst[i].executeQuery();
				}
				
			}
			
			// met les valeurs dans le code sql
			conn.commit();

						 

		}catch (Exception e) {
			Logger.getLogger(DataBaseGroup.class.getName()).log(Level.SEVERE, null, e);

		}finally{
			try{
				if(conn != null){
					conn.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseGroup.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
				for(int i=0; i<query.length; i++)
				{
					if(pst[i] != null)
					{
						pst[i].close();
					}
				}

			}

			catch (Exception e) {
				Logger.getLogger(DataBaseGroup.class.getName()).log(Level.SEVERE, null, e);
			}

		}        


    }//end 
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
			pst = conn.prepareStatement(query);

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

			rs = pst.executeQuery();

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
						 

		}catch (Exception e) {
			Logger.getLogger(DataBaseGroup.class.getName()).log(Level.SEVERE, null, e);

		}finally{
			try{
				if(conn != null){
					conn.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseGroup.class.getName()).log(Level.SEVERE, null, e);
			}

			try{
				if(pst != null){
					pst.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseGroup.class.getName()).log(Level.SEVERE, null, e);
			}
			
			try{
				if(rs != null){
					rs.close();
				}

			}catch (Exception e) {
				Logger.getLogger(DataBaseGroup.class.getName()).log(Level.SEVERE, null, e);
			}	

		}       
		
		return params;

    }//end 



}//end class
