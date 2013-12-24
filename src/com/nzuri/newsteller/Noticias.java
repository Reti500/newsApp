package com.nzuri.newsteller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

public class Noticias extends Fragment {
    
	
	public Noticias() {
	}

	  TextView sinConexion;
	     TextView sinNoticias;
	     View rootView;
	     boolean conexionFake = true;
	     boolean notNotice = false; 	
	     
	    private SettingsListAdapter adapter;
	 	private ExpandableListView categoriesList;
	 	private ArrayList<Category> categories;
	 	protected Context mContext;
	 	
	     
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			rootView = inflater.inflate(R.layout.noticias,
					container, false);
			
			mContext = getActivity().getBaseContext();
			categoriesList = (ExpandableListView)rootView.findViewById(R.id.categories);
			categories = Category.getCategories();
			adapter = new SettingsListAdapter(getActivity(), 
					categories, categoriesList);
	        categoriesList.setAdapter(adapter);   
	        
	        categoriesList.setOnChildClickListener(new OnChildClickListener() {
				
				@Override
				public boolean onChildClick(ExpandableListView parent, View v,
						int groupPosition, int childPosition, long id) {

					
//					parent.startAnimation(list_anim);
					
					
					CheckedTextView checkbox = (CheckedTextView)v.findViewById(R.id.list_item_text_child);
					checkbox.toggle();
					
					String name = checkbox.getText().toString();
					
					Toast.makeText(getActivity(),checkbox.getText().toString() , Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setClass(getActivity(),Noticia_oficial.class); 					
									
					intent.putExtra("titulo",checkbox.getText().toString());
					startActivity(intent);	
					
					// find parent view by tag
					View parentView = categoriesList.findViewWithTag(categories.get(groupPosition).name);
					if(parentView != null) {
						TextView sub = (TextView)parentView.findViewById(R.id.list_item_text_subscriptions);
						//HorizontialListView sub=  (HorizontalScrollView)parentView.findViewById(R.id.list_item_text_subscriptions);

						if(sub != null) {
							Category category = categories.get(groupPosition);
							if(checkbox.isChecked()) {
								// add child category to parent's selection list
								category.selection.add(checkbox.getText().toString());
								
								
								// sort list in alphabetical order
								Collections.sort(category.selection, new CustomComparator());
							
							}
							else {
								// remove child category from parent's selection list
								category.selection.remove(checkbox.getText().toString());
							}		
							
							// display selection list
						
							
							sub.setText(category.selection.toString());
							
						
						}
					}				
					return true;
				}
			});
		
		
//			TextView dummyTextView = (TextView) rootView
//					.findViewById(R.id.section_label);
//			dummyTextView.setText(Integer.toString(getArguments().getInt(
//					ARG_SECTION_NUMBER)));
			sinConexion = (TextView)rootView.findViewById(R.id.sinConexion);
			sinNoticias = (TextView)rootView.findViewById(R.id.sinNoticias);		
			//cargarNoticias(isOnline(),false);
			return rootView;	
			
		}
		
		public void cargarNoticias(boolean conexion,boolean noticias){
			
			if(conexion && noticias){
				//carga TODO Puto
			}
			else if(conexion == true && noticias == false){
				
				sinConexion.setVisibility(sinConexion.INVISIBLE);
				sinNoticias.setVisibility(sinNoticias.VISIBLE);
			}
			else{
				sinNoticias.setVisibility(sinNoticias.INVISIBLE);
				sinConexion.setVisibility(sinConexion.VISIBLE);
			}
		}
		
		@Override
		public void onResume() {
			//cargarNoticias(isOnline(),false);
			super.onResume();
		}

		public boolean isOnline() 
		{
			WifiManager connManager = (WifiManager)getActivity().getSystemService(Context.WIFI_SERVICE);
			
			if (connManager.isWifiEnabled()) {
			    return true;
			}
			else{
				return false;
			}
		}
		
		 public class CustomComparator implements Comparator<String> {
		        @Override
		        public int compare(String o1, String o2) {
		            return o1.compareTo(o2);
		        }
			}

	}