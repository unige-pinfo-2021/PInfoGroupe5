/**
 * 
 */
package api.rest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import api.model.User;

/**
 * @author mahyadaqiqui
 *
 */
class RestServiceUserTest {
	
	private RestServiceUser service;
	private User user1;
	private User user2;
	private User user3;
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
		service = new RestServiceUser();
		/*
		String username1 = "mahya";
		String email1 = "mahya@gmail.com";
		user1 = new User(username1, email1);
		service.addUser(user1);
		*/
		user2 = new User("tom", "sapin@");
		user3 = new User("jerry", "cormier@");
	}

	/**
	 * Test method for {@link api.rest.RestServiceUser#RestServiceUser()}.
	 */
	@Test
	void testRestServiceUser() {
		RestServiceUser service2 = new RestServiceUser();
		service2.hello();
		assertEquals("Hello from users !",service2.hello());
	}

	/**
	 * Test method for {@link api.rest.RestServiceUser#hello()}.
	 */
	@Test
	void testHello() {
		//System.out.println("Hello from test user");
		service.hello();
		assertEquals("Hello from users !",service.hello());	
	}

	/**
	 * Test method for {@link api.rest.RestServiceUser#getConn()}.
	 */
	@Test
	void testGetConn() {
		service.getConn();
		assertEquals("Connection established!", service.getConn());
	}

	/**
	 * Test method for {@link api.rest.RestServiceUser#getUser(java.lang.String)}.
	 */
	@Test
	void testGetUser() {
		//assertEquals(null,service.getUser("garbage"));
		//System.out.println(service.getUser("garbage"));
		assertEquals(user2.getUsername(),service.getUser(user2.getUsername()).getUsername());
		assertEquals(user2.getEmail(),service.getUser(user2.getUsername()).getEmail());
				
		assertEquals(user3.getUsername(),service.getUser(user3.getUsername()).getUsername());
		assertEquals(user3.getEmail(),service.getUser(user3.getUsername()).getEmail());
	}

	/**
	 * Test method for {@link api.rest.RestServiceUser#existUser(java.lang.String)}.
	 */
	@Test
	void testExistUser() {
		//assertFalse(service.existUser("garbage"));
		//assertTrue(service.existUser(user2.getUsername()));
		assertTrue(service.existUser("tom"));
		assertTrue(service.existUser(user3.getUsername()));	
	}

}
