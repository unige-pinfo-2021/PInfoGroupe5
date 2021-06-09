package api.rest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import api.model.Movie;

class RestServiceFilmsTest {
	private RestServiceFilms service;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("BeforeAll test");
	}

	@BeforeEach
	void setUp() throws Exception {
		service = new RestServiceFilms();
	}

	@Test
	void testRestServiceFilms() {
		RestServiceFilms service2 = new RestServiceFilms();
		assertNotEquals(null,service2);
	}

	@Test
	void testGetRandomMovies() throws IOException, InterruptedException {
		// 1 - get n times random movies
		// 2 - merge them as a single set
		// 3 - get the ratio over n*rnd_count (first time)
		// 4 - check the ratio to be in a rational range
		Set<Movie> all_movies_set = new HashSet<Movie>();
		
		
		int N = 10;
		int rnd_count= 80;
		List<Movie> movies_list;
		Set<Movie> movies_set;
		for (int i=0; i<N; i++) {
			movies_list = service.getRandomMovies();
//			System.out.println("dadaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//			System.out.println(movies_list.size());
//			System.out.println(movies_list);
			assertEquals(rnd_count,movies_list.size());
			movies_set = new HashSet<Movie>(movies_list);
			assertEquals(rnd_count,movies_set.size());
			
			all_movies_set.addAll(movies_set);
		}
		
		System.out.println(all_movies_set.size()/(rnd_count*N));
		
		assertTrue(all_movies_set.size() > (rnd_count*N*6)/10);
	}

	@Test
	void testGetRestCall() throws IOException, InterruptedException {
		// check vote_average
		// check sort_by=popularity.desc
		// check page: request page 1 and page 2, check the order
		
		//https://api.themoviedb.org/3/discover/movie?api_key=3aacfef6a62a872d2a4717b9b6cd5283&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&primary_release_date.gte=2015-01-01&primary_release_date.lte=2022-01-01&vote_average.gte=0&vote_average.lte=3
		
		Movie movie;
		double vote_average_gte = 0.4;
		double vote_average_lte = 5;
		int page = 1;
		String primary_release_date_gte = "2015-01-01";
		String primary_release_date_lte = "2022-01-01";
		String with_people = "";
		String with_genres = "28";
		String with_keywords = "";
		List<Movie> movie_list = service.getrestcall(vote_average_gte, vote_average_lte, page, primary_release_date_gte, primary_release_date_lte, with_people, with_genres, with_keywords);
		double prev_popularity = 1000000;
		double popularity = 1000000;
		for (int i=0; i < movie_list.size(); i++) {
			movie = movie_list.get(i);
			
			assertTrue(movie.vote_average >= vote_average_gte);
			assertTrue(movie.vote_average <= vote_average_lte);
			
			prev_popularity = popularity;
			popularity = movie.popularity;
			// popularity is hard-coded descending
			System.out.println("Popularity order check");
			System.out.println(i);
			System.out.println(popularity);
			System.out.println(prev_popularity);
			assertTrue(popularity<= prev_popularity);
		}
	}
//	
	
	@Test
	void testGetMovieById() throws IOException, InterruptedException {
		List<Integer> id_list = new ArrayList<>(Arrays.asList(25,30,87,100));
		Movie movie;
		int movie_id;
		for (int i=0; i<id_list.size(); i++) {
			movie_id = id_list.get(i);
			movie = service.getMovieById(movie_id);
			assertEquals(movie.id,movie_id);
		}
		
	}
	
	@Test
	void testGetRecommandationMovies() throws IOException, InterruptedException {
		List<Integer> id_list = new ArrayList<>(Arrays.asList(125,30,41,87,100));
		Movie movie;
		int movie_id = id_list.get(0);
		List<Movie> movie_list = service.getRecommandationMovies(movie_id);
		for (int i=0; i<movie_list.size(); i++) {
			// Test Logic ???
		}
		
	}

}
