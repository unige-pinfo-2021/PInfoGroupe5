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

import java.security.SecureRandom ;

public class TMDB_Caller {

	private String apiKey;
	private int pageSize = 20;

	//private Random rand = new Random();
	private SecureRandom rand = new SecureRandom();

	private final static String APIDISCADRESS = "https://api.themoviedb.org/3/discover/movie?api_key=";
	private final static String APISEARCHADRESS = "https://api.themoviedb.org/3/search/movie?api_key=";
	private final static String APISEARCHID="https://api.themoviedb.org/3/movie/";

	public TMDB_Caller(String apiKey) {
		super();
		this.apiKey = apiKey;
	}


	public String listAllGenres_uri() {
		//https://api.themoviedb.org/3/genre/movie/list?api_key=3aacfef6a62a872d2a4717b9b6cd5283&language=en-US
		return APIDISCADRESS+apiKey+"&language=en-US";
	}



	public String searchByGenre_uri(int genre_id) {
		return APIDISCADRESS+apiKey+"&language=en-US&sort_by=popularity.desc&page=1&include_adult=false&include_video=false&with_genres=" + genre_id;
	}


	public String searchByYear_uri(String year) {
		//https://api.themoviedb.org/3/discover/movie?api_key=3aacfef6a62a872d2a4717b9b6cd5283&language=en-US&sort_by=popularity.asc&page=1&year=2020
		return APISEARCHADRESS+apiKey+"&language=en-US&sort_by=popularity.asc&page=1&query=" + year;
	}


	public String searchMoviesByPage_uri(int page) {
		//https://api.themoviedb.org/3/discover/movie?api_key=3aacfef6a62a872d2a4717b9b6cd5283&language=en-US&sort_by=popularity.desc&page=1
		return APIDISCADRESS+apiKey+"&language=en-US&sort_by=popularity.asc&page=" + page;
	}


	public String searchByTitle_uri(String title_to_search) {
		return APISEARCHADRESS+apiKey+"&language=en-US&query="+title_to_search;
	}

	public String findByIdPeople_uri(int id) {
		return APISEARCHID+id+"/credits";
	}


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

