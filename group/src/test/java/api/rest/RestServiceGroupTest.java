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



/**
 * @author mahyadaqiqui
 *
 */
class RestServiceGroupTest {
	private RestServiceGroup service;
	//private User user1;
	private Group group1;
	

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("BeforeAll test");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		service = new RestServiceGroup();
		
		Map<String, String> inputJSON = new HashMap<String, String>();
		inputJSON.put("groupName","group1");
		inputJSON.put("admin","tom");
		inputJSON.put("invitation","group1_invitation");
		service.createGroupe(inputJSON);
	}

	/**
	 * Test method for {@link api.rest.RestServiceGroup#RestServiceGroup()}.
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	@Test
	void testRestServiceGroup() throws IOException, InterruptedException {
		RestServiceGroup service2 = new RestServiceGroup();
		service2.Default();
		assertEquals("You reached group",service2.Default());
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link api.rest.RestServiceGroup#Default()}.
	 */
	@Test
	void testDefault() {
		service.Default();
		assertEquals("You reached group",service.Default());
	}

	/**
	 * Test method for {@link api.rest.RestServiceGroup#createGroupe(java.util.Map)}.
	 */
	@Test
	void testCreateGroupe() {
		Map<String, String> inputJSON = new HashMap<String, String>();
		inputJSON.put("groupName","group2");
		inputJSON.put("admin","jerry");
		inputJSON.put("invitation","group2_invitation");
		service.createGroupe(inputJSON);
		
		
	}

	/**
	 * Test method for {@link api.rest.RestServiceGroup#getGroupUsers(java.lang.String)}.
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	@Test
	void testGetGroupUsers() throws IOException, InterruptedException {
		Map<String, String> groupJSON = new HashMap<String, String>();
		groupJSON.put("groupName","group3");
		groupJSON.put("admin","jerry");
		groupJSON.put("invitation","group3_invitation");
		service.createGroupe(groupJSON);
		
		Map<String, String> userJSON1 = new HashMap<String, String>();
		userJSON1.put("userName","user1");
		userJSON1.put("invitation","group3_invitation");
		service.addUser(userJSON1);
		
		Map<String, String> userJSON2 = new HashMap<String, String>();
		userJSON2.put("userName","user2");
		userJSON2.put("invitation","group3_invitation");
		service.addUser(userJSON2);
		
		Map<String, String> userJSON3 = new HashMap<String, String>();
		userJSON3.put("userName","user3");
		userJSON3.put("invitation","group3_invitation");
		service.addUser(userJSON3);
		
		ArrayList<String> group3_users = service.getGroupUsers("group3");
		
		Set<String> group3_added_users_set = new HashSet<String>(group3_users);
		
		ArrayList<String> original_users = new ArrayList<String>();
		original_users.add("user1");
		original_users.add("user2");
		original_users.add("user3");
		
		Set<String> original_users_set = new HashSet<String>(original_users);
		
		assertTrue(group3_added_users_set.equals(original_users_set));
	}

	/**
	 * Test method for {@link api.rest.RestServiceGroup#getAllGroupUsers(java.lang.String)}.
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	@Test
	void testGetAllGroupUsers() throws IOException, InterruptedException {
		Map<String, ArrayList<String>> allGroupUsers = service.getAllGroupUsers();
		
		ArrayList<String> users1;
		ArrayList<String> users2;
		Set<String> user_set1;
		Set<String> user_set2;
		
		for (Map.Entry<String, ArrayList<String>> group_users : allGroupUsers.entrySet()) {
	        //System.out.println(group_users.getKey() + ":" + group_users.getValue());
			users1 = group_users.getValue();
			user_set1 = new HashSet<String>(users1);
			String group_name = group_users.getKey();
			
			users2 = service.getGroupUsers(group_name);
			user_set2 = new HashSet<String>(users2);
			
			assertTrue(user_set1.equals(user_set2));
	    }
	}

	/**
	 * Test method for {@link api.rest.RestServiceGroup#addUser(java.util.Map, java.lang.String)}.
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	@Test
	void testAddUser() throws IOException, InterruptedException {
		Map<String, String> groupJSON = new HashMap<String, String>();
		groupJSON.put("groupName","group4");
		groupJSON.put("admin","jerry");
		groupJSON.put("invitation","group4_invitation");
		service.createGroupe(groupJSON);
		
		Map<String, String> userJSON1 = new HashMap<String, String>();
		userJSON1.put("userName","user4");
		userJSON1.put("invitation","group4_invitation");
		service.addUser(userJSON1);
		
		Map<String, String> userJSON2 = new HashMap<String, String>();
		userJSON2.put("userName","user5");
		userJSON2.put("invitation","wrong_invitation");
		service.addUser(userJSON2);
		
		ArrayList<String> group4_users = service.getGroupUsers("group4");
		
		Set<String> group4_added_users_set = new HashSet<String>(group4_users);
		
		assertTrue(group4_added_users_set.contains("user4"));
		assertFalse(group4_added_users_set.contains("user5"));
	}

	/**
	 * Test method for {@link api.rest.RestServiceGroup#deleteGroup(java.util.Map, java.lang.String)}.
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	@Test
	void testDeleteGroup() throws IOException, InterruptedException {
		Map<String, String> groupJSON = new HashMap<String, String>();
		groupJSON.put("groupName","group5");
		groupJSON.put("admin","tom");
		groupJSON.put("invitation","group5_invitation");
		service.createGroupe(groupJSON);
		
		Map<String, String> groupJSON2 = new HashMap<String, String>();
		groupJSON2.put("admin","tom");
		
		service.deleteGroup(groupJSON2, "group5");
		
		Map<String, ArrayList<String>> all_group_users = service.getAllGroupUsers();
		assertFalse(all_group_users.containsKey("group5"));
	}

	/**
	 * Test method for {@link api.rest.RestServiceGroup#deleteUser(java.util.Map, java.lang.String, java.lang.String)}.
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	@Test
	void testDeleteUser() throws IOException, InterruptedException {
		Map<String, String> groupJSON = new HashMap<String, String>();
		groupJSON.put("groupName","group6");
		groupJSON.put("admin","tom");
		groupJSON.put("invitation","group6_invitation");
		service.createGroupe(groupJSON);
		
		Map<String, String> userJSON = new HashMap<String, String>();
		userJSON.put("userName","user6");
		userJSON.put("invitation","group6_invitation");
		service.addUser(userJSON);
		
		Map<String, String> groupJSON2 = new HashMap<String, String>();
		groupJSON2.put("admin","tom");
		
		service.deleteUser(groupJSON2, "group6", "user6");
		
		ArrayList<String> group6_users = service.getGroupUsers("group6");
		
		Set<String> group6_users_set = new HashSet<String>(group6_users);
		
		assertFalse(group6_users_set.contains("user6"));
	}

}



























