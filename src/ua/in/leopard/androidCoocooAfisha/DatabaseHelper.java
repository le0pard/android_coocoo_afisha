package ua.in.leopard.androidCoocooAfisha;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * ./adb -s emulator-5554 shell
 * sqlite3 /data/data/ua.in.leopard.androidCoocooAfisha/databases/coocoo_afisha_db
*/
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
	
	public List<TheaterDB> getTheaters(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor result = db.query(THEATERS_TABLE, 
				new String[] {
				THEATERS_TABLE_EXT_ID, 
				THEATERS_TABLE_CITY_ID, 
				THEATERS_TABLE_TITLE,
				THEATERS_TABLE_LINK,
				THEATERS_TABLE_ADDRESS,
				THEATERS_TABLE_PHONE
				}, "city_id = " + EditPreferences.getCityId(this.myContext),
				null, null, null, THEATERS_TABLE_TITLE);
		result.moveToFirst();
		List<TheaterDB> theaters = new ArrayList<TheaterDB>();
		while (!result.isAfterLast()) {
			theaters.add(new TheaterDB(
				result.getInt(result.getColumnIndex("id")),
				result.getInt(result.getColumnIndex("city_id")),
				result.getString(result.getColumnIndex("title")),
				result.getString(result.getColumnIndex("link")),
				result.getString(result.getColumnIndex("address")),
				result.getString(result.getColumnIndex("phone"))
			));
			result.moveToNext();
		}
		result.close();
		db.close();
		return theaters;
	}
	
	public TheaterDB getTheater(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor result = db.query(THEATERS_TABLE, 
				new String[] {
				THEATERS_TABLE_EXT_ID, 
				THEATERS_TABLE_CITY_ID, 
				THEATERS_TABLE_TITLE,
				THEATERS_TABLE_LINK,
				THEATERS_TABLE_ADDRESS,
				THEATERS_TABLE_PHONE
				}, "id = ?",
				new String[] {Integer.toString(id)}, null, null, THEATERS_TABLE_TITLE, "1");
		
		result.moveToFirst();
		TheaterDB theater_row = null;
		while (!result.isAfterLast()) {
			theater_row = new TheaterDB(
				result.getInt(result.getColumnIndex("id")),
				result.getInt(result.getColumnIndex("city_id")),
				result.getString(result.getColumnIndex("title")),
				result.getString(result.getColumnIndex("link")),
				result.getString(result.getColumnIndex("address")),
				result.getString(result.getColumnIndex("phone"))
			);
			result.moveToNext();
		}
		result.close();
		db.close();
		return theater_row;
	}

	
	public void setTheater(TheaterDB theater_row){
		TheaterDB tmp_obj = this.getTheater(theater_row.getId());
		SQLiteDatabase db = this.getWritableDatabase();
		if (tmp_obj == null){
			ContentValues cv = new ContentValues();
			cv.put(THEATERS_TABLE_EXT_ID, theater_row.getId());
			cv.put(THEATERS_TABLE_CITY_ID, theater_row.getCityId());
			cv.put(THEATERS_TABLE_TITLE, theater_row.getTitle());
			cv.put(THEATERS_TABLE_LINK, theater_row.getLink());
			cv.put(THEATERS_TABLE_ADDRESS, theater_row.getAddress());
			cv.put(THEATERS_TABLE_PHONE, theater_row.getPhone());
			db.insert(THEATERS_TABLE, null, cv);
		} else if (!tmp_obj.equal(theater_row)){
			ContentValues cv = new ContentValues();
			cv.put(THEATERS_TABLE_EXT_ID, theater_row.getId());
			cv.put(THEATERS_TABLE_CITY_ID, theater_row.getCityId());
			cv.put(THEATERS_TABLE_TITLE, theater_row.getTitle());
			cv.put(THEATERS_TABLE_LINK, theater_row.getLink());
			cv.put(THEATERS_TABLE_ADDRESS, theater_row.getAddress());
			cv.put(THEATERS_TABLE_PHONE, theater_row.getPhone());
			db.update(THEATERS_TABLE, cv, "id = ?", new String[] {Integer.toString(theater_row.getId())});
		}
		db.close();
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
