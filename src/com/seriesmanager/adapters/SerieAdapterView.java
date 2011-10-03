package com.seriesmanager.adapters;

import java.util.List;

import com.seriesmanager.business.Serie;



import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * class to build a line of the Serie's ListView 
 * */
public class SerieAdapterView extends LinearLayout{
	
	public SerieAdapterView(final Context context,
			final List<Serie> series, final int position,
			final SerieAdapter adp) {
		super(context);
		Serie serie = series.get(position);

		// build the line

		LinearLayout layout = new LinearLayout(context);
		LinearLayout.LayoutParams generalParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		TextView tvName = new TextView(context);
		tvName.setText(serie.getName());
		tvName.setTextColor(Color.LTGRAY);
		tvName.setTextSize(18);
		tvName.setShadowLayer(3, 2, 2, Color.GRAY);
		LinearLayout.LayoutParams nameParam = new LinearLayout.LayoutParams(250, 50);
		
		tvName.setPadding(17, 12, 5, 0);

		TextView tvSeasonxEpisode = new TextView(context);
		tvSeasonxEpisode.setText(serie.getSeason()+"x"+serie.getEpisode());
		tvSeasonxEpisode.setTextColor(Color.LTGRAY);
		tvSeasonxEpisode.setTextSize(18);
		tvSeasonxEpisode.setShadowLayer(3, 2, 2, Color.GRAY);
		LinearLayout.LayoutParams sxeParam = new LinearLayout.LayoutParams(70, 50);
		
		tvSeasonxEpisode.setPadding(10, 12, 5, 0);

		layout.addView(tvName, nameParam);
		layout.addView(tvSeasonxEpisode, sxeParam);

		addView(layout, generalParams);
	}

}
