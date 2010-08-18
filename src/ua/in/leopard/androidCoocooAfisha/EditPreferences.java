package ua.in.leopard.androidCoocooAfisha;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class EditPreferences extends PreferenceActivity implements OnSharedPreferenceChangeListener {
   // Option names and default values
   private static final String OPT_CITY = "city_current_name";
   private static final String OPT_CITY_DEF = "Киев";
   private static final String OPT_CITY_ID = "city_id";
   private static final String OPT_CITY_ID_DEF = "1";
   
   private static final String SECRET_TOKEN = "sajdYGYgsdmKILIasdasdouher387hgdf";
   private static final String THEATERS_URL_KEY = "theaters_url";
   private static final String THEATERS_URL = "http://coocoorooza.com/api/afisha_theaters/:city_id/:token.json";
   
   
   private ListPreference cities_lp;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.settings);
      
      cities_lp = (ListPreference)findPreference(OPT_CITY_ID);
      
      setDefUrls(getPreferenceScreen().getSharedPreferences());
   }
   
   @Override
   protected void onResume() {
       super.onResume();
       // Set up a listener whenever a key changes            
       getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
   }

   @Override
   protected void onPause() {
       super.onPause();
       // Unregister the listener whenever a key changes            
       getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);    
   }

   
   public static String getCity(Context context) {
	   return PreferenceManager.getDefaultSharedPreferences(context).getString(OPT_CITY, OPT_CITY_DEF);
   }
   
   /** Get the current value of the cities option */
   public static String getCityId(Context context) {
      return PreferenceManager.getDefaultSharedPreferences(context).getString(OPT_CITY_ID, OPT_CITY_ID_DEF);
   }
   
   public static String getTheaterUrl(Context context) {
	   return PreferenceManager.getDefaultSharedPreferences(context).getString(THEATERS_URL_KEY, "");
   }
   
   public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
	   if (key.equals(OPT_CITY_ID)) {
		   SharedPreferences.Editor editor = sharedPreferences.edit();
		   
		   editor.putString(OPT_CITY, cities_lp.getEntry().toString());
		   editor.commit();
		   
		   setDefUrls(sharedPreferences);
		   
	   }
   }
   
   protected void setDefUrls(SharedPreferences sharedPreferences){
	   SharedPreferences.Editor editor = sharedPreferences.edit();
	   String theaters_url = THEATERS_URL;
	   theaters_url = theaters_url.replace(":city_id", cities_lp.getValue());
	   theaters_url = theaters_url.replace(":token", SECRET_TOKEN);
	   editor.putString(THEATERS_URL_KEY, theaters_url);
	   editor.commit();
   }
   
}
