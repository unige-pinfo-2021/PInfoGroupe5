package selector.model;

import java.util.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import org.json.*;
import java.net.*;
import java.io.*;

public class selector {

    public selector()throws IOException, InterruptedException
    {
        FileWriter myWriter = new FileWriter("/etc/hosts");
        try {myWriter.write("\n 129.194.10.130 tindfilm");
        } finally {
        	myWriter.close();
        }
		
    }

    public String algorithme(String critereJson)throws IOException, InterruptedException
    {
        JSONObject critere = new JSONObject(critereJson);

        String catalogue = get("tindfilm/film");
        return catalogue;

    }

    private String get(String adresse)throws IOException, InterruptedException 
    {
    	URL obj = new URL(adresse);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			return response.toString();
		} else {
			return "GET request not worked";
		}
       
    }
}
