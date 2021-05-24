package api.rest;

import api.model.*;

import java.util.*;

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
import javax.ws.rs.QueryParam;



@Path("/group")
public class RestServiceGroup {

	private GroupService groupService;

	public RestServiceGroup(){
		this.groupService = new GroupService();
	 }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from groups !";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/connect")
    public String getConn(){ 
		return new DataBaseInvit("src/main/resources/database.properties").try_connect();
    }


    @POST
    //@Produces(MediaType.TEXT_PLAIN)
    @Path("/new/{groupname}/{username}")
	public void create_group(@PathParam("groupname") String groupname, @PathParam("username") String username){
		groupService.create_group(groupname, username);	
	}//end*/
	

    @POST
    //@Produces(MediaType.TEXT_PLAIN)
    @Path("/{groupname}/new/{username}")
	public void add_user(@PathParam("groupname") String groupname, @PathParam("username") String username){
		groupService.add_user(groupname, username);
	}//end 

	@DELETE
   @Produces(MediaType.APPLICATION_JSON)
   @Path("/{groupname}/delete/{username}")
	public void delete_user(@PathParam("groupname") String groupname, @PathParam("username") String username){
		groupService.delete_user(groupname, username);
	}//end

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{groupname}")
	public ArrayList<String> get_user_list(@PathParam("groupname") String groupname){
		return  groupService.get_user_list(groupname);
	} //end 


	//public String get_selected_film(String groupname){}//end

	//public void create_films(String groupname, ArrayList<String> films){}//end

	//public void update_films(String groupname, ArrayList<Integer> scores){}

	//public void delete_films(String groupname){}//end  

	//public ArrayList<String> get_films(String groupname){}//end

	//public ArrayList<Integer> get_scores(String groupname){}//end	

	//public ArrayList<Integer> get_total_scores(String groupname){}//end

}//end class
