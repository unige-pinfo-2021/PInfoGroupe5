package api.group;

import api.film.FilmService;
import api.user.UserService;

import java.util.*;
import java.net.*;
import java.io.*;

public class GroupController{

	//private FilmService;
	//private UserService;

	public GroupController(){System.out.println("Groupe Controlleur cree");}

	// getGroup()

	// getSelectFilms() for a group

	// set selection films for a group
	
	public void run()
	{
		try{
		ServerSocket serverSocket = new ServerSocket(2000);
		Socket clientSocket = serverSocket.accept();
		
		PrintWriter out;
    		BufferedReader in;
		
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		out.println("Serveur groupe");
		
		in.close();
		out.close();

		clientSocket.close();
		}
		catch(IOException ioEx)
        	{
       	}
	}
 
}//end class
