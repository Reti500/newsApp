package com.nzuri.newsteller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;


import java.util.Comparator;

public class Mi_Periodico extends Fragment{
	
//	public static final String ARG_SECTION_NUMBER = "section_number";

	public Mi_Periodico() {
	}
     TextView sinConexion;
     TextView sinNoticias;
     View rootView;
     boolean conexionFake = true;
     boolean notNotice = false;
     ImageView foto_perfil= null;
     ImageView btn_fb= null;
     ImageView btn_tw= null;
     ImageView btn_gp= null;
     int CHANGE_IMAGE=10;
     
     ListView list;
 	String[] web = {
 			"Espectáculos",
 			"Noticias",
 			"Tiempo Real",
 			"En el Mundo"
 	} ;
 	Integer[] imageId = {
 			R.drawable.marco_fotos,
 			R.drawable.marco_fotos,
 			R.drawable.marco_fotos,
 			R.drawable.marco_fotos		
 	};
 	Integer[] puntos={
 			1,2,3,4
 	};
     
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.mi_periodico,
				container, false);	
		
		CustomList adapter = new CustomList(getActivity(), web, imageId,puntos);
		
		foto_perfil = (ImageView) rootView.findViewById(R.id.mi_periodico_foto_perfil);
		btn_fb = (ImageView) rootView.findViewById(R.id.mi_periodico_btn_fb);
		btn_tw = (ImageView) rootView.findViewById(R.id.mi_periodico_btn_tw);
		btn_gp = (ImageView) rootView.findViewById(R.id.mi_periodico_btn_gp);
		
		foto_perfil.setOnClickListener(crearToast("Foto de perfil"));
		btn_fb.setOnClickListener(crearToast("Botón de Facebook",btn_fb,R.drawable.boton_fb_inactivo,R.drawable.boton_fb_activo));
		btn_tw.setOnClickListener(crearToast("Botón de twitter",btn_tw,R.drawable.boton_twit_activo,R.drawable.boton_twit_inactivo));
		btn_gp.setOnClickListener(crearToast("Botón de Google Plus",btn_gp,R.drawable.boton_gplus_activo,R.drawable.boton_gplus_inactivo));
		
		
		
		list=(ListView)rootView.findViewById(R.id.list);
				list.setAdapter(adapter);
				list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		            @Override
		            public void onItemClick(AdapterView<?> parent, View view,
		                                    int position, long id) {
		                Toast.makeText(getActivity(), "Has seleccionado " +web[+ position], Toast.LENGTH_SHORT).show();

		            }
		        });
		
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
	
	public OnClickListener crearToast(final String t)
	{
		OnClickListener click = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(),t, Toast.LENGTH_SHORT).show();
				Intent i = new Intent(getActivity(),Editar_perfil.class);
				startActivityForResult(i, CHANGE_IMAGE);
				
			}
		};
		return click;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (requestCode==CHANGE_IMAGE)
		{
			//Toast.makeText(getActivity(),String.valueOf(resultCode), Toast.LENGTH_SHORT).show();
			if (resultCode==-1)
			{
				String path = data.getStringExtra("data");
				Bitmap ok = BitmapFactory.decodeFile(path);
				foto_perfil.setImageBitmap(ok);
				Toast.makeText(getActivity(),path, Toast.LENGTH_LONG).show();
			}
			
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

	public OnClickListener crearToast(final String t,final ImageView img, final int id, final int id2)
	{
		OnClickListener click = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(),t, Toast.LENGTH_SHORT).show();
				img.setImageResource(id);
				img.setOnClickListener(crearToast(t, img, id2, id));
			}
		};
		return click;
	}
}
