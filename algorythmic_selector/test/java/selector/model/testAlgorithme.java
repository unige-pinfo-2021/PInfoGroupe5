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

}



























