package com.nzuri.newsteller;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class Noticia_oficial extends Activity {

	TextView titulo_of;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.noticia_oficial);
		Intent myIntent= getIntent(); // gets the previously created intent
		String titulo = myIntent.getStringExtra("titulo"); // will return "FirstKeyValue"
		
		titulo_of = (TextView)findViewById(R.id.titulo);
		titulo_of.setText(titulo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.noticia_oficial, menu);		
				return true;	
	}

}
