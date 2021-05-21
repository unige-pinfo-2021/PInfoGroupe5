package api.rest;//.films.rest;

import java.util.*;

import api.model.*;

import java.io.IOException;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;

@Path("/film")
public class RestServiceFilms {

    private TMDB_Caller tmdb_caller;

    public RestServiceFilms(){
	this.tmdb_caller = new TMDB_Caller("3aacfef6a62a872d2a4717b9b6cd5283");
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getdefault() { 
	return "Hello from Film service";
    }
    

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/TMDB/title={title}")
    public List<Movie> getTMDBtitle(@PathParam("title") String title) throws IOException, InterruptedException { 
	return this.tmdb_caller.getMoviesByTitle(title);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/TMDB/kind={kind}")
    public List<Movie> getTMDBkind(@PathParam("kind") int kind) throws IOException, InterruptedException { 
	return this.tmdb_caller.getMoviesByGenre(kind);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/TMDB/year={year}")
    public List<Movie> getTMDByear(@PathParam("year") String year) throws IOException, InterruptedException { 
	return this.tmdb_caller.getMoviesByYear(year);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/TMDB/random={random}")
    public String getTMDBrandom(@PathParam("random") int random) throws IOException, InterruptedException { 
	return this.tmdb_caller.getRandomMovies_asJsonString(random);
    }
    

}//end class
