package com.seriesmanager.adapters;

import java.util.List;

import com.seriesmanager.business.Serie;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**Class to manipulate Series inside ListViews*/
public class SerieAdapter extends BaseAdapter{
		private Context context;
		private List<Serie> series;

		public SerieAdapter(Context context, List<Serie> series) {
			super();
			this.context = context;
			this.series = series;
		}

		@Override
		public int getCount() {

			return series.size();
		}

		@Override
		public Object getItem(int position) {

			return series.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int posicao, View convertView, ViewGroup parent) {
			return new SerieAdapterView(context, series, posicao, this);
		}

	
}
