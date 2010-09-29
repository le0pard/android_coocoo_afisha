package ua.in.leopard.androidCoocooAfisha;


public class TheaterDB {
	private int id;
	private int city_id;
	private String title;
	private String link;
	private String address;
	private String phone;
	private String latitude;
	private String longitude;
	private String call_phone;
	private Integer is_filter;
	
	// Constructor for the TheaterDB class
	public TheaterDB(int id, int city_id, String title, String link, String address, String phone, String latitude, String longitude, String call_phone, Integer is_filter) {
		super();
		this.id = id;
		this.city_id = city_id;
		this.title = title;
		this.link = link;
		this.address = address;
		this.phone = phone;
		this.latitude = latitude;
		this.longitude = longitude;
		this.call_phone = call_phone;
		this.is_filter = is_filter;
	}
	
	
	public Boolean equal(TheaterDB eq_object){
		if (
			(eq_object.getId() == this.getId()) && 
			(eq_object.getCityId() == this.getCityId()) && 
			(eq_object.getTitle() == this.getTitle()) && 
			(eq_object.getLink() == this.getLink()) && 
			(eq_object.getAddress() == this.getAddress()) && 
			(eq_object.getPhone() == this.getPhone())
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
	public int getCityId() {
		return city_id;
	}
	public void setCityId(int city_id) {
		this.city_id = city_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getCallPhone() {
		return call_phone;
	}
	public void setCallPhone(String call_phone) {
		this.call_phone = call_phone;
	}
	
	public Boolean isFiltered(){
		if (1 == this.is_filter){
			return true;
		} else {
			return false;
		}
	}
}
