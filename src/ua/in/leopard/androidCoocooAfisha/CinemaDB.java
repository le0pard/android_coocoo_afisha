package ua.in.leopard.androidCoocooAfisha;

import java.io.IOException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CinemaDB {
	private int id;
	private String title;
	private String orig_title;
	private String year;
	private String poster;
	private String description;
	
	private String zal_title = "";
	private String times = "";
	private String prices = "";
	
	
	// Constructor for the TheaterDB class
	public CinemaDB(int id, String title, String orig_title, String year, String poster, String description) {
		super();
		this.id = id;
		this.title = title;
		this.orig_title = orig_title;
		this.year = year;
		this.poster = poster;
		this.description = description;
	}
	
	
	public Boolean equal(CinemaDB eq_object){
		if (
			(eq_object.getId() == this.getId()) && 
			(eq_object.getTitle() == this.getTitle()) && 
			(eq_object.getOrigTitle() == this.getOrigTitle()) && 
			(eq_object.getYear() == this.getYear()) && 
			(eq_object.getPoster() == this.getPoster()) && 
			(eq_object.getDescription() == this.getDescription())
			){
			return true;
		} else {
			return false;
		}
		
	}
	
	// Getter and setter methods for all the fields.
	// Though you would not be using the setters for this example,
	// it might be useful later.
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOrigTitle() {
		return orig_title;
	}
	public void setOrigTitle(String orig_title) {
		this.orig_title = orig_title;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Bitmap getPosterImg(){
		Bitmap bitmap = null;
		if (this.getPoster() != ""){
			
			try{
				URL newurl = new URL("http://coocoorooza.com/uploads/afisha_films/" + this.getPoster()); 
				bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream()); 
			} catch (IOException e) {
				//
			} finally {
				
			}
		}
		return bitmap;
	}
	
	/* additional fields */
	public String getZalTitle() {
		return zal_title;
	}
	public void setZalTitle(String zal_title) {
		this.zal_title = zal_title;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getPrices() {
		return prices;
	}
	public void setPrices(String prices) {
		this.prices = prices;
	}
}
