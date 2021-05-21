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
		try {
			FileWriter myWriter = new FileWriter("/etc/hosts");
			myWriter.write("\n 129.194.10.130 tindfilm");
			myWriter.close();
		
		this.db = new DataBaseGroup("database.properties");
		} 
		catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		  }
		
	}//end constructor

	public void CreateGroupe(String groupeName,String admin, String invitation)
	{
		this.db.INSERT_Group(groupeName, admin, invitation);
	}

	// structure réponse { nomGroupe: list[utilisateurs] }. Donne toutes les lignes avec le bon nom de groupe
	public Map<String,ArrayList<String>> getGroupUsers(String groupeName)
	{
	
		// on récupère les infos de la table groupeUsers
		String query ="SELECT userName FROM groupeUsers WHERE groupeName='"+groupeName+"';";
		String attribut[] = {"userName"};
		String type[] = {"string"};
		int nombreAttributs = 1;
		ArrayList<Map<String,String>> infos =  this.db.getBD(query,attribut,type,nombreAttributs);
			
		// on prépare l'objet de retour
		Map<String,ArrayList<String>> retour = new HashMap<String,ArrayList<String>>();// objet de retour
		ArrayList<String> liste = new ArrayList<String>();// liste des nom d'utilisateur

		for(Map<String,String> ligne : infos)// remplie la liste
		{
			liste.add(ligne.get("userName"));
		}

		retour.put(groupeName,liste);// associe la liste au nom de groupe
		return retour;
	
	}
	
	public Map<String,ArrayList<String>> getALLGroupUsers()
	{
		// on récupère les noms des groupes existant ayant des utilisateurs
		String query ="SELECT DISTINCT groupeName FROM groupeUsers ;";
		String attributs[] = {"groupeName"};
		String types[] = {"string"};
		int nombreAttributs = 1;
		ArrayList<Map<String,String>> infos =  this.db.getBD(query,attributs,types,nombreAttributs);

		// on met dans une liste les noms de groupe
		ArrayList<String> nomGroupes = new ArrayList<String>();
		for(Map<String,String> ligne : infos)
		{
			nomGroupes.add(ligne.get("groupeName"));
		}

		// pour chaque nom, on appelle la méthode getGroupeUsers et ajoute le résultat à l'objet de retour
		Map<String,ArrayList<String>> retour = new HashMap<String,ArrayList<String>>();
		for(String nom : nomGroupes)
		{
			// on réutilise la méthode précdente pour récupérér les noms d'utilisateurs
			Map<String,ArrayList<String>> listeUsers = this.getGroupUsers(nom);

			retour.put(nom,listeUsers.get(nom));
		}
		return retour;
	}

	public void addUser(String groupName,String userName,String invitation){
		
		// avant de d'ajouter un utilisateur, on vérifie s'il connait l'invitation du groupe
		String query = "SELECT invitation FROM groupe WHERE groupeName='"+ groupName +"';";
		String attributs[] = {"invitation"};
		String types[] = {"string"};
		int nombreAttributs = 1;

		ArrayList<Map<String,String>> invite = this.db.getBD(query, attributs, types, nombreAttributs);
		if(invitation.equals(invite.get(0).get("invitation")))
		{
			this.db.INSERT_User(groupName,userName);
		}
		
		
	}

	public String getUser(String userName)throws IOException, InterruptedException
	{
		String url = "http://tindfilm/user/" + userName;
        String JSONuser = get(url);
        return JSONuser;
	}

	
	public void removeGroup(String groupName,String admin)
	{
		// on vérifie si celui qui veut effacer le groupe est bien l'admin
		String query = "SELECT admin FROM groupe WHERE groupeName='"+groupName+"';";
		String attributs[] = {"admin"};
		String types[] = {"string"};
		int nombreAttributs = 1;
		ArrayList<Map<String,String>> administrateur = this.db.getBD(query, attributs, types, nombreAttributs);

		if(admin.equals(administrateur.get(0).get("admin")))
		{
			this.db.DELETE_Group(groupName);
		}
		
	}

	public void removeUser(String groupName,String userName, String admin)
	{
		// on vérifie si celui qui veut effacer l'utilisateur du groupe est bien l'admin
		String query = "SELECT admin FROM groupe WHERE groupeName='"+groupName+"';";
		String attributs[] = {"admin"};
		String types[] = {"string"};
		int nombreAttributs = 1;
		ArrayList<Map<String,String>> administrateur = this.db.getBD(query, attributs, types, nombreAttributs);

		if(admin.equals(administrateur.get(0).get("admin")))
		{
			this.db.DELETE_GroupUser(groupName,userName);
		}
		
	}	

	public void setRandomCatalogue(String groupeName,String admin)throws IOException, InterruptedException 
	{
		// on vérifie si celui qui veut créer un catalogue est bien l'admin
		String query = "SELECT admin FROM groupe WHERE groupeName='"+groupeName+"';";
		String attributs[] = {"admin"};
		String types[] = {"string"};
		int nombreAttributs = 1;
		ArrayList<Map<String,String>> administrateur = this.db.getBD(query, attributs, types, nombreAttributs);

		if(admin.equals(administrateur.get(0).get("admin")))
		{
			// on efface les anciens films du groupe
			this.db.DELETE_Film(groupeName);

			// on demande une liste aléatoire de films
			String catalogue = get("http://tindfilm/film");
			JSONArray jsonCatalogue = new JSONArray(catalogue);
			for(int i = 0; i< jsonCatalogue.length(); i++)
			{
				JSONObject jsonfilm = jsonCatalogue.getJSONObject(i);
				this.db.Insert_Film(groupeName,jsonfilm.getInt("id"));
			}
			
		}
	}

	public void setCatalogue(String groupeName, String admin ,String CatalogueJSON)
	{
		// on vérifie si celui qui veut créer un catalogue est bien l'admin
		String query = "SELECT admin FROM groupe WHERE groupeName='"+groupeName+"';";
		String attributs[] = {"admin"};
		String types[] = {"string"};
		int nombreAttributs = 1;
		ArrayList<Map<String,String>> administrateur = this.db.getBD(query, attributs, types, nombreAttributs);

		if(admin.equals(administrateur.get(0).get("admin")))
		{
			// on efface les anciens films du groupe
			this.db.DELETE_Film(groupeName);

			// on transforme le string json en jsonarray
			
			JSONArray jsonCatalogue = new JSONArray(CatalogueJSON);
			for(int i = 0; i< jsonCatalogue.length(); i++)
			{
				JSONObject jsonfilm = jsonCatalogue.getJSONObject(i);
				this.db.Insert_Film(groupeName,jsonfilm.getInt("id"));
			}
			
		}
	}

	public void calculNewCatalogue(String groupeName,String admin, int idfilm[])throws IOException, InterruptedException
	{
		// on vérifie si celui qui veut créer un catalogue est bien l'admin
		String query = "SELECT admin FROM groupe WHERE groupeName='"+groupeName+"';";
		String attributs[] = {"admin"};
		String types[] = {"string"};
		int nombreAttributs = 1;
		ArrayList<Map<String,String>> administrateur = this.db.getBD(query, attributs, types, nombreAttributs);

		if(admin.equals(administrateur.get(0).get("admin")))
		{
			// on efface les anciens films du groupe
			this.db.DELETE_Film(groupeName);

			// on construit le json de requete
			JSONObject requete = new JSONObject();
			Integer i = 0;
			for(i = 0; i< idfilm.length;i++)
			{
				requete.put(i.toString(),idfilm[i]);
			}
			
			String newCatalogue = this.post("http://tindfilm/selector", requete.toString());
			this.setCatalogue(groupeName, admin, newCatalogue);
		}
	}
	
	//retourne les id des filmes du groupe
	public int[] getCatalogue(String groupeName,String userName)
	{
		// on vérifie si l'utilisateur se trouve dans le groupe
		ArrayList<String> listeUser = this.getGroupUsers(groupeName).get(groupeName);
		if(listeUser.contains(userName))
		{
			String query = "SELECT filmID FROM groupeScores WHERE groupeName='"+groupeName+"';";

			String attributs[] = {"filmID"};
			String types[]={"int"};
			int size = 1;

			ArrayList<Map<String,String>> reponse = this.db.getBD(query, attributs, types, size);
			int retour[] = new int[reponse.size()];

			for(int i = 0; i < reponse.size(); i++)
			{
				retour[i] = Integer.parseInt(reponse.get(i).get("filmID"));
			}
			return retour;
		}
		else
		{
			return null;
		}
	}

	// incrémente sans problème de conccurence le score des films. si increment = true, on incremente
	// sinon on décremente.
	public void incrementScore(String groupeName, int idFilm,boolean increment ) 
	{
		String query = "SELECT score FROM groupeScores WHERE groupeName='"+groupeName+"' AND filmID="+idFilm+" FOR UPDATE;";
		query += "UPDATE groupeScores SET score = score ";
		if(increment)
		{
			query += " + 1;";
		}
		else
		{
			query += " - 1";
		}

		this.db.setBD(query);
	}

	// retourne les films avec le plus de succès. top = les n premierss films
	public int[] getBestFilmId(String groupeName, int top)
	{
		String query = "SELECT filmID score FROM groupeScores WHERE groupeName='"+groupeName+"' ORDER BY score FOR SHARE;";
		String attributs[] = {"filmID"};
		String types[] = {"int"};
		int size = 1;
		// on récupère une liste d'id de film ordonnée par le score
		ArrayList<Map<String,String>> listeFilmIDOrdonnee = this.db.getBD(query, attributs, types, size);
		int nombreId = top;
		if(nombreId > listeFilmIDOrdonnee.size())
		{
			nombreId = listeFilmIDOrdonnee.size();
		}

		int retour[] = new int[nombreId];

		for(int i = 0; i<nombreId;i++)
		{
			retour[i] = Integer.parseInt(listeFilmIDOrdonnee.get(i).get("filmID")); 
		}
		
		return retour;
	}

	public Map<Integer,Integer> getScores(String groupeName)
	{
		Map<Integer,Integer> retour = new HashMap<Integer,Integer>();
		String query = "SELECT filmID score FROM groupeScores WHERE groupeName='"+groupeName+"' ORDER BY score FOR SHARE;";
		String attributs[] = {"filmID", "score"};
		String types[] = {"int","int"};
		int size = 2;
		ArrayList<Map<String,String>> listeScores = this.db.getBD(query, attributs, types, size);

		for(Map<String,String> ligne : listeScores)
		{
			retour.put(Integer.parseInt(ligne.get("filmID")),Integer.parseInt(ligne.get("score")));
		}
		return retour;
	}

	public void deleteCatalogue(String groupeName,String admin)
	{
		// on vérifie si celui qui veut effacer le catalogue est bien l'admin
		String query = "SELECT admin FROM groupe WHERE groupeName='"+groupeName+"';";
		String attributs[] = {"admin"};
		String types[] = {"string"};
		int nombreAttributs = 1;
		ArrayList<Map<String,String>> administrateur = this.db.getBD(query, attributs, types, nombreAttributs);

		if(admin.equals(administrateur.get(0).get("admin")))
		{
			// on efface les anciens films du groupe
			this.db.DELETE_Film(groupeName);
		}
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
