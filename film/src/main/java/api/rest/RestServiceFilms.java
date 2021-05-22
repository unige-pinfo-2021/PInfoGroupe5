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

    private TMDB_Caller filmService;
    
    public RestServiceFilms(){
	this.filmService =  new TMDB_Caller("3aacfef6a62a872d2a4717b9b6cd5283");
    }
    
    @GET
    @Path("/head")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRandomMoviesHead() throws IOException, InterruptedException { 
    Response.ResponseBuilder rb = Response.ok(this.getRandomMovies());
    Response response = rb.header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Credentials", "true")
            .header("Access-Control-Allow-Headers",
                    "origin, content-type, accept, authorization")
            .header("Access-Control-Allow-Methods", 
                    "GET, POST, PUT, DELETE, OPTIONS, HEAD")
            .build();
    return response;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Movie> getRandomMovies() throws IOException, InterruptedException { 
	return this.filmService.getRandomMovies_asList(40);
    }

    
    
    @GET
    @Path("/restcall/vgt={vote_average.gte}/vlt={vote_average.lte}/page={page}/prgt={primary_release_date.gte}/prlt={primary_release_date.lte}/with_people={with_people}/with_genres={with_genres}/with_keywords={with_keywords}/head")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getrestcallHead(@PathParam("vote_average.gte") double vote_average_gte,@PathParam("vote_average.lte") double vote_average_lte,
    		@PathParam("page") int page,@PathParam("primary_release_date.gte") String primary_release_date_gte,@PathParam("primary_release_date.lte") String primary_release_date_lte,@PathParam("with_people") String with_people,
    		@PathParam("with_genres") String with_genres,@PathParam("with_keywords") String with_keywords) throws IOException, InterruptedException { 
    Response.ResponseBuilder rb = Response.ok(this.getrestcall(vote_average_gte,vote_average_lte,page,primary_release_date_gte,primary_release_date_lte,with_people,with_genres,with_keywords));
    Response response = rb.header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Credentials", "true")
            .header("Access-Control-Allow-Headers",
                    "origin, content-type, accept, authorization")
            .header("Access-Control-Allow-Methods", 
                    "GET, POST, PUT, DELETE, OPTIONS, HEAD")
            .build();
    return response;
    }
    
    
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/restcall/vgt={vote_average.gte}/vlt={vote_average.lte}/page={page}/prgt={primary_release_date.gte}/prlt={primary_release_date.lte}/with_people={with_people}/with_genres={with_genres}/with_keywords={with_keywords}")
    public List<Movie> getrestcall(@PathParam("vote_average.gte") double vote_average_gte,@PathParam("vote_average.lte") double vote_average_lte,
    		@PathParam("page") int page,@PathParam("primary_release_date.gte") String primary_release_date_gte,@PathParam("primary_release_date.lte") String primary_release_date_lte,@PathParam("with_people") String with_people,
    		@PathParam("with_genres") String with_genres,@PathParam("with_keywords") String with_keywords) throws IOException, InterruptedException { 
    	String requestJson = "{\n"
				+ "	\"include_adult\": false,\n"
				+ "	\"vote_average.gte\": "+vote_average_gte+",\n"
				+ "	\"vote_average.lte\": "+vote_average_lte+",\n"
				+ "	\"page\": "+page+",\n"
				+ "	\"primary_release_date.gte\": "+primary_release_date_gte+",\n"
				+ "	\"primary_release_date.lte\": "+primary_release_date_lte+",\n"
				+ "	\"with_people\": "+with_people+",\n"
				+ "	\"with_genres\": "+with_genres+",\n"
				+ "	\"with_keywords\": "+with_keywords+"\n"
				+ "}";
    	return filmService.executeRequest_asList(requestJson);
    }


    

}//end class
