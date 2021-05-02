package api.model;

import java.util.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;


public class Group {

    private String groupName;
    // critere = nomCritere : probabilité [0,1]
    private Map<String,Float> criteres = new HashMap<String,Float>() ; 
    private Set<String> users = new HashSet<String>();
    //private ArrayList<Films> Historic;

   //private ArrayList<String> preferences;

    public Group() {
	this.groupName = "Unknown";
    }

    public Group(String groupName) {
        this.groupName = groupName;
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


   // permet de calculer le critere moyen. Pour l'instant, 
   // envoie requete http pour récupérer les données users.
   public void calculCritere()throws IOException, InterruptedException
   {
        for(String user : this.users)
        {
           String info = this.getUser(user);
           System.out.println(user + " " + info);
        }    
   }
	/* renvoie le nom des utilisateurs */
	 public Set<String> getUsersName(){
		return this.users;
    }
    
    /* renvoie les infos utilisateurs*/
    private String getUser(String userName)throws IOException, InterruptedException
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
    	HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(adresse))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}