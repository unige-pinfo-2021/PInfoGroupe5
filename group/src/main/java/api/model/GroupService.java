package api.model;

import java.util.*;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import org.json.*;
import java.io.FileWriter;

import java.net.*;
import java.io.*;

public class GroupService{


	private DataBaseGroup db;
	
	public GroupService()throws IOException, InterruptedException
	{
		FileWriter myWriter = new FileWriter("/etc/hosts");
		try {
			
			myWriter.write("\n 129.194.10.130 tindfilm");
			
		
		this.db = new DataBaseGroup("database.properties");
		} 
		catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		finally
		{
			myWriter.close();
		}
		
	}//end constructor


	// créer un nouveau groupe
	public Map<String, String> CreateGroup(String groupName,String admin, String invitation)
	{
		String erreur = "";
		boolean reussit = true;
		if(this.db.EXIST_Groupe(groupName))
		{
			erreur += "Nom de groupe déjà existant \n";
			reussit = false;
		}
		if(this.ExisteInvite(invitation))
		{
			erreur += "invitation déjà utilisée par un autre groupe \n";
			reussit = false;
		}

		this.db.INSERT_Group(groupName, admin, invitation);
		return msgRetour(reussit, erreur);
	}

	// retourne les infos d'un groupe. S'il n'existe pas, retourne un tableau vide.
	public Map<String,String> getGroup(String groupName)
	{
		Map<String,String> retour = new HashMap<String,String>();
		// on vérifie que le groupe existe pour ne pas avoir d'erreur
		// lorsqu'on essaiera de récupérer les infos du tableau
		if(this.db.EXIST_Groupe(groupName))
		{
			retour = this.db.SELECT_group(groupName).get(0);
		}
		return retour;
		
	}


	//retourne les infos de tous les groupes
	public ArrayList<Map<String,String>> getGroupAll()
	{
		return this.db.SELECT_groupAll();
	}


	// retourne une liste de nom des utilisateurs du groupe
	public ArrayList<String> getGroupUsers(String groupName)
	{
		// on récupère les infos
		ArrayList<Map<String,String>> infos =  this.db.SELECT_users(groupName);
			
		// on prépare l'objet de retour
		ArrayList<String> retour = new ArrayList<String>();

		// remplie la liste
		for(Map<String,String> ligne : infos)
		{
			retour.add(ligne.get("userName"));
		}

		return retour;
	
	}
	

	// retourne pour chaque groupe, ses utilisateurs.
	// retourne un tableau associatif où la clé est le nom de groupe et la valeur un tableau de nom d'utilisateurs
	public Map<String,ArrayList<String>> getAllGroupUsers()
	{
		// on récupère les noms des groupes
		ArrayList<Map<String,String>> infos =  this.db.SELECT_groupAll();

		// on prépare la valeur de retour
		Map<String,ArrayList<String>> retour = new HashMap<String,ArrayList<String>>();

		for(Map<String,String> ligne : infos)
		{
			String groupName = ligne.get("groupName");
			retour.put(groupName,this.getGroupUsers(groupName));
		}

		return retour;
	}


	// ajoute un utilisateur
	public Map<String,String> addUser(String groupName,String userName,String invitation){
		
		String erreur = "";
		boolean reussit = true;
		// vérifie l'invitation
		if(!this.checkInvite(groupName, invitation))
		{
			erreur += "l'invitation '" + invitation +"'' incorrecte pour le groupe : " + groupName + "\n";  
			reussit = false;
		}

		// vérifie si l'utilisateur n'existe pas déjà dans le groupe
		if(this.db.EXIST_User(groupName, userName))
		{
			erreur += userName + " existe déjà dans le groupe " +groupName + "\n";
			reussit = false;
		}

		// ajout de l'utilisateur
		this.db.INSERT_User(groupName, userName);
		return msgRetour(reussit, erreur);
	}


