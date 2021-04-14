package api.model;

public class Film {

    private String title;
    private String producer;

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
