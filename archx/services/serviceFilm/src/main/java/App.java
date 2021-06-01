import api.film.*;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	new Film();
    	FilmService v = new FilmService();
    	new Rest_Caller();
        System.out.println( "Hello World!" );
        v.run();
    }
}
