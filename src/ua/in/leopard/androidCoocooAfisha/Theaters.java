package ua.in.leopard.androidCoocooAfisha;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Theaters extends MainActivity implements OnItemClickListener {
	private TheaterAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.theaters);
        
        String title = getString(R.string.theaters_title) + " - " + EditPreferences.getCity(this);
        if (EditPreferences.isTheatersIsFilter(this)){
        	title = title + " (" + getString(R.string.theaters_is_filter_on) + ")";
        }
        TextView main_title = (TextView)findViewById(R.id.main_title);
        main_title.setText(title);
        
        ListView TheaterList = (ListView)findViewById(R.id.theaters_list);
        DatabaseHelper DatabaseHelperObject = new DatabaseHelper(this);
        List<TheaterDB> theaters = DatabaseHelperObject.getTheaters(false);
        adapter = new TheaterAdapter(this, theaters);
        TheaterList.setAdapter(adapter);
        TheaterList.setOnItemClickListener(this);
        if (theaters.size() == 0){
        	TheaterList.setVisibility(View.GONE);
        	TextView noDataText = (TextView)findViewById(R.id.no_data_title);
        	noDataText.setVisibility(View.VISIBLE);
        	noDataText.setText(R.string.no_data_maybe_update);
        }
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		TheaterDB theater_object = (TheaterDB)adapter.getItem(position);
		Intent intent = new Intent(this, Theater.class);
		Bundle bundle = new Bundle();
		bundle.putInt("theater_id", theater_object.getId());
		intent.putExtras(bundle);
		startActivity(intent);
	}

	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
      super.onCreateOptionsMenu(menu);
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.theaters_menu, menu);
      return true;
    }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
        case R.id.cinemas_list:
          startActivity(new Intent(this, Cinemas.class));
          break;
        case R.id.settings_key:
         startActivity(new Intent(this, EditPreferences.class));
         break;
      }
      return false;
   }

}
