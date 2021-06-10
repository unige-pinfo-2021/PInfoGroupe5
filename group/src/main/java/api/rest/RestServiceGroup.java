package api.rest;

import java.util.*;

import api.model.*;
import jdk.nashorn.internal.objects.annotations.Getter;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;

import java.io.IOException;
import org.json.*;


@Path("/group")
public class RestServiceGroup {

    // deux petites classe privée pour construire le message de retour
    class retourMsg{
        public String groupName;
        public String admin;
        public String invitation;
        public ArrayList<String> users;
    }

    class RetourMsg {
		public boolean reussit = true;
		public String msg = "";
	}

    class A
    {
        public boolean reussit;
        public String message = "";
    }

    class B {
        public int[] catalogue;
        public String message = "";
    }
    // reto

    private GroupService groupService;

    public RestServiceGroup()throws Exception, IOException, InterruptedException
    {
	this.groupService = new GroupService();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String Default() { 
	return "You reached group";
    }

    // créer un nouveau groupe
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Map<String,String> createGroupe(Map<String,String> inputJSON)
    {
       return this.groupService.CreateGroup(inputJSON.get("groupName"), inputJSON.get("admin"), inputJSON.get("invitation"));   
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{groupName}")
    public Object getGroup(@PathParam("groupName") String groupName)throws IOException, InterruptedException
    { 
        retourMsg retour = new retourMsg();
        
        Map<String,String> info = this.groupService.getGroup(groupName);
        retour.groupName = info.get("groupName");
        retour.admin = info.get("admin");
        retour.invitation = info.get("invitation");
        retour.users = this.getGroupUsers(groupName);

	    return retour;
    }

	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{invitation}")
    public String getGroupInvite(@PathParam("invitation") String invitation)throws IOException, InterruptedException
    { 
   	return this.groupService.getGroupInvite(invitation);
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{groupName}/{admin}")
    public Object deleteGroup(@PathParam("groupName") String groupName, @PathParam("admin") String admin)throws IOException, InterruptedException
     {
       
         return this.groupService.removeGroup(groupName, admin);
     }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{groupName}/users")
    public ArrayList<String> getGroupUsers(@PathParam("groupName") String groupName)throws IOException, InterruptedException
    { 
	    return this.groupService.getGroupUsers(groupName);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all/users")
    public Map<String,ArrayList<String>> getAllGroupUsers()throws IOException, InterruptedException
    { 
	    return this.groupService.getAllGroupUsers();
    }

    
   @POST //, PUT
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/users")
   public Object addUser(Map<String,String> inputJSON)
   {
      return this.groupService.addUser(inputJSON.get("userName"), inputJSON.get("invitation"));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userName}/groups")
    public ArrayList<String> getGroups(@PathParam("userName") String userName)
    {
        return this.groupService.getUserGroups(userName);
    }
    
    

    @DELETE
   @Produces(MediaType.APPLICATION_JSON)
   @Path("/{groupName}/{userName}/{admin}")
   public Object deleteUser(@PathParam("groupName") String groupName, @PathParam("userName") String userName, @PathParam("admin") String admin)throws IOException, InterruptedException
    {
       
        return this.groupService.removeUser(groupName, userName, admin);
        
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{groupName}/newCatalogue")
    public Response setNewCatalogue(Map<String,String> inputJSON,@PathParam("groupName") String groupeName)throws IOException, InterruptedException
    { 
       
       
        // catalogue au hasard
        if(inputJSON.get("type").equals( "random"))
        {
        	return Response
        			.status(Response.Status.OK)
        			.header("Access-Control-Allow-Origin", "*")
        			.header("Access-Control-Allow-Credentials", "true")
        			.header("Access-Control-Allow-Headers",
        					"origin, content-type, accept, authorization")
        			.header("Access-Control-Allow-Methods", 
        					"GET, POST, PUT, DELETE, OPTIONS, HEAD")
        			.entity(this.groupService.setRandomCatalogue(groupeName, inputJSON.get("admin")))
        			.build();
        }
        // catalogue calculé selon un ensemble d'id de film
        else
        {
        	return Response
        			.status(Response.Status.OK)
        			.header("Access-Control-Allow-Origin", "*")
        			.header("Access-Control-Allow-Credentials", "true")
        			.header("Access-Control-Allow-Headers",
        					"origin, content-type, accept, authorization")
        			.header("Access-Control-Allow-Methods", 
        					"GET, POST, PUT, DELETE, OPTIONS, HEAD")
        			.entity(this.groupService.calculNewCatalogue(groupeName, inputJSON.get("admin")))
        			.build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{groupName}/{userName}/Catalogue")
    public Object getCatalogue(@PathParam("groupName") String groupeName,@PathParam("userName") String userName)throws IOException, InterruptedException
    { 
        B retour = new B();
        // retourne catalogue
        retour.catalogue = this.groupService.getCatalogue(groupeName, userName);
        return retour;
    }

    @DELETE
    @Path("/{groupName}/{admin}/Catalogue")
    public Object deleteScores(@PathParam("groupName") String groupeName, @PathParam("admin") String admin)
    {     
        return this.groupService.deleteCatalogue(groupeName, admin);  
    }



    // changer et get le score des films
    @POST
    @Produces(MediaType.APPLICATION_JSON) 
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{groupName}/scores")
    public Object setScores(Map<String,String> inputJSON, @PathParam("groupName") String groupName)
    {
        return this.groupService.incrementScore(groupName,inputJSON.get("userName"), Integer.parseInt(inputJSON.get("idFilm")),Boolean.parseBoolean( inputJSON.get("increment")));
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON) 
    @Path("/{groupName}/scores")
    public ArrayList<Map<String,Integer>> getScores(@PathParam("groupName") String groupName)
    {
        return this.groupService.getScores(groupName);
    }

    // obtenir les votes du groupes
    @GET
    @Produces(MediaType.APPLICATION_JSON) 
    @Path("/{groupName}/votes")
    public Map<String, Map<Integer,Integer>> getVotes(@PathParam("groupName") String groupName)
    {
        return this.groupService.getVotes(groupName);
    }


}//end class
