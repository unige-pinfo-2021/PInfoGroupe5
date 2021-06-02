package api.rest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
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

//	@Test
//	void testTest() throws IOException, InterruptedException {
//		RestServiceFilms service2 = new RestServiceFilms();
//		assertEquals("RESTEASY003210: Could not find resource for full path: http://tindfilm/film/testhead",service2.test().toString());
//	}
	
	@Test
	void testGetRandomMovies() throws IOException, InterruptedException {
		// 1 - get n times random movies
		// 2 - merge them as a single set
		// 3 - get the ratio over n*40 (first time)
		// 4 - check the ratio to be in a rational range
		Set<Movie> all_movies_set = new HashSet<Movie>();
		
		
		int N = 10;
		List<Movie> movies_list;
		Set<Movie> movies_set;
		for (int i=0; i<N; i++) {
			movies_list = service.getRandomMovies();
			//assertEquals(40,movies_list.size());
			movies_set = new HashSet<Movie>(movies_list);
			//assertEquals(40,movies_set.size());
			
			all_movies_set.addAll(movies_set);
		}
		
		System.out.println(all_movies_set.size()/(40*N));
		
		assertTrue(all_movies_set.size() > 10);
	}

//	@Test
//	void testGetrestcall() {
//		fail("Not yet implemented");
//	}

	
	@Test
	void testGetMovieById() throws IOException, InterruptedException {
		service.getMovieById(25);
		
	}


	@Test
	void testGetRecommandationMovies() throws IOException, InterruptedException {
		service.getRecommandationMovies(25);
		
	}
	
}
