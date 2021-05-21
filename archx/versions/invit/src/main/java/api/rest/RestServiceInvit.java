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



@Path("/invit")
public class RestServiceInvit {

	private InvitService invitService;

	public RestServiceInvit(){
		this.invitService = new InvitService();
	 }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from invits !";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/connect")
    public String getConn(){ 
		return new DataBaseInvit("src/main/resources/database.properties").try_connect();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{ID}/{film}")
    public Invit getInvit(@PathParam("ID") String ID, @PathParam("film") String film){ 
		return this.invitService.getInvitDB(ID,film);
    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{ID}")
    public ArrayList<Invit> getallInvit(@PathParam("ID") String ID){ 
		return this.invitService.getallInvitDB(ID);
    }


   @POST //, PUT
   @Produces(MediaType.APPLICATION_JSON)
   //@Consumes(MediaType.APPLICATION_JSON)
   @Path("/update/{ID}/{film}/{score_updt}")
   public void updateInvit(@PathParam("ID") String ID, @PathParam("film") String film, @PathParam("score_updt") int score_updt){
	this.invitService.updateInvitDB(ID, film, score_updt);
     }


   @POST //, PUT
   //@Consumes(MediaType.APPLICATION_JSON)
   //@Consumes("application/x-www-form-urlencoded")
   @Path("/new/{ID}/ref/{film}/total/{total}") ///service/new?ID=value1&film=value2&total=value3  @QueryParam
   public void addInvit(@PathParam("ID") String ID, @PathParam("film") String film, @PathParam("total") int total){	
	this.invitService.setInvitDB(new Invit(ID, film, total)); 
	//return  ID+" "+film+" "+total+"\n";
    }

   @DELETE
   @Produces(MediaType.APPLICATION_JSON)
   @Path("/delete/{ID}")
   public void deleteInvit(@PathParam("ID") String ID){
	this.invitService.removeInvitDB(ID);
     }
}//end class
