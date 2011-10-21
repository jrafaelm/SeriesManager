package com.seriesmanager.adapters;

import java.util.List;

import com.seriesmanager.R;
import com.seriesmanager.business.Serie;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
		public View getView(int position, View convertView, ViewGroup parent) {
			 ViewHolder holder;  
			  
			   Serie s = series.get(position);  
			  
			   if (convertView == null){  
			      convertView = LayoutInflater.from(context).inflate(  
			         R.layout.serie_cell, null);  
			   
			      holder = new ViewHolder();  
			      holder.txtTitle = (TextView)   
			         convertView.findViewById(R.id.tvSerieTitle);  
			      holder.txtDetail = (TextView)   
			         convertView.findViewById(R.id.tvSerieDetail);  
			      convertView.setTag(holder);  
			   } else {  
			      holder = (ViewHolder)convertView.getTag();  
			   }  
			   holder.txtTitle.setText(s.getName());  
			   holder.txtDetail.setText(s.getSeason().toString() + " X "+ s.getEpisode());  
			  
			   return convertView;  
		}  
			  
			static class ViewHolder {  
			   TextView txtTitle;  
			   TextView txtDetail;  
			}  
			

	
}
