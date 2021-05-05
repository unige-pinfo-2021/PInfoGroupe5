package api.rest;

import java.util.*;

import api.model.*;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;

@Path("/user")
public class RestServiceUser {

    private UserService userService;

    public RestServiceUser(){
	this.userService = new UserService();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String Default() { 
	return "You reached user3";
    }
/*
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/id={id}")
    public User getUser(@PathParam("id") int id) { 
	return this.userService.getUser(id);
    }
*/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/name={name}")
    public User getUser(@PathParam("name") String name) { 
	return this.userService.getUserDB(name);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Map<String,User> getAll() { 
	return this.userService.getAll();
    }

  /*  @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/group={group}")
    public ArrayList<User> getGroupUser(@PathParam("group") String group) { 
	return this.userService.getGroupUser(group);
    }
*/
   @POST //, PUT
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   @Path("/put")
   public Map<String,User> addUser(String user,String email){
	this.userService.addUserDB(new User(user, email));
	return userService.getAll();
     }

   @DELETE
   @Produces(MediaType.APPLICATION_JSON)
   public int deleteUser(String user){
	this.userService.removeUserDB(user);
	return 0;
     }
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   @Path("/name={name}/exist")
   public boolean existUser(@PathParam("name") String name) { 
	return this.userService.existUser(name);
   }
    

}//end class
