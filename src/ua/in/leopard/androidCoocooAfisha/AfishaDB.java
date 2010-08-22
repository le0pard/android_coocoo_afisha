package ua.in.leopard.androidCoocooAfisha;

public class AfishaDB {
	private int id;
	private int cinema_id;
	private int theater_id;
	private String zal_title;
	private String data_begin;
	private String data_end;
	private String times;
	private String prices;
	
	// Constructor for the TheaterDB class
	public AfishaDB(int id, int cinema_id, int theater_id, String zal_title, String data_begin, String data_end, String times, String prices) {
		super();
		this.id = id;
		this.cinema_id = cinema_id;
		this.theater_id = theater_id;
		this.zal_title = zal_title;
		this.data_begin = data_begin;
		this.data_end = data_end;
		this.times = times;
		this.prices = prices;
	}
	
	
	public Boolean equal(AfishaDB eq_object){
		if (
			(eq_object.getId() == this.getId()) && 
			(eq_object.getCinemaId() == this.getCinemaId()) && 
			(eq_object.getTheaterId() == this.getTheaterId()) && 
			(eq_object.getZalTitle() == this.getZalTitle()) && 
			(eq_object.getDataBegin() == this.getDataBegin()) && 
			(eq_object.getDataEnd() == this.getDataEnd()) && 
			(eq_object.getTimes() == this.getTimes()) && 
			(eq_object.getPrices() == this.getPrices())
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
	public int getCinemaId() {
		return cinema_id;
	}
	public void setCinemaId(int cinema_id) {
		this.cinema_id = cinema_id;
	}
	public int getTheaterId() {
		return theater_id;
	}
	public void setTheaterId(int theater_id) {
		this.theater_id = theater_id;
	}
	public String getZalTitle() {
		return zal_title;
	}
	public void setZalTitle(String zal_title) {
		this.zal_title = zal_title;
	}
	public String getDataBegin() {
		return data_begin;
	}
	public void setDataBegin(String data_begin) {
		this.data_begin = data_begin;
	}
	public String getDataEnd() {
		return data_end;
	}
	public void setDataEnd(String data_end) {
		this.data_end = data_end;
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
