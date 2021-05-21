package api.rest;

import java.util.*;

import api.model.*;
import jdk.nashorn.internal.objects.annotations.Getter;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;

import java.io.IOException;
//import java.lang.invoke.PolymorphicSignature;



@Path("/group")
public class RestServiceGroup {

    private GroupService groupService;

    public RestServiceGroup()throws IOException, InterruptedException
    {
	this.groupService = new GroupService();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String Default() { 
	return "You reached group";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public void createGroupe(Map<String,String> inputJSON)
    {
        this.groupService.CreateGroupe(inputJSON.get("groupeName"), inputJSON.get("admin"), inputJSON.get("invitation"));
    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{groupName}/users")
    public Map<String,ArrayList<String>> getGroupUsers(@PathParam("groupName") String groupName)throws IOException, InterruptedException
    { 
	    return this.groupService.getGroupUsers(groupName);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all/users")
    public Map<String,ArrayList<String>> getAllGroupUsers(@PathParam("groupName") String groupName)throws IOException, InterruptedException
    { 
	    return this.groupService.getALLGroupUsers();
    }

    
   @POST //, PUT
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/{groupName}/users")
   public void addUser( Map<String,String> inputJSON, @PathParam("groupName") String groupName)
   {
	this.groupService.addUser(groupName, inputJSON.get("userName"),inputJSON.get("invitation"));
    }
    

   @DELETE
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/{groupName}")
   public void deleteGroup(Map<String,String> inputJSON,@PathParam("groupName") String groupName)throws IOException, InterruptedException
    {
        this.groupService.removeGroup(groupName, inputJSON.get("admin"));
    }

    @DELETE
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/{groupName}/{userName}")
   public void deleteUser(Map<String,String> inputJSON,@PathParam("groupName") String groupName, @PathParam("userName") String userName)throws IOException, InterruptedException
    {
        this.groupService.removeUser(groupName, userName, inputJSON.get("admin"));
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{groupName}/newCatalogue")
    public int[] setNewCatalogue(Map<String,String> inputJSON,@PathParam("groupName") String groupeName)throws IOException, InterruptedException
    { 
        // catalogue au hasard
        if(inputJSON.get("type").equals( "random"))
        {
            this.groupService.setRandomCatalogue(groupeName, inputJSON.get("admin"));
            return this.groupService.getCatalogue(groupeName, inputJSON.get("userName"));
        }
        // catalogue calculé selon un ensemble d'id de film
        else
        {
            int idFilm [] = this.groupService.getBestFilmId(groupeName,5);// récupère le top 5 des films pour groupeName
            this.groupService.calculNewCatalogue(groupeName, inputJSON.get("admin"), idFilm);// envoie l'id des films, recoit le catalogue et l'inscrit dans notre BD.
            return this.groupService.getCatalogue(groupeName, inputJSON.get("userName"));// renvoie les id des nouveaux films.
        }
	
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{groupName}/{userName}/Catalogue")
    public int[] getCatalogue(@PathParam("groupName") String groupeName,@PathParam("userName") String userName)throws IOException, InterruptedException
    { 
        // retourne catalogue
        return this.groupService.getCatalogue(groupeName, userName);
	
    }

    // changer le score des films
    @POST 
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{groupeName}/scores")
    public void setScores(Map<String,String> inputJSON, @PathParam("groupeName") String groupeName)
    {
        this.groupService.incrementScore(groupeName, Integer.parseInt(inputJSON.get("idFilm")), Boolean.parseBoolean(inputJSON.get("increment")));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON) 
    @Path("/{groupeName}/scores")
    public Map<Integer,Integer> getScores(@PathParam("groupeName") String groupeName)
    {
        return this.groupService.getScores(groupeName);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{groupeName}/Catalogue")
    public void deleteScores(Map<String,String> inputJSON, @PathParam("groupeName") String groupeName)
    {
        this.groupService.deleteCatalogue(groupeName, inputJSON.get("admin"));
    }

}//end class
