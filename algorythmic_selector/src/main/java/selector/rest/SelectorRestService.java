package selector.rest;


import java.util.*;

import selector.model.*;

import java.io.IOException;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;

@Path("/selector")
public class SelectorRestService
{
   private selector selecteur;

   public SelectorRestService()throws IOException, InterruptedException
   {
       this.selecteur = new selector();
   }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String Default()
    {
        return "You reached selector";
    }

    @POST 
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object getCatalogue(Map<String,Integer> inputJSON )throws IOException, InterruptedException 
    {
    
    Map<Integer,Integer> retour = new HashMap<Integer,Integer>();
    // on récupère les films et on les met dans un tablau int
    Integer[] film = new Integer[5];
    Integer i = 0;
    for(i = 0; i< 5; i++)
    {
        film[i] = inputJSON.get(Integer.toString(i));
    }
    // on lance la fonction de recommendation
    Integer id[] = this.selecteur.recommendations(film);
    
    // on prépare le json de retour
    for(i = 0; i< id.length; i++)
    {
    	retour.put(i, id[i]);
    }
	return retour;
    }
    
}//end class
