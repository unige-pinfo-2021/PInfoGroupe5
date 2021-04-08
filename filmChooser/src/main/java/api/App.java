package api;

import java.util.*;

import api.film.*;
import api.user.*;

import org.json.*;
import com.google.gson.*;

import java.io.IOException;

public class App 
{

    public static void main( String[] args ) throws IOException, InterruptedException
    {
        System.out.println( "Hello World!" );
        
	Film film = new Film("Film","Producer");
	User user = new User("User","email");

	System.out.println( user.getUsername()+" is watching "+ film.getTitle());

	Rest_Caller rest_caller = new Rest_Caller();

	Scanner sc = new Scanner(System.in);
	System.out.println("Select ''type'' of your Title to search for:\n ");	
	int title_type;
	 title_type = sc.nextInt();
	System.out.println("Enter ''name'' of your Title to search for:\n ");
	String title_to_search; 
	title_to_search= sc.nextLine();
	title_to_search = sc.nextLine();

	String result = rest_caller.getFilm(title_type, title_to_search);

        System.out.println(result + "\n \n");

	//Film o = (new Gson()).fromJson(result,Film.class);

	/*String[] splt = result.split("\\})(?=\\{)");//"[\\:,.]");

	for (String t : splt)
  		System.out.println(t);*/

	sc.close();
		
    }
}
