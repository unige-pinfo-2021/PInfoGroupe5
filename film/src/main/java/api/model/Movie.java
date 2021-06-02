package api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
	
    public final String title;
    public final String release_date;
    public String poster_path;
    public final int popularity;
    public final String overview; 
    public final int[] genre_ids;
    public final int id;
    
    public Movie(@JsonProperty("title") String title, @JsonProperty("release_date") String release_date, @JsonProperty("poster_path") String poster_path, @JsonProperty("popularity") int popularity,@JsonProperty("overview") String overview,@JsonProperty("genre_ids") int[]
    		genre_ids,@JsonProperty("id") int id) {
        this.title = title;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.popularity = popularity;
        this.overview = overview;
        this.genre_ids = genre_ids;
        this.id = id;
    }
    
    
    public static String printList(List<Movie> movieList) {
		String descrpt = "";
		for (int i=0; i<movieList.size(); i++) {
			descrpt += movieList.get(i).title + " --> "+ movieList.get(i).release_date+"\n";
		}
		return descrpt;
    }
    
}//finClass
