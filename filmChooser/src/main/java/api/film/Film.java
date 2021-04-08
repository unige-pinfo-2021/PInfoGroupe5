package api.film;

public class Film {

    private String title;
    private String producer;
    /*private String year;
	private String rated;
	private String released;
	private String runtime;
	private String genre;
	private String director;
	private String writer;
	private String actors;
	private String plot;
	private String language;
	private String country;
	private String awards;
	private String poster;
	private String[] ratings;
//"Ratings":[{"Source":"Internet Movie Database","Value":"8.1/10"}],
	private String metascore;
	private String imdbRating;
	private String imdbVotes;
	private String imdbID;
	private String type;
	private String totalSeasons;
	private String response;*/

    public Film() {
	this.title = "Default";
        this.producer = "Unknown";
    }

    public Film(String title, String producer) {
        this.title = title;
        this.producer = producer;
    }

   public String getTitle(){
	return this.title;
   }

   public String getProducer(){
	return this.producer;
   }

    public void setTitle(String title){
	this.title = title;
   }

    public void setProducer(String producer){
	this.producer = producer;
   }

   public boolean equals(Object obj){
   		boolean flag = false;
   		if (obj instanceof Film){
   			flag = (this.title).equals(((Film) obj).getTitle()) && (this.producer).equals(((Film) obj).getProducer());
   		}
   		return flag;
	}

}
