package ua.in.leopard.androidCoocooAfisha;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

public class FilterTheaterAdapter extends BaseAdapter {
	
	private Context context;
	private List<TheaterDB> theaters_list;
	
	public FilterTheaterAdapter(Context context, List<TheaterDB> theaters_list){
		this.context = context;
		this.theaters_list = theaters_list;
	}
	
	@Override
	public int getCount() {
		return theaters_list.size();
	}

	@Override
	public Object getItem(int position) {
		return theaters_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TheaterDB entry = theaters_list.get(position);
		
        View row = ((Activity) context).getLayoutInflater().inflate(R.xml.theaters_filter_row, null);
        row.setTag(R.id.text1, row.findViewById(R.id.text1));
        CheckedTextView ctv =(CheckedTextView)row.getTag(R.id.text1);
        ctv.setText(entry.getTitle());
        return row;
		//return new FilterTheaterAdapterView(context, entry);
	}

}
