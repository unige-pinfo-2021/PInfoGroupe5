package api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)

public class Genre {
    
	public final int id;
    public final String name;
    
    //constracteur----------------------------------------------------------------
    public Genre(@JsonProperty("id")int id, @JsonProperty("name")String name) {
        this.id = id;
        this.name = name;
    }
    
}//finclass