		return mapper.readValue(genres_str, new TypeReference<List<Genre>>() {});

	}


	public List<Movie> getMoviesByGenre(int genre_id) throws IOException, InterruptedException {
		String uri = searchByGenre_uri(genre_id);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri))
				.build();

		HttpResponse<Supplier<TMDBResult>> response = client.send(request, new JsonBodyHandler<>(TMDBResult.class));
		Supplier<TMDBResult> TMDB_result = response.body();

		String results_str = TMDB_result.get().results.toPrettyString();

		ObjectMapper mapper = new ObjectMapper();

		return mapper.readValue(results_str, new TypeReference<List<Movie>>() {});

	}


	public List<Movie> getMoviesByYear(String year) throws IOException, InterruptedException {
		String uri = searchByYear_uri(year);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri))
				.build();

		HttpResponse<Supplier<TMDBResult>> response = client.send(request, new JsonBodyHandler<>(TMDBResult.class));
		Supplier<TMDBResult> TMDB_result = response.body();

		String results_str = TMDB_result.get().results.toPrettyString();
		ObjectMapper mapper = new ObjectMapper();

		return mapper.readValue(results_str, new TypeReference<List<Movie>>() {});

	}


	public String getMoviesByPage_asJsonString(int page) throws IOException, InterruptedException {
		String uri = searchMoviesByPage_uri(page);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri))
				.build();

		HttpResponse<Supplier<TMDBResult>> response = client.send(request, new JsonBodyHandler<>(TMDBResult.class));
		Supplier<TMDBResult> TMDB_result = response.body();

		return TMDB_result.get().results.toPrettyString();

	}


	public List<Movie> getMoviesByPage_asList(int page) throws IOException, InterruptedException {
		String responseJson = getMoviesByPage_asJsonString(page);

		return jsonStringToList(responseJson);
	}

	
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
			if (totalMovieList.get(rand_index_list.get(i).intValue()).poster_path == null) {
				continue;
			}
			randomMovieList.add(totalMovieList.get(rand_index_list.get(i).intValue()));
		}
		return randomMovieList;		    
	}


	public String getRandomMovies_asJsonString(int count) throws IOException, InterruptedException {
		List<Movie> movieList = getRandomMovies_asList(count);

		ObjectMapper mapper = new ObjectMapper();

		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(movieList);
	}


	public List<Movie> getMoviesByTitle(String title) throws IOException, InterruptedException {
		String uri = searchByTitle_uri(title);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri))
				.build();

		HttpResponse<Supplier<TMDBResult>> response = client.send(request, new JsonBodyHandler<>(TMDBResult.class));
		Supplier<TMDBResult> TMDB_result = response.body();

		String results_str = TMDB_result.get().results.toPrettyString();

		ObjectMapper mapper = new ObjectMapper();

		return mapper.readValue(results_str, new TypeReference<List<Movie>>() {});
	}


	private String uriFromJson(String requestJson) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		mapper.setDateFormat(df);
		TMDB_Request requestData = mapper.readValue(requestJson, TMDB_Request.class);

		String uri = APIDISCADRESS+ apiKey + "&language=en-US&sort_by=popularity.asc";

		uri += "&include_adult=" + requestData.include_adult;
		uri += "&vote_average.gte=" + requestData.vote_average_gte;
		uri += "&vote_average.lte=" + requestData.vote_average_lte;
		uri += "&page=" + requestData.page;
		uri += "&primary_release_date.gte=" + df.format(requestData.primary_release_date_gte);
		uri += "&primary_release_date.lte=" + df.format(requestData.primary_release_date_lte);
		uri += "&with_genres=" + requestData.with_genres.toString().replaceAll("\\s+","");


		return uri;
	}


	public String executeRequest_asJsonString(String requestJson) throws IOException, InterruptedException {
		String uri = uriFromJson(requestJson);


		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri))
				.build();

		HttpResponse<Supplier<TMDBResult>> response = client.send(request, new JsonBodyHandler<>(TMDBResult.class));
		Supplier<TMDBResult> TMDB_result = response.body();

		return TMDB_result.get().results.toPrettyString();

	}

	private List<Movie> jsonStringToList(String jsonString) throws JsonMappingException, JsonProcessingException  {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonString, new TypeReference<List<Movie>>() {});

	}


	public List<Movie> executeRequest_asList(String requestJson) throws IOException, InterruptedException  {
		String responseJson = executeRequest_asJsonString(requestJson);
		return jsonStringToList(responseJson);
	}
	

	public Movie getMovieById(int id) throws IOException, InterruptedException {
		String uri = getMovieById_uri(id);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri))
				.build();

		HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
		String result_str = response.body().toString();


		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(result_str, new TypeReference<Movie>() {});
	}

	public String getMovieById_uri(int id) {
		//https://api.themoviedb.org/3/movie/25?api_key=3aacfef6a62a872d2a4717b9b6cd5283&language=en-US
		return APISEARCHID+ id + "?api_key=" + apiKey + "&language=en-US" ;
	} 


	public String getMovieById_asJsonString(int id) throws IOException, InterruptedException {
		Movie movie = getMovieById(id);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(movie);
	}

	public String getRecommandationMovies_asJsonString(int Movie_Id) throws IOException, InterruptedException {
		String uri = RecommandationMovies_uri(Movie_Id);
		System.out.println(uri);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri))
				.build();

		HttpResponse<Supplier<TMDBResult>> response = client.send(request, new JsonBodyHandler<>(TMDBResult.class));
		Supplier<TMDBResult> TMDB_result = response.body();

		return TMDB_result.get().results.toPrettyString();

	}
	

	public String RecommandationMovies_uri(int Movie_Id) {
		//https://api.themoviedb.org/3/movie/125/recommendations?api_key=3aacfef6a62a872d2a4717b9b6cd5283&language=en-US
		return APISEARCHID+ Movie_Id+"/recommendations?api_key="+ apiKey + "&language=en-US&sort_by=popularity.asc";
	}
}//finClass


