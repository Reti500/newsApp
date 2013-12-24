package com.nzuri.newsteller;

//import static com.nzuri.spot.util.Fonts.setFontFairview;
import static com.nzuri.lib.Imagen.galleryAddPic;
import static com.nzuri.lib.Imagen.getNewSpotPhotoFile;
import static com.nzuri.lib.Util.copyStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class FotoDialog extends Activity {
	private static final String TAG = "FotoDialog";
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
	private int[] textview_ids = {
		R.id.textView0, R.id.textView1, R.id.textView2	
	};

	private final static int REQUEST_CODE_GALLERY = 0x1;
	private final static int REQUEST_CODE_TAKE_PICTURE = 0x2;
    private File mFileTemp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foto_dialog);
		this.setFinishOnTouchOutside(false);

		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		
		for (int i = 0; i < textview_ids.length; i++) {
			int t_id = textview_ids[i];
			TextView tvw = (TextView)findViewById(t_id);
			tvw.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.menu_text_size));
//			setFontFairview(tvw);
		}
		ImageButton bcamara = (ImageButton)findViewById(R.id.boton_camara);
		bcamara.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				takePicture();
			}
		});
		ImageButton bgaleria = (ImageButton)findViewById(R.id.boton_galeria);
		bgaleria.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openGallery();
			}
		});
		
		if (savedInstanceState == null) {
	        
	    	String state = Environment.getExternalStorageState();
	    	if (Environment.MEDIA_MOUNTED.equals(state)) {
//	    		mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
	    		mFileTemp = getNewSpotPhotoFile();
	    	}
	    	else {
	    		mFileTemp = new File(getFilesDir(), TEMP_PHOTO_FILE_NAME);
	    	}
			
		}
		else {
			mFileTemp = (File) savedInstanceState.getSerializable("file");
		}
	}
	
	private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
        	Uri mImageCaptureUri = null;
        	mImageCaptureUri = Uri.fromFile(mFileTemp);
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        } catch (ActivityNotFoundException e) {
            Log.wtf(TAG, "cannot take picture", e);
        }
    }

    private void openGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);
    }
	
	@Override
	protected void onActivityResult(int request, int result, Intent data) {
		if (result!=Activity.RESULT_OK){
			return;
		}
		Intent intent = new Intent();
		switch (request) {
		case REQUEST_CODE_GALLERY:
			InputStream inputStream;
			try {
				inputStream = getContentResolver().openInputStream(data.getData());
	            FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
	            copyStream(inputStream, fileOutputStream);
	            fileOutputStream.close();
	            inputStream.close();
	            
	            intent.putExtra("type", "gallery");
				intent.putExtra("data", mFileTemp.getPath());
			} catch (IOException e) {
				e.printStackTrace();
				Log.wtf("spot", e.toString());
			}
			break;
		case REQUEST_CODE_TAKE_PICTURE:
			String mCurrentPhotoPath = mFileTemp.getPath();
			intent.putExtra("type", "camera");
			intent.putExtra("data", mCurrentPhotoPath);
			galleryAddPic(this, mCurrentPhotoPath);
			break;
		}
		setResult(RESULT_OK, intent);
		finish();
		
	}
    
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra("data", "");
		setResult(RESULT_OK, intent);
		super.onBackPressed();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("file", mFileTemp);
	}
}