package api.model;
import java.io.IOException;
import java.util.List;
//import java.util.Scanner;


public class Rest_Caller {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		//variables
		String apiKey = "3aacfef6a62a872d2a4717b9b6cd5283";
		//String title_to_search;
		//int genre_id;
		//String year;
	
		//create object 
		TMDB_Caller tmdb_caller = new TMDB_Caller(apiKey);
		
		//Scanner scanner = new Scanner(System.in);
		
		
//		//display all genres--------------------------------------------------------------------------------------------------
//		System.out.println("List of All Genres : \n");
//		List<Genre> genreList = tmdb_caller.getAllGenres();
//		Genre genre;
//	    for (int i=0; i<genreList.size(); i++) {
//	    	genre = genreList.get(i);
//	    	System.out.println("Genre " + (i+1) + " : " + genre.name);
//	    }	
//		
//		//search by genre----------------------------------------------------------------------------------------------------
//		
//		System.out.println("Select your desired Genre to search: \n");
//		genre_id = genreList.get(scanner.nextInt()-1).id;
//		
//		List<Movie> movieList1 = tmdb_caller.getMoviesByGenre(genre_id);
//		for (int i=0; i<10; i++)
//	    	System.out.println("Movie " + (i+1) + ": " + movieList1.get(i).title);
//	
//	
//	    //search by title----------------------------------------------------------------------------------------------------
//		
//		System.out.println("Title to search: \n");
//		title_to_search = scanner.next();	
//		
//		List<Movie> movieList2 = tmdb_caller.getMoviesByTitle(title_to_search);
//		for (int i=0; i<movieList2.size(); i++)
//	    	System.out.println("Movie " + (i+1) + ": " + movieList2.get(i).title + "(" + movieList2.get(i).release_date
//	    			+ ")");
//		
//		
//		//search by year-----------------------------------------------------------------------------------------------------
//		
//		System.out.println("Year: \n");
//		year = scanner.next();
//		
//		List<Movie> movieList3 = tmdb_caller.getMoviesByYear(year);
//		
//		for (int i=0; i<movieList3.size(); i++)
//	    	System.out.println( movieList3.get(i).title + " --> "+ movieList3.get(i).release_date);
		
		
		
		//get random movies-----------------------------------------------------------------------------------------------------
		
//		System.out.println("Count of Random Movies: \n");
//		int rand_count = scanner.nextInt();
//		
//		List<Movie> randomMovieList = tmdb_caller.getRandomMovies(rand_count);
//		
//		for (int i=0; i<randomMovieList.size(); i++)
//	    	System.out.println((i+1) + ": " + randomMovieList.get(i).title + " --> "+ randomMovieList.get(i).release_date);
//		
//		final ObjectMapper mapper = new ObjectMapper();
//		
//		System.out.println("Result as JSON : \n");
//		
//		String base_url = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2";
//		
//		for (Movie movie : randomMovieList)
//			if (movie.poster_path != null)
//				movie.poster_path = base_url + movie.poster_path;
//			else
//				movie.poster_path = "";
//		
//	    System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(randomMovieList));
		
		
		String requestJson = "{\n"
				+ "	\"include_adult\": false,\n"
				+ "	\"vote_average.gte\": 0.9,\n"
				+ "	\"vote_average.lte\": 3.2,\n"
				+ "	\"page\": 4,\n"
				+ "	\"primary_release_date.gte\": \"2009-03-07\",\n"
				+ "	\"primary_release_date.lte\": \"2021-04-12\",\n"
				+ "	\"with_people\": 1,\n"
				+ "	\"with_genres\": [28,12,16],\n"
				+ "	\"with_keywords\": 1\n"
				+ "}";
		
//		String responseJsonString = tmdb_caller.executeRequest_asJsonString(requestJson);
//		
//		System.out.println(responseJsonString);
		
		List<Movie> movieList = tmdb_caller.executeRequest_asList(requestJson);
		
		Movie.printList(movieList);
		
		
		//String responseJsonString = tmdb_caller.getRandomMovies_asJsonString(3);
		
		//System.out.println(responseJsonString);
		
	}//main
	
}//Rest_caller




