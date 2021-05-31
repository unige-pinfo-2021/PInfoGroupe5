package selector.model;

import java.io.IOException;
import org.json.*;
import java.net.*;
import java.io.*;

public class selector {

    public selector()throws IOException, InterruptedException
    {
        
        try(FileWriter myWriter = new FileWriter("/etc/hosts");) {
        	myWriter.write("\n 129.194.10.130 tindfilm");
        } 
		
    }

	// on sépare l'appelle à la requette http de recommendation de l'algorithme afin de pour voir faire des testes unitaires 
	// sur l'algorithme

	public int[][] recomendation(int filmID[])throws IOException, InterruptedException
	{
		int size = filmID.length;
		// les recommendations pour chaque filmID
		JSONArray recommendations[] = new JSONArray[size];
		for(int i=0; i<size; i++)
		{
			// requet http à film
			String adresse = "http://tindfilm/film/recommendation/" + Integer.toString(filmID[i]);
			recommendations[i] = new JSONArray(get(adresse));
		}
		// on récupère le nombre de film pour chaque recommendation
		int sizeRecom = recommendations[0].length();

		// on initialise le tableau retourné
		int retour[][] = new int[size][sizeRecom];

		// affect le filmID à la valeur de retour
		for(int i=0;i<size;i++)
		{
			for(int j = 0;j<sizeRecom;j++)
			{
				JSONObject film = recommendations[i].getJSONObject(j);
				retour[i][j] = film.getInt("id");
			}
		}
		return retour;
	}

    public int[] algorithme(int filmID [][])
    {
		int size = filmID.length;
	
		// On regarde les réccurences de filmID dans chaque recommendation
		// on parcours toutes les listes et ajout dans "reccurence"
		// le map est indêxé par l'id du film et contient comme valeur le nombre de reccurrence
		Map<Integer,Integer> reccurence = new HashMap<Integer,Integer>();
		for(int i=0;i<size;i++)
		{
			int sizeRecom = filmID[i].length;
			for(int j=0;j<sizeRecom;j++)
			{
				int id = filmID[i][j];
				// on vérifie si l'id est déjà apparu
				if(reccurence.containsKey(id))
				{
					int ancienneValeur = reccurence.get(id);
					reccurence.put(id,ancienneValeur + 1);
				}
				else
				{
					reccurence.put(id,1);
				}
			}
		}

		// on classe les films selon leur réccurrence
		int nombreFilm = 40;
		int classement[] = new int[nombreFilm];
		int indexClassement = 0;
		// pour chaque niveau de reccurrence, on parcours le Map
		// et on prend en compte les films qui ont le niveau de réccurrence requis
		// on renvoie 40 films.
		for(int i = size; i>0 ; i--)// loop sur les niveaux de réccurrence
		{
			for(int key : reccurence.keySet())// loop sur le map
			{
				if(i == reccurence.get(key))// si bon nombre de reccurrence
				{
					classement[indexClassement] = key;
					nombreFilm --;	
					indexClassement ++;
				}
				if(nombreFilm <= 0)// si on atteint le nombre de film max, on retourne le tableau
				{
					return classement;
				}
			}
		}
		return classement; // si il n'y a pas assez de film pour retourner 40 film
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
