package com.nzuri.newsteller;

import static com.nzuri.lib.Imagen.decodeSampledBitmapFromPath;
import static com.nzuri.lib.Imagen.rotateIfNeeded;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import android.app.ActionBar;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class ManejadorPage extends FragmentActivity implements ActionBar.TabListener {

	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	 ViewPager mViewPager;
	 TextView titulo;
	 EditText noticia;
	 Button camara,video;
	 Spinner sp;
	 String image_path = "";
	 
	 ImageView jpgView;
	 private String imPath;		
	 private Bitmap mBitmap;
	
   
	 AlertDialog.Builder builder;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_noticias);
		
		
		// Set up the action bar.
			
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		mViewPager.setCurrentItem(1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_total, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment;
			switch(position)
			{
				
			case 0:
				fragment = new Mi_Periodico();				
				return fragment;
			case 1:
				fragment = new Noticias();
				return fragment;
				
			case 2:
				fragment = new Redactar();
				return fragment;
				
			default:
			    fragment = new DummySectionFragment();
				Bundle args = new Bundle();
				args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				return fragment;
			}
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_noticias_dummy,
					container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
	}
	
	 public void publicDialog(View v){  	
		 
//		 sp = (Spinner)findViewById(R.id.spin);
//			   ArrayAdapter<CharSequence> ar = ArrayAdapter.createFromResource(getApplicationContext(), R.array.week, android.R.layout.simple_list_item_1);
//			   ar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//			   sp.setAdapter(ar);		   
			  
		 titulo = (TextView)findViewById(R.id.titulo);
		 noticia = (EditText)findViewById(R.id.noticia);			
		 camara = (Button)findViewById(R.id.camera);
		 video = (Button)findViewById(R.id.video);		 
		
		 
		 if(titulo.getText().toString().trim().length() == 0 || 
				 noticia.getText().toString().trim().length() == 0 ){
			 
			 Toast.makeText(ManejadorPage.this,"No pusiste Titulo ó tu Noticia", Toast.LENGTH_SHORT).show();		
             			 
		 }
		 else{
			 
			 if(image_path.length() == 0 ){
				  
				  AlertDialog.Builder builder = new AlertDialog.Builder(this);
				  builder.setMessage("¿Desea tomar una foto?")
				          .setCancelable(false)
				          .setNegativeButton("No",
				                  new DialogInterface.OnClickListener() {
				                      public void onClick(DialogInterface dialog, int id) {
				                    	  
				                         dialog.cancel();
				                          
				                         String t = titulo.getText().toString();
				             			 String n = noticia.getText().toString();		 
				             			 Toast.makeText(ManejadorPage.this, "Titulo: "+t+" Noticia: "+n, Toast.LENGTH_SHORT).show();		
				             			 Intent intent = new Intent();
				             			 intent.setClass(ManejadorPage.this, DialogPublicar.class); 
				             			 
				             			 intent.putExtra("titulo",t);
				             			 intent.putExtra("noticia",n);
				             	         
				             			 startActivityForResult(intent, 2);
				                      }
				                  })
				          .setPositiveButton("Si",
				                  new DialogInterface.OnClickListener() {
				                      public void onClick(DialogInterface dialog, int id) {
				                    	  
				                    	  Intent intent = new Intent();
				                 		  intent.setClass(ManejadorPage.this, FotoDialog.class); 
				                 		  startActivityForResult(intent, 1);
				                      }
				                  });
				  AlertDialog alert = builder.create();
				  alert.show();
			  }	
			 else{
				 
				 String t = titulo.getText().toString();
     			 String n = noticia.getText().toString();		 
     			 Toast.makeText(ManejadorPage.this, "Titulo: "+t+" Noticia: "+n, Toast.LENGTH_SHORT).show();		
     			 Intent intent = new Intent();
     			 intent.setClass(ManejadorPage.this, DialogPublicar.class); 
     			 
     			 intent.putExtra("titulo",t);
     			 intent.putExtra("noticia",n);
     	         
     			 startActivityForResult(intent, 2);
			 }
		 }
	  }
	 
	 public void tomarFoto(View v){
		 
		 Intent intent = new Intent();
		 intent.setClass(ManejadorPage.this, FotoDialog.class); 
		 startActivityForResult(intent, 1);
//		 startActivity(intent);
		 
	 }

	 @Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			
			jpgView = (ImageView)findViewById(R.id.foto);  
			
			if(requestCode==1){
					
						 image_path = data.getStringExtra("data");
						ImageView imageView = (ImageView)findViewById(R.id.foto);
						loadFromPath(image_path, imageView);
				    
			}
			else if(requestCode==2){
				
				titulo.setText("");
				noticia.setText("");
				image_path = "";
				jpgView.setImageResource(R.drawable.ic_launcher);
//				Toast.makeText(getApplicationContext(), "Tu noticia se publico :)", Toast.LENGTH_SHORT).show();			
								
			}
		}
	 public final void loadFromPath(String path, ImageView imageView) {
	        if (path == null) {
	            return;
	        }
	        mBitmap = decodeSampledBitmapFromPath(path, 
	        		(imageView.getWidth() != 0)?imageView.getWidth():100, 
	        				(imageView.getHeight() != 0)?imageView.getHeight():100);
	        try {
	        	mBitmap = rotateIfNeeded(path, mBitmap);
			} catch (IOException e) {
				e.printStackTrace();
			}
	        imageView.setImageBitmap(mBitmap);
		}

}
