package selector.model;

import java.io.IOException;
import org.json.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class selector {

    public selector()throws IOException, InterruptedException
    {
        
        try(FileWriter myWriter = new FileWriter("/etc/hosts");) {
        	myWriter.write("\n 129.194.10.130 tindfilm");
        } 
		
    }

	public int[] recommendations (Integer[] filmID)
	{
		int[][] recomm = new int[5][20];

		String url = "http://tindfilm/film/recommande/";
		for(int i = 0; i <5; i++)
		{
			String r = this.get(url + Integer.toString(filmID[i]));
			JSONArray rec = new JSONArray(r);
			for(int j=0; j<rec.length();j++)
			{
				JSONObject film = rec.getJSONObject(i);
				recomm[i][j] = film.getInt("id");
			}
		}

		return algorithme(recomm);
	}

    public int[] algorithme(int[][] recommendation)throws IOException, InterruptedException
    {
		int[] retour = new int[40];
		// on compte les réccurences des films [idFilm] = nombre de réccurrences
		Map<Integer,Integer> reccurrence = new HashMap<Integer,Integer>(); 

		for(int[] liste : recommendation)
		{
			for (int id : liste) 
			{
				if(reccurrence.containsKey(id))// vérifie si le film déjà rencontré
				{
					int nouvelleValeure = reccurrence.get(id) + 1;
					reccurrence.put(id,nouvelleValeure);
				}
				else
				{
					reccurrence.put(id,1);
				}
			}
		}

		int indexRetour = 0; // permet d'indexer le tableau de retour
		// on parcours plusieurs fois le tableau des réccurrences pour y ajouter des films.
		// on commence avec les plus réccurrents

		for(int r = 5; r >0; r --)
		{
			for(int idFilm : reccurrence.keySet())// on parcours le tableau associatif
			{
				if(reccurrence.get(idFilm) == r)
				{
					retour[indexRetour] = idFilm;
					indexRetour ++;
					if(indexRetour < 40)
					{
						return retour; // le tableau est rempli avec assez de film
					}
				}
			}
		}
		return retour;
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
