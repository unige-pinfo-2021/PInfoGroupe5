package api.rest;

import api.model.*;

import java.util.*;

//import api.model.*;

import java.io.IOException;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;



@Path("/user")
public class RestServiceUser {

	private UserService userService;

	public RestServiceUser(){
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
    public String getConn(){ 
		return new DataBaseUser("src/main/resources/database.properties").try_connect();
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

   /*@POST //, PUT
   @Produces(MediaType.APPLICATION_JSON)
   //@Consumes(MediaType.APPLICATION_JSON)
   @Path("/new")
   public void addUser(User user){
	this.userService.setUserDB(user);
	//return user;
     }*/

   /*@DELETE
   @Produces(MediaType.APPLICATION_JSON)
   @Path("/delete")
   public void deleteUser(){
	this.userService.removeUserDB(new User("Murderer"));
	//return this.userService.getall();
     }*/
}//end class
