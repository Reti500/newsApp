package com.nzuri.newsteller;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomList extends ArrayAdapter<String>{
	
	private final Activity context;
	private final String[] web;
	private final Integer[] imageId;
	private final Integer[] puntos;
	
	public CustomList(Activity context,String[] web, Integer[] imageId,Integer[] puntos) {
		super(context, R.layout.list_single, web);
		this.context = context;
		this.web = web;
		this.imageId = imageId;
		this.puntos= puntos;
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.list_single, null, true);
		
		TextView txtTitle = (TextView) rowView.findViewById(R.id.list_single_txt1);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.list_single_img1);
		TextView textPuntos = (TextView) rowView.findViewById(R.id.list_single_puntos_text);
		
		txtTitle.setText(web[position]);
		imageView.setImageResource(imageId[position]);
		textPuntos.setText(String.valueOf(puntos[position]));
		return rowView;
	}
}