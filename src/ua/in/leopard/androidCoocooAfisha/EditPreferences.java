package ua.in.leopard.androidCoocooAfisha;

import android.content.Context;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class EditPreferences extends PreferenceActivity {
   // Option names and default values
   private static final String OPT_CITY_ID = "city_id";
   private static final String OPT_CITY_ID_DEF = "1";
   private ListPreference cities_lp;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.settings);
      cities_lp = (ListPreference)findPreference(OPT_CITY_ID);
      
   }
   
   public static String getCity(Context context) {
      return "";
   }
   
   /** Get the current value of the cities option */
   public static String getCityId(Context context) {
      return PreferenceManager.getDefaultSharedPreferences(context).getString(OPT_CITY_ID, OPT_CITY_ID_DEF);
   }
   
}
