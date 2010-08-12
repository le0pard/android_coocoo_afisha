package ua.in.leopard.androidCoocooAfisha;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class EditPreferences extends PreferenceActivity {
   // Option names and default values
   private static final String OPT_CITY = "cities";
   private static final String OPT_CITY_DEF = "1";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.settings);
   }
   
   /** Get the current value of the cities option */
   public static String getCity(Context context) {
      return PreferenceManager.getDefaultSharedPreferences(context).getString(OPT_CITY, OPT_CITY_DEF);
   }
   
}
