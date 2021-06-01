package api.rest;

import api.model.*;

import java.util.*;

//import api.model.*;

import java.io.IOException;

import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;



@Path("/user")
public class RestServiceUser {

	private UserService userService;

	public RestServiceUser() throws Exception{
		this.userService = new UserService();
	 }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from users !";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/connect")
    public String getConn() throws Exception{ 
		return new DataBaseUser().connect();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{username}")
    public User getUser(@PathParam("username") String username){ 
		return this.userService.getUserDB(username);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/exist/{username}")
    public boolean existUser(@PathParam("username") String username){ 
		return this.userService.existUser(username);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public ArrayList<Map<String,String>> getAll(){ 
		return this.userService.getAllDB();
    }


   /* LAST UPDTS */
   @POST //, PUT
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/new")
   public boolean addUser(Map<String,String> inputJSON)
   {
      return this.userService.setUserDB(inputJSON.get("userName"),inputJSON.get("email"));
    }


   @DELETE
   @Produces(MediaType.APPLICATION_JSON)
   @Path("/{username}")
   public boolean deleteInvit(@PathParam("username") String username){
	return this.userService.removeUserDB(username);
     }
}//end class
