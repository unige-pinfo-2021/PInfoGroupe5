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

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


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
    @Path("/name={groupName}")
    public Group getGroup(@PathParam("groupName") String groupName) { 
	return groupService.getGroup(groupName);
    }


  /*  @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/group={group}")
    public ArrayList<User> getGroupUser(@PathParam("group") String group) { 
	return this.userService.getGroupUser(group);
    }
*/
   @POST //, PUT
   @Produces(MediaType.APPLICATION_JSON)
   public Group addUser(String groupName,String username){
	this.groupService.addUser(groupName, username);
	return this.groupService.getGroup(groupName);
     }

   @DELETE
   @Produces(MediaType.APPLICATION_JSON)
   public Group deleteUser(String groupName,String userName){
	this.groupService.removeUser(groupName, userName);
	return this.getGroup(groupName);
     }
    

}//end class
