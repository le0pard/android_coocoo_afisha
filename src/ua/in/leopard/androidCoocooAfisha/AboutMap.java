package ua.in.leopard.androidCoocooAfisha;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class AboutMap extends Activity {
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.about_map);
      
      TextView about_text = (TextView)findViewById(R.id.about_map_content);
      about_text.setText(Html.fromHtml(getString(R.string.about_map_text)));
   }
}