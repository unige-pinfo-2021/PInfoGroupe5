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

	class RetourMsg {
		public boolean reussit = true;
		public String msg = "";
	}

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
	public boolean CreateGroup(String groupName,String admin, String invitation,String erreur)
	{
		if(this.db.EXIST_Groupe(groupName))
		{
			erreur = "Nom de groupe déjà existant";
			return false;
		}
		if(this.ExisteInvite(invitation))
		{
			erreur = "invitation déjà utilisée par un autre groupe";
			return false;
		}

		this.db.INSERT_Group(groupName, admin, invitation);
		return true;
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
		
		Map<String,String> retour = new HashMap<String,String>();
		retour.put("msg",""); 
		retour.put("reussit","true");
		// vérifie l'invitation
		if(!this.checkInvite(groupName, invitation))
		{
			retour.put("msg","l'invitation '" + invitation +"'' incorrecte pour le groupe : " + groupName);  
			retour.put("reussit","false");
		}

		// vérifie si l'utilisateur n'existe pas déjà dans le groupe
		if(this.db.EXIST_User(groupName, userName))
		{
			retour.put("msg",userName + " existe déjà dans le groupe " +groupName);
			retour.put("reussit","false") ;
		}

		// ajout de l'utilisateur
		this.db.INSERT_User(groupName, userName);
		return retour;
	}


	// ajoute un utilisateur en connaissant que l'invite
	public Map<String,String> addUser(String userName,String invitation){
		
		Map<String,String> retour = new HashMap<String,String>();
		retour.put("msg",""); 
		retour.put("reussit","true");
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
			retour.put("msg","Aucun groupe n'utilise '"+invitation+"' comme invitation");
			retour.put("reussit","false") ;
			return retour;
		}

		return this.addUser(groupName, userName, invitation);
		
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

	public boolean removeGroup(String groupName,String utilisateur,String erreur)
	{
		// on vérifie si le groupe existe
		if(!this.db.EXIST_Groupe(groupName))
		{
			erreur = "Le groupe '" + groupName + "' n'existe pas";
			return false;
		}

		// on vérifie s'il est admin
		if(!this.checkAdmin(groupName, utilisateur))
		{
			erreur = "Vous n'êtes pas l'admin du groupe";
			return false;
		}


		this.db.DELETE_Group(groupName);
		return true;
	}

	public boolean removeUser(String groupName,String userName, String utilisateur,String erreur)
	{
		// on vérifie si celui qui veut effacer l'utilisateur du groupe est bien l'admin ou bien l'utilisateur lui-même
		if(userName.equals(utilisateur) || this.checkAdmin(groupName, utilisateur))
		{
			this.db.DELETE_GroupUser(groupName,userName);
			return true;
		}
		erreur = "Vous n'êtes ni l'utilisateur concerné, ni l'admin du groupe";
		return false;
	}	

	// on demande un catalogue aléatoire au service "/film" et on ajoute les id des films dans la base de donnée
	public boolean setRandomCatalogue(String groupName,String utilisateur,String erreur)throws IOException, InterruptedException 
	{
		// on vérifie si le groupe existe
		if(!this.db.EXIST_Groupe(groupName))
		{
			erreur = "Le groupe '"+groupName+"' n'existe pas";
			return false;
		}

		// on vérifie si celui qui veut créer un catalogue est bien l'admin
		if(!this.checkAdmin(groupName, utilisateur))
		{
			erreur = "Vous n'êtes pas l'admin du groupe";
			return false;
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
			
		return true;
	}

	// créer le catalgue du groupe à partir d'une liste de film au format json
	public boolean setCatalogue(String groupName, String utilisateur ,String CatalogueJSON , String erreur)
	{
		// on vérifie si le groupe existe
		if(!this.db.EXIST_Groupe(groupName))
		{
			erreur = "Le groupe '"+groupName+"' n'existe pas";
			return false;
		}

		// on vérifie si celui qui veut créer un catalogue est bien l'admin
		if(!this.checkAdmin(groupName, utilisateur))
		{
			erreur = "Vous n'êtes pas l'admin du groupe";
			return false;
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
			
		return true;
	}

	// prépare la requete json à envoyer au service "/selector" afin d'obtenir des films plus pertinants
	public boolean calculNewCatalogue(String groupName,String utilisateur , String erreur)throws IOException, InterruptedException
	{
		ArrayList<Map<String,String>> scores = this.db.SELECT_Score(groupName);
		int top = 5;
		if(scores.size() < top)
		{
			top = scores.size();
		}

		if(top < 1)
		{
			erreur = "Pas de film pour le groupe '" +groupName+" sur lesquelles se baser.";
			return false;
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
		return this.setCatalogue(groupName, utilisateur, newCatalogue,erreur);
		
	}
	
	//retourne les id des films du groupe
	public int[] getCatalogue(String groupName,String userName,String erreur)
	{
		// on vérifie si l'utilisateur se trouve dans le groupe
		if(! this.db.EXIST_User(groupName, userName))
		{	
			erreur = userName + " ne se trouve pas dans le groupe " +groupName;
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
	// sinon on décremente.
	public boolean incrementScore(String groupName, String invitation, int idFilm,boolean increment ,String erreur ) 
	{
		if(!this.db.EXIST_Groupe(groupName))
		{
			erreur = "Le groupe '"+groupName+" n'existe pas";
			return false;
		}
		if(!this.checkInvite(groupName, invitation))
		{
			erreur = "pas membre du groupe: "+groupName;
			return false;
		}

		this.db.incrementScore(groupName, idFilm, increment);
		return true;
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

	public boolean deleteCatalogue(String groupName,String utilisateur, String erreur)
	{
		if(!this.checkAdmin(groupName, utilisateur))
		{
			erreur = "Vous n'êtes pas l'admin du groupe";
			return false;
		}

		this.db.DELETE_FilmAll(groupName);
		return true;
	}

	// retourne les votes du groupes. Format: username : [idFilm , vote]. vote = +- 1 et 0 si pas encore voté
	public Map<String, Map<Integer,Integer>> getVote(String groupName)
	{
		ArrayList<Map<String,String>> votes = this.db.SELECT_Vote(groupName);
		Map<String, Map<Integer,Integer>> retour = new HashMap<String, Map<Integer,Integer>>();
		for(Map<String,String> ligne: votes)
		{
			String userName = ligne.get("userName");
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

		try(OutputStream os = con.getOutputStream()) {
			byte[] input = requete.getBytes("utf-8");
			os.write(input, 0, input.length);			
		}
		catch(IOException e){}

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

	
}//end class