	// ajoute un utilisateur en connaissant que l'invite
	public Map<String,String> addUser(String userName,String invitation){
		
		String erreur = "";
		boolean reussit = true;
		// récupère tous les noms de groupes
		ArrayList<Map<String,String>> groups = this.getGroupAll();
		String groupName = "";
		// on cherche le groupe avec la même invitation. On choisira le premier.
		for(Map<String,String> ligne: groups)
		{
			if(invitation.equals(ligne.get("invitation")))
			{
				groupName = ligne.get("groupName");
				break;
			}
		}
		if(groupName.equals(""))
		{
			erreur +="Aucun groupe n'utilise '"+invitation+"' comme invitation \n";
			reussit = false;	
		}

		Map<String, String> r = this.addUser(groupName, userName, invitation);
		reussit = true && Boolean.parseBoolean(r.get("reussit"));
		erreur += r.get("msg");
		return msgRetour(reussit, erreur);
		
	}	

	// retourne des infos plus précises sur un utilisateur
	// en passant par le service "/user"
	public String getUser(String userName)throws IOException, InterruptedException
	{
		String url = "http://tindfilm/user/" + userName;
        String JSONuser = get(url);
        return JSONuser;
	}

	// retourne les groupes d'un utilisateur
	public ArrayList<String> getUserGroups(String userName)
	{
		ArrayList<Map<String,String>> info = this.db.SELECT_userGroups(userName);
		ArrayList<String> retour = new ArrayList<String>();
		for(Map<String,String> ligne : info)
		{
			retour.add(ligne.get("groupName"));
		}
		return retour;
	}

	public Map<String, String> removeGroup(String groupName,String utilisateur)
	{
		String erreur = "";
		boolean reussit = true;
		// on vérifie si le groupe existe
		if(!this.db.EXIST_Groupe(groupName))
		{
			erreur += "Le groupe '" + groupName + "' n'existe pas \n";
			reussit = false;
		}

		// on vérifie s'il est admin
		if(!this.checkAdmin(groupName, utilisateur))
		{
			erreur += "Vous n'êtes pas l'admin du groupe\n";
			reussit = false;
		}


		this.db.DELETE_Group(groupName);
		return msgRetour(reussit, erreur);
	}

	public Map<String, String> removeUser(String groupName,String userName, String utilisateur)
	{
		String erreur = "";
		boolean reussit = true;

		// on vérifie si celui qui veut effacer l'utilisateur du groupe est bien l'admin ou bien l'utilisateur lui-même
		if(userName.equals(utilisateur) || this.checkAdmin(groupName, utilisateur))
		{
			this.db.DELETE_GroupUser(groupName,userName);
			return msgRetour(reussit, erreur);
		}
		erreur += "Vous n'êtes ni l'utilisateur concerné, ni l'admin du groupe\n";
		return msgRetour(reussit, erreur);
	}	

	// on demande un catalogue aléatoire au service "/film" et on ajoute les id des films dans la base de donnée
	public Map<String, String> setRandomCatalogue(String groupName,String utilisateur)throws IOException, InterruptedException 
	{
		String erreur = "";
		boolean reussit = true;
		// on vérifie si le groupe existe
		if(!this.db.EXIST_Groupe(groupName))
		{
			erreur += "Le groupe '"+groupName+"' n'existe pas\n";
			reussit=false;
		}

		// on vérifie si celui qui veut créer un catalogue est bien l'admin
		if(!this.checkAdmin(groupName, utilisateur))
		{
			erreur += "Vous n'êtes pas l'admin du groupe";
			reussit = false;
		}

		if(!reussit)
		{
			return msgRetour(reussit, erreur);
		}
		// on efface les anciens films du groupe
		this.db.DELETE_FilmAll(groupName);

		// on demande une liste aléatoire de films
		String catalogue = get("http://tindfilm/film");
		JSONArray jsonCatalogue = new JSONArray(catalogue);
		// on associe les id des films au groupe et on l'ajoute à la base de donnée
		for(int i = 0; i< jsonCatalogue.length(); i++)
		{
			JSONObject jsonfilm = jsonCatalogue.getJSONObject(i);
			this.db.Insert_Film(groupName,jsonfilm.getInt("id"));
		}
			
		return msgRetour(reussit, erreur);
	}

