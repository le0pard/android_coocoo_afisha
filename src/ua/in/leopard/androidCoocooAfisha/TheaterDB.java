package ua.in.leopard.androidCoocooAfisha;

public class TheaterDB {
	private int id;
	private int city_id;
	private String title;
	private String link;
	private String address;
	private String phone;
	
	// Constructor for the TheaterDB class
	public TheaterDB(int id, int city_id, String title, String link, String address, String phone) {
		super();
		this.id = id;
		this.city_id = city_id;
		this.title = title;
		this.link = link;
		this.address = address;
		this.phone = phone;
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
}
