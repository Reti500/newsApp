package com.nzuri.newsteller;

import static com.nzuri.lib.Imagen.decodeSampledBitmapFromPath;
import static com.nzuri.lib.Imagen.rotateIfNeeded;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Editar_perfil extends Activity {

	ImageView foto_perfil = null;
	String image_path= null;
	Bitmap mBitmap =null;
	Button btn_ok= null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_perfil);
		foto_perfil= (ImageView) findViewById(R.id.editar_perfil_foto_perfil);
		btn_ok = (Button) findViewById(R.id.editar_perfil_btn_ok);
		
		btn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				setResult(RESULT_OK, i);
				i.putExtra("data", image_path);
				finish();
			}
		});
		
		foto_perfil.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tomarFoto();
				
			}
		});
	}

	public void tomarFoto(){
		 
		 Intent intent = new Intent();
		 intent.setClass(Editar_perfil.this, FotoDialog.class); 
		 startActivityForResult(intent, 1);
//		 startActivity(intent);
		 
	 }

	 @Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			
			foto_perfil = (ImageView)findViewById(R.id.editar_perfil_foto_perfil);  
			
			if(requestCode==1){
					
						 image_path = data.getStringExtra("data");
						loadFromPath(image_path, foto_perfil);
				    
			}
			else if(requestCode==2){
				image_path = "";
				foto_perfil.setImageResource(R.drawable.avatar_perfil);
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