	// créer le catalgue du groupe à partir d'une liste de film au format json
	public Map<String, String> setCatalogue(String groupName, String utilisateur ,String CatalogueJSON )
	{
		String erreur = "";
		boolean reussit = true;
		// on vérifie si le groupe existe
		if(!this.db.EXIST_Groupe(groupName))
		{
			erreur += "Le groupe '"+groupName+"' n'existe pas\n";
			reussit = false;
		}

		// on vérifie si celui qui veut créer un catalogue est bien l'admin
		if(!this.checkAdmin(groupName, utilisateur))
		{
			erreur += "Vous n'êtes pas l'admin du groupe\n";
			reussit = false;
		}

		if(!reussit)
		{
			return msgRetour(reussit, erreur);
		}

		// on efface les anciens films du groupe
		this.db.DELETE_FilmAll(groupName);

		// on transforme le string json en jsonarray	
		JSONArray jsonCatalogue = new JSONArray(CatalogueJSON);
		// on ajoute les id des films au groupe
		for(int i = 0; i< jsonCatalogue.length(); i++)
		{
			JSONObject jsonfilm = jsonCatalogue.getJSONObject(i);
			this.db.Insert_Film(groupName,jsonfilm.getInt("id"));
		}
			
		return msgRetour(reussit, erreur);
	}

	// prépare la requete json à envoyer au service "/selector" afin d'obtenir des films plus pertinants
	public Map<String, String> calculNewCatalogue(String groupName,String utilisateur )throws IOException, InterruptedException
	{
		String erreur = "";
		boolean reussit = true;

		ArrayList<Map<String,String>> scores = this.db.SELECT_Score(groupName);
		int top = 5;
		if(scores.size() < top)
		{
			top = scores.size();
		}

		if(top < 1)
		{
			erreur += "Pas de film pour le groupe '" +groupName+" sur lesquelles se baser.\n";
			return msgRetour(false, erreur);
		}

		int idTopFilm[] = new int[top]; 

		// on construit le json de requete
		JSONObject requete = new JSONObject();
		Integer i = 0;
		for(i = 0; i< top ;i++)
		{
			requete.put(i.toString(),scores.get(i).get("filmID"));
		}
		
		// on envoie la requête et attend le nouveau catalogue
		String newCatalogue = this.post("http://tindfilm/selector", requete.toString());
		// on inscrit le catalogue dans la base de donnée
		return this.setCatalogue(groupName, utilisateur, newCatalogue);
		
	}
	
	//retourne les id des films du groupe
	public int[] getCatalogue(String groupName,String userName)
	{
		// on vérifie si l'utilisateur se trouve dans le groupe
		if(! this.db.EXIST_User(groupName, userName))
		{	
			return new int[0];
		}

		// on récupère les films
		ArrayList<Map<String,String>> films = this.db.SELECT_Film(groupName);
		int retour[] = new int[films.size()];

		for(int i = 0; i< films.size(); i++)
		{
			retour[i] = Integer.parseInt(films.get(i).get("filmID"));
		}

		return retour;

	}

	// incrémente sans problème de conccurence le score des films. si increment = true, on incremente
	// sinon on décremente. On ajoute ensuite que l'utilisateur a voté
	public Map<String, String> incrementScore(String groupName,String userName, int filmID,boolean increment) 
	{
		String erreur = "";
		boolean reussit = true;
		if(!this.db.EXIST_Groupe(groupName))
		{
			erreur += "Le groupe '"+groupName+" n'existe pas\n";
			return msgRetour(false,erreur);
		}
		if(!this.db.EXIST_User(groupName, userName))
		{
			erreur += userName + " n'est pas membre du groupe "+groupName + ".\n";
			return msgRetour(false,erreur);
		}

		this.db.incrementScore(groupName, filmID, increment);
		int vote = -1;
		if(increment)
		{
			vote =1;
		}
		this.db.INSERT_Vote(groupName, userName, filmID, vote);
		return msgRetour(reussit, erreur);
	}

