package ua.in.leopard.androidCoocooAfisha;

import java.util.List;

import ua.in.leopard.androidCoocooAfisha.helper.ImageDownloader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SeanceAdapter extends BaseAdapter {
	
	private Context context;
	private List<CinemaDB> cinemas_list;
	private final ImageDownloader imageDownloader = new ImageDownloader();
	
	public SeanceAdapter(Context context, List<CinemaDB> cinemas_list){
		this.context = context;
		this.cinemas_list = cinemas_list;
	}
	
	@Override
	public int getCount() {
		return cinemas_list.size();
	}

	@Override
	public Object getItem(int position) {
		return cinemas_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CinemaDB entry = cinemas_list.get(position);
		return new SeanceAdapterView(context, entry, imageDownloader);
	}

}
