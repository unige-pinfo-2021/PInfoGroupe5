package api.model;

import java.util.*;

public class Film {

    private String title;
    private String year;
    private String rated;
    private String released;
    private String runtime;
    private String genre; // ou String[]
    private String director;
    private String writer;
    private String actors; // ou String[]
    private String plot;
    private String language;
    private String Country;
    private String awards;
    private String poster;
    private String ratings;
    private String metascore;
    private String imdbrating;
    private String imdbvotes;
    private String imdbId;
    private String type;
    //private String totalseasons;


    public Film(ArrayList<String> inflist) {
            this.title=inflist.get(inflist.indexOf("Title")+2);
	    this.year=inflist.get(inflist.indexOf("Year")+2);
	    this.rated=inflist.get(inflist.indexOf("Rated")+2);
	    this.released=inflist.get(inflist.indexOf("Released")+2);
	    this.runtime=inflist.get(inflist.indexOf("Runtime")+2);
	    this.genre=inflist.get(inflist.indexOf("Genre")+2); // ou String[]
	    this.director=inflist.get(inflist.indexOf("Director")+2);
	    this.writer=inflist.get(inflist.indexOf("Writer")+2);
	    this.actors=inflist.get(inflist.indexOf("Actors")+2); // ou String[]
	    this.plot=inflist.get(inflist.indexOf("Plot")+2);
	    this.language=inflist.get(inflist.indexOf("Language")+2);
	    this.Country=inflist.get(inflist.indexOf("Country")+2);
	    this.awards=inflist.get(inflist.indexOf("Awards")+2);
	    this.poster=inflist.get(inflist.indexOf("Poster")+2);
	    this.ratings=inflist.get(inflist.indexOf("Value")+2); // !!! A CHANGER
	    this.metascore=inflist.get(inflist.indexOf("Metascore")+2);
	    this.imdbrating=inflist.get(inflist.indexOf("imdbRating")+2);
	    this.imdbvotes=inflist.get(inflist.indexOf("imdbVotes")+2);
	    this.imdbId=inflist.get(inflist.indexOf("imdbID")+2);
	    this.type=inflist.get(inflist.indexOf("Type")+2);
          //private String totalseasons;
    }

   public String getTitle(){
	return this.title;
   }

   public String getYear(){
	return this.year;
   }
   
   public String getRated(){
	return this.rated;
   }

   public String getReleased(){
	return this.released;
   }

   public String getRuntime(){
	return this.runtime;
   }

   public String getGenre(){// ou String[]
	return this.genre;
   }
  
   public String getDirector(){
	return this.director;
   }
 
   public String getWriter(){
	return this.writer;
   }
    
   public String getActors(){// ou String[]
	return this.actors;
   }

   public String getPlot(){
	return this.plot;
   }
   
   public String getLanguage(){
	return this.language;
   }
 
   public String getCountry(){
	return this.Country;
   }
    
   public String getAwards(){
	return this.awards;
   }
   
   public String getPoster(){
	return this.poster;
   }

   public String getRatings(){
	return this.ratings;
   }
  
   public String getMetascore(){
	return this.metascore;
   }
 
   public String getImdbrating(){
	return this.imdbrating;
   }
   
   public String getImdbvotes(){
	return this.imdbvotes;
   }
 
   public String getImdbId(){
	return this.imdbId;
   }
  
   public String getType(){
	return this.type;
   }
    //private String totalseasons;
  
   public String descrpt(){
     return this.title +"\n"+this.year+"\n"+this.rated+"\n"+this.released+"\n"+this.runtime+"\n"+this.genre+"\n" +this.director+"\n"+this.writer+"\n"+this.actors+"\n" +this.plot+"\n"+this.language+"\n"+this.Country+"\n"+this.awards+"\n"+this.poster+"\n"+this.ratings+"\n"+this.metascore+"\n"+this.imdbrating+"\n"+this.imdbvotes+"\n"+this.imdbId+"\n"+this.type;
   }


    public boolean equals(Object obj){
   		boolean flag = false;
   		if (obj instanceof Film){
   			flag = (this.title).equals(((Film) obj).getTitle()); /*&& (this.producer).equals(((Film) obj).getProducer());*/
   		}
   		return flag;
	}
}//end class
