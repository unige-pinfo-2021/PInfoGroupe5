package api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TMDB_Request {
	
    public final boolean include_adult;
    public final float vote_average_gte;
    public final float vote_average_lte;
    public final int page;
    public final java.util.Date primary_release_date_gte;
    public final java.util.Date primary_release_date_lte;
    public final List<Integer> with_genres;
    
    
    
    public TMDB_Request(@JsonProperty("include_adult") boolean include_adult,
    					@JsonProperty("vote_average.gte") float vote_average_gte,
    					@JsonProperty("vote_average.lte") float vote_average_lte,
    					@JsonProperty("page") int page,
    					@JsonProperty("primary_release_date.gte") java.util.Date primary_release_date_gte,
    					@JsonProperty("primary_release_date.lte") java.util.Date primary_release_date_lte,
    					@JsonProperty("with_genres") List<Integer> with_genres) {
        this.include_adult = include_adult;
        this.vote_average_gte = vote_average_gte;
        this.vote_average_lte = vote_average_lte;
        this.page = page;
        this.primary_release_date_gte = primary_release_date_gte;
        this.primary_release_date_lte = primary_release_date_lte;
        this.with_genres = with_genres;
    }
    
}//finClass
