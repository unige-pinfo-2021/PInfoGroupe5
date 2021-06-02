import api.user.*;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    	UserService v = new UserService ();
        System.out.println( "Hello World!" );
        v.run();
    }
}
