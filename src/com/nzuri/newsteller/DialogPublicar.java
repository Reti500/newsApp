package com.nzuri.newsteller;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DialogPublicar extends Activity {

	CheckBox box1,box2,box3,box4;
	CheckBox faceBox,twtBox;
	TextView tituloV;
	EditText noticiaV;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_publicar);		
		this.setFinishOnTouchOutside(false);
		
//		box1 = (CheckBox)findViewById(R.id.checkBox1);
//		box2 = (CheckBox)findViewById(R.id.checkBox2);
//		box3 = (CheckBox)findViewById(R.id.checkBox3);
//		box4 = (CheckBox)findViewById(R.id.checkBox4);
		
		faceBox = (CheckBox)findViewById(R.id.faceBox);
		twtBox = (CheckBox)findViewById(R.id.twtBox);		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dialog_publicar, menu);
		return true;
	}
	
	public void publicar(View v){
		
//		boolean contenido[] = {box1.isChecked(),box2.isChecked(),box3.isChecked(),
//				                 box4.isChecked()};			
		//faceBox.isChecked();
		
		Intent myIntent= getIntent(); // gets the previously created intent
		String titulo = myIntent.getStringExtra("titulo"); // will return "FirstKeyValue"
		String noticia= myIntent.getStringExtra("noticia"); // will return "SecondK		
		
//		Toast.makeText(DialogPublicar.this, "Titulo: "+titulo+" Noticia: "+noticia, Toast.LENGTH_SHORT).show();
		
		Intent i = getIntent();	
		//setResult(RESULT_OK, i);
		setResult(2);
		
		DialogPublicar.this.finish();
		
	}
	
	

}
