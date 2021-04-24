package integration;

import javax.json;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class App 
{
    public static void main(String[] args) throws IOException, InterruptedException {
    
        System.out.println( "Hello World!" );
        //Thread.sleep(120000);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://127.0.0.1:30001/user"))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
        
        
    
        var values = new HashMap<String, String>() {{
            put("username", "khptif");
            put ("email", "email33");
            put ("groupList","Group");
        }};

        
        String requestBody = "{\"username\":\"khptif\",\"email\":\"email23\",\"groupList\":[\"Group\",\"all\"]}";
                
	JsonObject value = Json.createObjectBuilder()
	.add("username", "John")
     	.add("email", "Smith")
     	.add("groupList", "all")
     	.build();
     	requestBody = value.toString();
     	print(requestBody);
        HttpClient clientPost = HttpClient.newHttpClient();
        HttpRequest requestPost = HttpRequest.newBuilder()
                .uri(URI.create("http://127.0.0.1:30001/user"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> responsePost = clientPost.send(requestPost,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(responsePost.body());
    }
}