	// retourne le score des film du groupe
	public ArrayList<Map<String,Integer>> getScores(String groupName)
	{
		// récupère les infos de la base de donnée
		ArrayList<Map<String,String>> infos = this.db.SELECT_Score(groupName);
		// on transforme les nombres en int 
		ArrayList<Map<String,Integer>> retour = new ArrayList<Map<String,Integer>>();
		for(Map<String,String> ligne : infos )
		{
			Map<String,Integer> l = new HashMap<String,Integer>();
			l.put("filmID", Integer.parseInt(ligne.get("filmID")));
			l.put("score", Integer.parseInt(ligne.get("score")));
			retour.add(l);
		}

		return retour;
	}

	public Map<String, String> deleteCatalogue(String groupName,String utilisateur)
	{
		String erreur = "";
		boolean reussit = true;
		if(!this.checkAdmin(groupName, utilisateur))
		{
			erreur += "Vous n'êtes pas l'admin du groupe\n";
			return msgRetour(false, erreur);
		}

		this.db.DELETE_FilmAll(groupName);
		return msgRetour(reussit, erreur);
	}

	// retourne les votes du groupes. Format: {username : [idFilm , vote]}. vote = +- 1 
	public Map<String, Map<Integer,Integer>> getVote(String groupName)
	{
		ArrayList<Map<String,String>> votes = this.db.SELECT_Vote(groupName);
		Map<String, Map<Integer,Integer>> retour = new HashMap<String, Map<Integer,Integer>>();
		for(Map<String,String> ligne: votes)
		{
			Map<Integer,Integer> vote = new HashMap<Integer,Integer>();
			vote.put(Integer.parseInt(ligne.get("filmID")), Integer.parseInt(ligne.get("vote")));
			retour.put(ligne.get("userName"),vote);
		}
		return retour;
	}

	// vérifie si un autre groupe possède la même invitation
	private boolean ExisteInvite(String invitation)
	{
		// on récupère tous les groupes
		ArrayList<Map<String,String>> groups = this.db.SELECT_groupAll();
		
		// on vérifie chaque ligne
		for(Map<String,String> ligne : groups)
		{
			if(invitation.equals(ligne.get("invitation")))
			{
				return true;
			}
		}
		return false;
	}
	// vérifie la bonne invitation pour le bon groupe
	private boolean checkInvite(String groupName, String invitation)
	{
		if(this.db.EXIST_Groupe(groupName))
		{
			ArrayList<Map<String,String>> infos = this.db.SELECT_group(groupName);
			return invitation.equals(infos.get(0).get("invitation"));
		}
		return false;
	}

	// vérifie si l'utilisateur est l'admin
	private boolean checkAdmin(String groupName, String admin)
	{
		if(this.db.EXIST_Groupe(groupName))
		{
			ArrayList<Map<String,String>> infos = this.db.SELECT_group(groupName);
			return admin.equals(infos.get(0).get("admin"));
		}
		return false;
	}
	
	/* permet de faire une requête http POST */
	private static String post(String adresse, String requete)throws IOException, InterruptedException 
    {
		URL obj = new URL(adresse);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		try {
			byte[] input = requete.getBytes("utf-8");
			os.write(input, 0, input.length);			
		} catch(IOException e){} 
		finally {
			if(os == null) {}
			else {os.close();}
		}
		

		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			try(BufferedReader br = new BufferedReader(
				new InputStreamReader(con.getInputStream(), "utf-8"))) {
				  StringBuilder response = new StringBuilder();
				  String responseLine = null;
				  while ((responseLine = br.readLine()) != null) {
					  response.append(responseLine.trim());
				  }
				  System.out.println(response.toString());
				  return response.toString();
			  }
		} else 
		{
			return "POST request not worked";
		}	
    }

	/* permet de faire une requête http GET  et retourne la réponse en JSON*/
	private String get(String adresse)throws IOException, InterruptedException 
	{
		URL obj = new URL(adresse);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			return response.toString();
		} else 
		{
			return "GET request not worked";
		}	
	}

	private Map<String,String> msgRetour(boolean reussit,String erreur)
	{
		Map<String,String> retour = new HashMap<String,String>();
		retour.put("msg",erreur);
		if(reussit)
		{
			retour.put("reussit","true");
		}
		else
		{
			retour.put("reussit","false");
		}
		return retour;
		
	}
	
}//end class
