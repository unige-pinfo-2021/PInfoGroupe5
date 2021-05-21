package api.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class TMDBResult {
    
	public final String page;
    public final ArrayNode results;
    
    //constractor-------------------------------------------------------------------------------------------
    public TMDBResult(@JsonProperty("page") String page,@JsonProperty("results") ArrayNode results){
        this.page = page;
        this.results = results;
    }
    
}//finClass