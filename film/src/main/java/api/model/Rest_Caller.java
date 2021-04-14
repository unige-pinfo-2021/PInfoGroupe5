//package backendFilmGetter;
package api.model;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;

import org.json.*;

public class Rest_Caller {
	
	public String call(String apikey, String title_to_search, TitleType title_type) throws IOException, InterruptedException {
		String type = TitleTypeName(title_type);
		
		//String uri = "https://imdb-api.com/en/API/SearchMovie/k_dp3eur0c/pianist";
		String uri = "http://www.omdbapi.com/?apikey=" + apikey + "&t=" + title_to_search;
		if (title_type != TitleType.ALL)
			uri += "&type=" + type;
		
		//client request and server response 
		HttpClient client = HttpClient.newHttpClient();
	    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
	    HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
	    return response.body();
	}
	
	
	enum TitleType {
		ALL,    //0 
		MOVIE,  //1
		SERIES, //2
		EPISODE,//3
	}

	public static String TitleTypeName(TitleType t){
		String[] names = {"All Types (Movie/Series/Episode)", "Movie", "Series", "Episode"};
		//convert to integer 
		return names[t.ordinal()];
	}
	
	public String request(String JSONQuery) throws IOException, InterruptedException  {
		String apikey = "85aad23d";
		JSONObject jo = new JSONObject(JSONQuery);
		String title_to_search = jo.getString("title");
		int title_type = jo.getInt("Type");
		String result = "error";
		try {
			result = call(apikey, title_to_search, TitleType.values()[title_type]);
		} catch (Exception e) {
			System.out.println("Something went wrong.");
		}
		return result;
	}


	public  String restCallFilm(int title_type, String title_to_search) throws IOException, InterruptedException{
		// Request Parameters
		String apikey = "85aad23d";

		Scanner sc = new Scanner(System.in);
		
		String result = this.call(apikey, title_to_search, TitleType.values()[title_type]);

	       sc.close();

		return result;
	}
	

}


