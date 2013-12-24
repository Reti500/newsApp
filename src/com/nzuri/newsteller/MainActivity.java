package com.nzuri.newsteller;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity  {
    
	
	 AlertDialog.Builder builder;
	 boolean validSession = false;
	 Intent intent = new Intent();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		loginFace();
	}
	
	
   public void FaceDialog(View v){
       
	   intent.setClass(MainActivity.this, ManejadorPage.class);
       startActivity(intent);
       finish(); 
	 
    }
    
   public void TwtDialog(View v){
	   
	  
    }
   
   public void loginFace()
   {
	 //Iniciar sesion facebook	   
	 if(validSession){//validar inicio de sesion de facebook
		 intent.setClass(MainActivity.this, ManejadorPage.class);
         startActivity(intent);
         finish(); 
	 }
	 else{
		 
	 }
   }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
