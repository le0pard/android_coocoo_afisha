package ua.in.leopard.androidCoocooAfisha;

import java.util.List;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchableActivity extends MainActivity implements OnItemClickListener {
	private SearchAdapter search_adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);
        
        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
          String query = intent.getStringExtra(SearchManager.QUERY);
          setTitle(getString(R.string.search_result_label) + " " + query);
          
          ListView searchList = (ListView)findViewById(R.id.search_list);
          DatabaseHelper DatabaseHelperObject = new DatabaseHelper(this);
          List<SearchResDB> search_results = DatabaseHelperObject.searchCinemas(query);
          search_adapter = new SearchAdapter(this, search_results);
          searchList.setAdapter(search_adapter);
          searchList.setOnItemClickListener(this); 
        }
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		SearchResDB search_object = (SearchResDB)search_adapter.getItem(position);
		Intent intent = new Intent(this, Cinema.class);
		Bundle bundle = new Bundle();
		bundle.putInt("cinema_id", search_object.getCinemaId());
		intent.putExtras(bundle);
		startActivity(intent);
	}

}