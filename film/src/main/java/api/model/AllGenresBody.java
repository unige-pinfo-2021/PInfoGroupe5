package api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class AllGenresBody {
    
	public final ArrayNode genres;
    
    //constracteur--------------------------------------------------
    public AllGenresBody(@JsonProperty("genres") ArrayNode genres) {
        this.genres = genres;
    }
    
    
}//fin class