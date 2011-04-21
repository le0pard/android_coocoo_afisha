package ua.in.leopard.androidCoocooAfisha;

public class SearchResDB {
	private int cinema_id;
	private String title;
	private String orig_title;
	
	// Constructor for the TheaterDB class
	public SearchResDB(int cinema_id, String title, String orig_title) {
		super();
		this.cinema_id = cinema_id;
		this.title = title;
		this.orig_title = orig_title;
	}
	
	public int getCinemaId(){
		return this.cinema_id;
	}
	
	public void setCinemaId(int cinema_id){
		this.cinema_id = cinema_id;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getOrigTitle(){
		return this.orig_title;
	}
	
	public void setOrigTitle(String orig_title){
		this.orig_title = orig_title;
	}

}
