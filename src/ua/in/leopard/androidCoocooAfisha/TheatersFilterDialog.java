package ua.in.leopard.androidCoocooAfisha;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TheatersFilterDialog extends Activity implements OnClickListener, OnItemClickListener {
	private List<TheaterDB> theaters_list;
	private ListView TheaterList;
	private FilterTheaterAdapter adapter;
	private DatabaseHelper DatabaseHelperObject;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.xml.theaters_filter);
        
        setTitle(getString(R.string.theaters_is_filter_title));
        
        TheaterList = (ListView)findViewById(R.id.theaters_list);
        TheaterList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        DatabaseHelperObject = new DatabaseHelper(this);
        theaters_list = DatabaseHelperObject.getTheaters(true);
        adapter = new FilterTheaterAdapter(this, theaters_list);
        TheaterList.setAdapter(adapter);
        TheaterList.setOnItemClickListener(this);
        
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		if (TheaterList.isItemChecked(position)){
			DatabaseHelperObject.addFilterTheater(theaters_list.get(position));
		} else {
			DatabaseHelperObject.removeFilterTheater(theaters_list.get(position));
		}
				
	}
	
}
