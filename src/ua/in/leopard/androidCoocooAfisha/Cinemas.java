package ua.in.leopard.androidCoocooAfisha;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Cinemas extends MainActivity implements OnItemClickListener {
	private CinemaAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.cinemas);
        
        String title = getString(R.string.cinemas_title) + " - " + EditPreferences.getCity(this);
        if (EditPreferences.isTheatersIsFilter(this)){
        	title = title + " (" + getString(R.string.theaters_is_filter_on) + ")";
        }
        setTitle(title);
        
        ListView CinemasList = (ListView)findViewById(R.id.cinemas_list);
        DatabaseHelper DatabaseHelperObject = new DatabaseHelper(this);
        List<CinemaDB> cinemas = DatabaseHelperObject.getTodayCinemas();
        adapter = new CinemaAdapter(this, cinemas);
        CinemasList.setAdapter(adapter);
        CinemasList.setOnItemClickListener(this);
        if (cinemas.size() == 0){
        	CinemasList.setVisibility(View.GONE);
        	TextView noDataText = (TextView)findViewById(R.id.no_data_title);
        	noDataText.setVisibility(View.VISIBLE);
        }
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		CinemaDB cinema_object = (CinemaDB)adapter.getItem(position);
		Intent intent = new Intent(this, Cinema.class);
		Bundle bundle = new Bundle();
		bundle.putInt("cinema_id", cinema_object.getId());
		intent.putExtras(bundle);
		startActivity(intent);
	}

}
