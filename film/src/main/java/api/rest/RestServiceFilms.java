package api.rest;//.films.rest;

import java.util.*;

import api.model.*;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;

@Path("/films")
public class RestServiceFilms {

    private FilmService filmService;

    public RestServiceFilms(){
	this.filmService = new FilmService();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Film> getall() { 
	return this.filmService.getall();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/id={id}")
    public Film getFilm(@PathParam("id") int id) { 
	return this.filmService.getFilm(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/title={title}")
    public Film getFilm(@PathParam("title") String title) { 
	return this.filmService.getFilm(title);
    }


    /*@GET
    //@Produces(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/restcall/{type}&{title}")
    public String getrestcall(@PathParam("type") int type,@PathParam("title") String title) throws IOException, InterruptedException { 
	Rest_Caller rc = new Rest_Caller();
	String response = rc.restCallFilm(type, title);
	return title+" "+type;
    }*/


   /*@POST , PUT
   @Produces(MediaType.APPLICATION_JSON)
   public ArrayList<Film> addFilm(Film film){
	this.filmService.addFilm(film);
     }*/

   /*@DELETE
   @Produces(MediaType.APPLICATION_JSON)
   public ArrayList<Film>removeFilm(Film film){
	this.filmService.removeFilm(film);
	return this.filmService.getall();
     }*/

    

}//end class
