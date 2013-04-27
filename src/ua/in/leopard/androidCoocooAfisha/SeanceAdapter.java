package ua.in.leopard.androidCoocooAfisha;

import java.util.List;

import ua.in.leopard.androidCoocooAfisha.helper.ImageDownloader;
import ua.in.leopard.androidCoocooAfisha.helper.PosterSetuper;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SeanceAdapter extends BaseAdapter {
	
	private Context context;
	private List<CinemaDB> cinemas_list;
	private final ImageDownloader imageDownloader;
	private final PosterSetuper posterSetuper;
	private SparseArray<SeanceAdapterView> listSeanceAdapterView = new SparseArray<SeanceAdapterView>();
	
	
	public SeanceAdapter(Context context, List<CinemaDB> cinemas_list){
		this.context = context;
		this.cinemas_list = cinemas_list;
		this.imageDownloader = new ImageDownloader(this.context);
		this.posterSetuper = new PosterSetuper(this.context);
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
		if (null == listSeanceAdapterView.get(position)){
			listSeanceAdapterView.put(position, new SeanceAdapterView(context, entry, imageDownloader, posterSetuper));
		}
		return listSeanceAdapterView.get(position);
	}

}
