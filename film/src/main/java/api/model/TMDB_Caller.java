package api.model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TMDB_Caller {

	//variables
	private String apiKey;
	private int pageSize = 20;
	private Random rand = new Random();
	
	//connstracteur---------------------------------------------------------------------------------------------------
	public TMDB_Caller(String apiKey) {
		super();
		this.apiKey = apiKey;
	}
	
	///////////     Genres      /////////////

	//listAllGenres_uri-------------------------------------------------------------------------------------------------
	public String listAllGenres_uri() {
		//https://api.themoviedb.org/3/genre/movie/list?api_key=3aacfef6a62a872d2a4717b9b6cd5283&language=en-US
		return "https://api.themoviedb.org/3/genre/movie/list?api_key="+apiKey+"&language=en-US";
	}
	
	
	/////////////     Discover      /////////////
	
	//searchByGenre_uri-------------------------------------------------------------------------------------------------
	public String searchByGenre_uri(int genre_id) {
		return "https://api.themoviedb.org/3/discover/movie?api_key="+apiKey+"&language=en-US&sort_by=popularity.desc&page=1&include_adult=false&include_video=false&with_genres=" + genre_id;
	}
	
	//searchByYear_uri--------------------------------------------------------------------------------------------------
	public String searchByYear_uri(String year) {
		//https://api.themoviedb.org/3/discover/movie?api_key=3aacfef6a62a872d2a4717b9b6cd5283&language=en-US&sort_by=popularity.asc&page=1&year=2020
		return "https://api.themoviedb.org/3/search/movie?api_key="+apiKey+"&language=en-US&sort_by=popularity.asc&page=1&query=" + year;
	}
	
	//searchMoviesByPage_uri--------------------------------------------------------------------------------------------------
	public String searchMoviesByPage_uri(int page) {
		//https://api.themoviedb.org/3/discover/movie?api_key=3aacfef6a62a872d2a4717b9b6cd5283&language=en-US&sort_by=popularity.desc&page=1
		return "https://api.themoviedb.org/3/discover/movie?api_key="+apiKey+"&language=en-US&sort_by=popularity.asc&page=" + page;
	}
	
	
	/////////////     Search      /////////////
	
	//searchByTitle_uri--------------------------------------------------------------------------------------------------
	public String searchByTitle_uri(String title_to_search) {
		return "https://api.themoviedb.org/3/search/movie?api_key="+apiKey+"&language=en-US&query="+title_to_search;
	}
	
	//findByIdPeople_uri------------------------------------------------------------------------------------------------------
	public String findByIdPeople_uri(int id) {
		return "https://api.themoviedb.org/3/movie/"+id+"/credits";
	}
	
	//getAllGenres()----------------------------------------------------------------------------------------------------
	public List<Genre> getAllGenres() throws IOException, InterruptedException {
		String uri = listAllGenres_uri();
		
		HttpClient client = HttpClient.newHttpClient();
	    HttpRequest request = HttpRequest.newBuilder()
	          .uri(URI.create(uri))
	          .build();
	    
	    HttpResponse<Supplier<AllGenresBody>> response = client.send(request, new JsonBodyHandler<>(AllGenresBody.class));
	    Supplier<AllGenresBody> allGenres_result = response.body();
	    
	    String genres_str = allGenres_result.get().genres.toPrettyString();
	    
	    ObjectMapper mapper = new ObjectMapper();
	    List<Genre> genreList = mapper.readValue(genres_str, new TypeReference<List<Genre>>() {});
	    
	    return genreList;
	}
	
	//getMovieByGenre()----------------------------------------------------------------------------------------------------
		public List<Movie> getMoviesByGenre(int genre_id) throws IOException, InterruptedException {
			String uri = searchByGenre_uri(genre_id);
			//uri = "https://api.themoviedb.org/3/discover/movie?api_key=3aacfef6a62a872d2a4717b9b6cd5283&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&with_genre=14&page=1";
			//System.out.println(uri);
			
			HttpClient client = HttpClient.newHttpClient();
		    HttpRequest request = HttpRequest.newBuilder()
		          .uri(URI.create(uri))
		          .build();
		    
		    HttpResponse<Supplier<TMDBResult>> response = client.send(request, new JsonBodyHandler<>(TMDBResult.class));
		    Supplier<TMDBResult> TMDB_result = response.body();
		    
		    String results_str = TMDB_result.get().results.toPrettyString();
		    
		    ObjectMapper mapper = new ObjectMapper();
		    List<Movie> movieList = mapper.readValue(results_str, new TypeReference<List<Movie>>() {});
		    
		    return movieList;
		}
		
	//getMoviesByYear()----------------------------------------------------------------------------------------------------
	public List<Movie> getMoviesByYear(String year) throws IOException, InterruptedException {
		String uri = searchByYear_uri(year);
		//System.out.println(uri);
		
		HttpClient client = HttpClient.newHttpClient();
	    HttpRequest request = HttpRequest.newBuilder()
	          .uri(URI.create(uri))
	          .build();
	    
	    HttpResponse<Supplier<TMDBResult>> response = client.send(request, new JsonBodyHandler<>(TMDBResult.class));
	    Supplier<TMDBResult> TMDB_result = response.body();
	    
	    String results_str = TMDB_result.get().results.toPrettyString();
	    ObjectMapper mapper = new ObjectMapper();
	    List<Movie> movieList = mapper.readValue(results_str, new TypeReference<List<Movie>>() {});

	    return movieList;
	    
	}
	
	//getMoviesByPage()----------------------------------------------------------------------------------------------------
	public String getMoviesByPage_asJsonString(int page) throws IOException, InterruptedException {
		String uri = searchMoviesByPage_uri(page);
		//System.out.println(uri);
		
		HttpClient client = HttpClient.newHttpClient();
	    HttpRequest request = HttpRequest.newBuilder()
	          .uri(URI.create(uri))
	          .build();
	    
	    HttpResponse<Supplier<TMDBResult>> response = client.send(request, new JsonBodyHandler<>(TMDBResult.class));
	    Supplier<TMDBResult> TMDB_result = response.body();
	    
	    String results_str = TMDB_result.get().results.toPrettyString();
	    
	    return results_str;
	}
	
	//getMoviesByPage()----------------------------------------------------------------------------------------------------
	public List<Movie> getMoviesByPage_asList(int page) throws IOException, InterruptedException {
		String responseJson = getMoviesByPage_asJsonString(page);
	    
		List<Movie> movieList = jsonStringToList(responseJson);

	    return movieList;
	}
	
	//getRandomMovies_asList()----------------------------------------------------------------------------------------------------
	public List<Movie> getRandomMovies_asList(int count) throws IOException, InterruptedException {
		int total_count = count * 5;
		int n_pages = (int)java.lang.Math.ceil((double)total_count/pageSize);
		
		List<Movie> pageMovieList = new ArrayList<>();
		List<Movie> totalMovieList = new ArrayList<>();
		
		List<Movie> randomMovieList = new ArrayList<>();
		
		for (int page=1; page<=n_pages; page++) {
			pageMovieList = getMoviesByPage_asList(page);
			
			totalMovieList.addAll(pageMovieList);
		}
		
		// select some movies randomly
		
		int listSize = totalMovieList.size();
		
		
		
		List<Integer> rand_index_list = new ArrayList<>();
		
		int rand_count = 0;
		int rand_index = 0;
		
		while (rand_count < count) {
			rand_index = rand.nextInt(listSize);
			
			if (!rand_index_list.contains(rand_index)) {
				rand_index_list.add(rand_index);
				rand_count++;
			}
		}
		
		for (int i=0; i<count; i++) {
			if (totalMovieList.get(rand_index_list.get(i).intValue()).poster_path.equals("null")) {
				continue;
			}
			randomMovieList.add(totalMovieList.get(rand_index_list.get(i).intValue()));
		}
	    return randomMovieList;		    
	}
	
	//getRandomMovies_asJsonString()----------------------------------------------------------------------------------------------------
		public String getRandomMovies_asJsonString(int count) throws IOException, InterruptedException {
			List<Movie> movieList = getRandomMovies_asList(count);
			
			ObjectMapper mapper = new ObjectMapper();
			
			String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(movieList);
			
			return jsonString;
		}
	
	//getMoviesByTitle()----------------------------------------------------------------------------------------------------
	public List<Movie> getMoviesByTitle(String title) throws IOException, InterruptedException {
		String uri = searchByTitle_uri(title);
		//uri = "https://api.themoviedb.org/3/search/movie?api_key=3aacfef6a62a872d2a4717b9b6cd5283&language=en-US&query="; 
		//System.out.println(uri);
		
		HttpClient client = HttpClient.newHttpClient();
	    HttpRequest request = HttpRequest.newBuilder()
	          .uri(URI.create(uri))
	          .build();
	    
	    HttpResponse<Supplier<TMDBResult>> response = client.send(request, new JsonBodyHandler<>(TMDBResult.class));
	    Supplier<TMDBResult> TMDB_result = response.body();
	    
	    String results_str = TMDB_result.get().results.toPrettyString();
	    
	    ObjectMapper mapper = new ObjectMapper();
	    List<Movie> movieList = mapper.readValue(results_str, new TypeReference<List<Movie>>() {});
	    
	    return movieList;
	}
	
	//searchByTitle_uri--------------------------------------------------------------------------------------------------
	private String uriFromJson(String requestJson) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		mapper.setDateFormat(df);
		TMDB_Request requestData = mapper.readValue(requestJson, TMDB_Request.class);
		
//		JsonNode request = mapper.readTree(requestJson);
//		boolean include_adult = request.get("include_adult").asBoolean();
		
//		boolean include_adult = requestData.include_adult;
//		float vote_average_gte = requestData.vote_average_gte;
//		float vote_average_lte = requestData.vote_average_lte;
//		int page = requestData.page;
//		Date primary_release_date_gte = requestData.primary_release_date_gte;
//		Date primary_release_date_lte = requestData.primary_release_date_lte;
//		List<Integer> with_genres = requestData.with_genres;
//		
//		System.out.println(include_adult);
//		System.out.println(vote_average_gte);
//		System.out.println(vote_average_lte);
//		System.out.println(page);
//		System.out.println(primary_release_date_gte);
//		System.out.println(primary_release_date_lte);
//		System.out.println(with_genres);
		
		String uri = "https://api.themoviedb.org/3/discover/movie?api_key=" + apiKey + "&language=en-US&sort_by=popularity.asc";
		
		uri += "&include_adult=" + requestData.include_adult;
		uri += "&vote_average.gte=" + requestData.vote_average_gte;
		uri += "&vote_average.lte=" + requestData.vote_average_lte;
		uri += "&page=" + requestData.page;
		uri += "&primary_release_date.gte=" + df.format(requestData.primary_release_date_gte);
		uri += "&primary_release_date.lte=" + df.format(requestData.primary_release_date_lte);
		uri += "&with_genres=" + requestData.with_genres.toString().replaceAll("\\s+","");
		
//		System.out.println("");
//		System.out.println(uri);
		
		return uri;
	}
	
	//executeRequest_asJsonString()----------------------------------------------------------------------------------------------------
	public String executeRequest_asJsonString(String requestJson) throws IOException, InterruptedException {
		String uri = uriFromJson(requestJson);
		
		//System.out.println(uri);
		
		HttpClient client = HttpClient.newHttpClient();
	    HttpRequest request = HttpRequest.newBuilder()
	          .uri(URI.create(uri))
	          .build();
	    
	    HttpResponse<Supplier<TMDBResult>> response = client.send(request, new JsonBodyHandler<>(TMDBResult.class));
	    Supplier<TMDBResult> TMDB_result = response.body();
	    
	    String results_str = TMDB_result.get().results.toPrettyString();
	    
	    return results_str;
	}
	
	private List<Movie> jsonStringToList(String jsonString) throws JsonMappingException, JsonProcessingException  {
		ObjectMapper mapper = new ObjectMapper();
	    List<Movie> movieList = mapper.readValue(jsonString, new TypeReference<List<Movie>>() {});

	    return movieList;
	}
	
	//executeRequest_asList()----------------------------------------------------------------------------------------------------
	public List<Movie> executeRequest_asList(String requestJson) throws IOException, InterruptedException  {
		String responseJson = executeRequest_asJsonString(requestJson);
		List<Movie> movieList = jsonStringToList(responseJson);

	    return movieList;
	}
	
	
}//finClass

	
