package integration;

/**
 * Hello world!
 *
 */
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class App 
{
    public static void main(String[] args) throws IOException, InterruptedException {
    
        System.out.println( "Hello World!" );
        //Thread.sleep(120000);
        /*HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http:localhost:30001/user"))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
        */
        
    }
}
