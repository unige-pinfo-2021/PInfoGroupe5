/**
 * 
 */
package api.rest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import api.model.Group;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




class algorithmeTest {
	private selector algo;
	
	
	
	@Test
	void testalgo() throws Exception, IOException, InterruptedException {
		int[][] table = new int[5][40];
		for(int i = 0; i<5;i++)
		{
			for(int j = 0; j<40; j++)
			{
				table[i][j] = i*40 + j;
			}
		}
		int[] rec = {1000,2000,3000,4000}
		for(int i = 5; i>1;i--)
		{
			for(int k= 0; k<i;k++)
			{
				table[k][i] = rec[i];
			}
		}
		
		
		int[] resultat = this.algo.algorithme(table);
		
		for(int i = 0; i<4; i++)
		{
			assertEquals(resultat[i],rec[i]);
		}
		//fail("Not yet implemented");
	}
	
	@Test
    void testGetRecommandationMovies() throws IOException, InterruptedException {
        List<Integer> id_list = new ArrayList<>(Arrays.asList(25,30,87,100,337404,460465,791373,602734,825597));
        int movie_id;
        List<Movie> movie_list;
        for (int i=0; i<id_list.size(); i++) {
            movie_id = id_list.get(i);
            movie_list = service.getRecommandationMovies(movie_id);
//            System.out.println("movie_id : ");
//            System.out.println(movie_id);
            assertTrue(movie_list.size() > 0);
        }
	
	

}



























