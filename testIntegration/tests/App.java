

/**
 * Hello world!
 *
 */

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class App 
{
    public static void main(String[] args) throws IOException, InterruptedException {
    
        get("http://127.0.0.1:30001/user");
        get("http://127.0.0.1:30001/user/name=user0");
  
        String requestBody = "{\"username\":\"khptif\",\"email\":\"email23\",\"groupList\":[\"Group\",\"all\"]}";
                
        post("http://127.0.0.1:30001/user",requestBody);
        
        String deleteRequete = "{\"username\":\"user0\",\"email\":\"email0\",\"groupList\":[\"Group\",\"all\"]}";
    
    delete("http://127.0.0.1:30001/user",deleteRequete);
    
    }
    	
    private static String get(String adresse)throws IOException, InterruptedException 
    {
    	System.out.println(" GET " + adresse);
    	HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(adresse))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body() + '\n');
        return response.body();
    }
    
    private static String post(String adresse, String requete)throws IOException, InterruptedException 
    {
    	System.out.println(" POST " + adresse);
    	HttpClient clientPost = HttpClient.newHttpClient();
        HttpRequest requestPost = HttpRequest.newBuilder()
                .uri(URI.create(adresse))
                .POST(HttpRequest.BodyPublishers.ofString(requete))
                .build();

        HttpResponse<String> responsePost = clientPost.send(requestPost,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(responsePost.body() + '\n');
        
         return responsePost.body();
    }
    
     private static String delete(String adresse, String requete)throws IOException, InterruptedException 
    {
    	System.out.println(" DELETE " + adresse);
    	HttpClient clientPost = HttpClient.newHttpClient();
        HttpRequest requestPost = HttpRequest.newBuilder()
                .uri(URI.create(adresse))
                .DELETE(HttpRequest.BodyPublishers.ofString(requete))
                .build();

        HttpResponse<String> responsePost = clientPost.send(requestPost,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(responsePost.body() + '\n');
        
         return responsePost.body();
    }
    
}


