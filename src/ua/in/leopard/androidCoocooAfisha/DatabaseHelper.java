package ua.in.leopard.androidCoocooAfisha;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME="coocoo_afisha_db";
	private final Context myContext;
	
	private static final String AFISHA_TABLE="afisha";
	private static final String AFISHA_TABLE_EXT_ID="id";
	private static final String AFISHA_TABLE_CINEMA_ID="cinema_id";
	private static final String AFISHA_TABLE_THEATER_ID="theater_id";
	private static final String AFISHA_TABLE_DATA_BEGIN="data_begin";
	private static final String AFISHA_TABLE_DATA_END="data_end";
	private static final String AFISHA_TABLE_TIMES="times";
	private static final String AFISHA_TABLE_PRICES="prices";
	
	private static final String CINEMAS_TABLE="cinemas";
	private static final String CINEMAS_TABLE_EXT_ID="id";
	private static final String CINEMAS_TABLE_TITLE="title";
	private static final String CINEMAS_TABLE_OR_TITLE="orig_title";
	private static final String CINEMAS_TABLE_YEAR="year";
	private static final String CINEMAS_TABLE_POSTER="poster";
	private static final String CINEMAS_TABLE_DESCRIPTION="description";

	private static final String THEATERS_TABLE="theaters";
	private static final String THEATERS_TABLE_EXT_ID="id";
	private static final String THEATERS_TABLE_CITY_ID="city_id";
	private static final String THEATERS_TABLE_TITLE="title";
	private static final String THEATERS_TABLE_LINK="link";
	private static final String THEATERS_TABLE_ADDRESS="address";
	private static final String THEATERS_TABLE_PHONE="phone";

	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.myContext = context;
	}
	
	public void setTheater(int id, int city_id, String title, String link, String address, String phone){
		ContentValues cv = new ContentValues();
		cv.put(THEATERS_TABLE_EXT_ID, id);
		cv.put(THEATERS_TABLE_CITY_ID, city_id);
		cv.put(THEATERS_TABLE_TITLE, title);
		cv.put(THEATERS_TABLE_LINK, link);
		cv.put(THEATERS_TABLE_ADDRESS, address);
		cv.put(THEATERS_TABLE_PHONE, phone);
		this.getWritableDatabase().insert(THEATERS_TABLE, THEATERS_TABLE_EXT_ID, cv);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + AFISHA_TABLE + 
				" (" + 
				AFISHA_TABLE_EXT_ID + " INTEGER PRIMARY KEY, " + 
				AFISHA_TABLE_CINEMA_ID + " INTEGER, " + 
				AFISHA_TABLE_THEATER_ID + " INTEGER, " + 
				AFISHA_TABLE_DATA_BEGIN + " DATE, " + 
				AFISHA_TABLE_DATA_END + " DATE, " + 
				AFISHA_TABLE_TIMES + " TEXT, " + 
				AFISHA_TABLE_PRICES + " TEXT" + 
				");");
		
		db.execSQL("CREATE TABLE " + CINEMAS_TABLE + 
				" (" + 
				CINEMAS_TABLE_EXT_ID + " INTEGER PRIMARY KEY, " + 
				CINEMAS_TABLE_TITLE + " TEXT, " + 
				CINEMAS_TABLE_OR_TITLE + " TEXT, " + 
				CINEMAS_TABLE_YEAR + " INTEGER, " + 
				CINEMAS_TABLE_POSTER + " TEXT, " + 
				CINEMAS_TABLE_DESCRIPTION + " TEXT" + 
				");");
		
		db.execSQL("CREATE TABLE " + THEATERS_TABLE + 
				" (" + 
				THEATERS_TABLE_EXT_ID + " INTEGER PRIMARY KEY, " + 
				THEATERS_TABLE_CITY_ID + " INTEGER, " + 
				THEATERS_TABLE_TITLE + " TEXT, " + 
				THEATERS_TABLE_LINK + " TEXT, " + 
				THEATERS_TABLE_ADDRESS + " TEXT, " + 
				THEATERS_TABLE_PHONE + " TEXT" + 
				");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		android.util.Log.w("DatabaseHelper", "Upgrading database, which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + AFISHA_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + CINEMAS_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + THEATERS_TABLE);
		onCreate(db);
	}
	
}
