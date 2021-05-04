package api.model;

import java.util.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import org.json.*;
import java.net.*;
import java.io.*;


public class Group {

    private String groupName;
    // critere = nomCritere : probabilité [0,1]
    private Map<String,Double> criteres = new HashMap<String,Double>() ; 
    private Set<String> users = new HashSet<String>();
    //private ArrayList<Films> Historic;

   //private ArrayList<String> preferences;

    public Group() {
	this.groupName = "Unknown";
    }

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public Group(String groupName,Set<String> users)throws IOException, InterruptedException
    {
        this(groupName);
        this.users = users;
        this.calculCritere();
    }
    
   public String getGroupName(){
	return this.groupName;
   }

    public void setGroupName(String groupName){
	this.groupName = groupName;
   }

    public void addUser(String userName){
	this.users.add(userName);
   }

   public Map<String,Double> getMoyenne()
   {
       return this.criteres;
   }


   // permet de calculer le critere moyen. Pour l'instant, 
   // envoie requete http pour récupérer les données users.
   public void calculCritere()throws IOException, InterruptedException
   {
       System.out.println("calcul moyenne critere");
       // on définit les différent thèmes de film
       String[] themes = {"humour","horreur","drame","action","aventure"};
       // on initialise le tableau qui contiendra la moyenne
        Map<String,Double> moyenne = new HashMap<String,Double>();
        for(String theme : themes)
        {
            moyenne.put(theme, 0.0);
        }
        // on récupère les critères de chaque utilisateur et faisons la moyenne
        for(String user : this.users)
        {
           String info = this.getUser(user);
           JSONObject jsonObject = new JSONObject(info);
           JSONObject criteres = jsonObject.getJSONObject("critere");
           System.out.println(criteres.toString());
           for(String theme : themes)
           {
               moyenne.put(theme,moyenne.get(theme)+criteres.getDouble(theme));
           }
        }
        System.out.println("Vers la Division");
        // division final
        for(String theme : themes)
        {
            System.out.println("Division");
            moyenne.put(theme, moyenne.get(theme)/this.users.size());
            System.out.println(theme + " " + moyenne.get(theme));
        }
        System.out.println("fin calcul critere");
        this.criteres = moyenne;
   }
	/* renvoie le nom des utilisateurs */
	 public Set<String> getUsersName(){
		return this.users;
    }
    
    /* renvoie les infos utilisateurs*/
    public String getUser(String userName)throws IOException, InterruptedException
    {
        String url = "http://tindfilm/user/name=" + userName;
        String JSONuser = get(url);
        return JSONuser;
    } 
 
     public boolean removeUser(String userName){
        return this.users.remove(userName);
    }
    
     public boolean isInGroup(String userName){
    	return this.users.contains(userName);
    }

    /* renvoie les critères */

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
		} else {
			return "GET request not worked";
		}
       
    }
}
