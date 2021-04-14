package api.rest;

import java.util.*;

import api.model.*;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
    public ArrayList<User> getall() { 
	return this.userService.getall();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/id={id}")
    public User getUser(@PathParam("id") int id) { 
	return this.userService.getUser(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/name={name}")
    public User getUser(@PathParam("name") String name) { 
	return this.userService.getUser(name);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/group={group}")
    public ArrayList<User> getGroupUser(@PathParam("group") String group) { 
	return this.userService.getGroupUser(group);
    }

   @POST //, PUT
   @Produces(MediaType.APPLICATION_JSON)
   public ArrayList<User> /*void*/ addUser(User user){
	this.userService.addUser(user);
	return this.userService.getall();
     }

   @DELETE
   @Produces(MediaType.APPLICATION_JSON)
   public ArrayList<User> deleteUser(User user){
	this.userService.deleteUser(user);
	return this.userService.getall();
     }
    

}//end class
