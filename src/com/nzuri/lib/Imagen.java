package com.nzuri.lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;

import org.json.JSONObject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

//import com.cloudinary.Cloudinary;
//import com.google.android.gms.maps.model.LatLng;

public class Imagen {
	public static abstract class ResponseListener {
		public abstract void error(String msg);
		public abstract void success(String val);
	}
    public static final String SPOT_DIRECTORY = "Spot/";
    public static final String SPOT_PHOTOS_DIRECTORY_NAME = "Spot/Spot/";
	public static final String SPOT_CACHE_DIRECTORY_NAME = "Spot/cache/";
	public static final String SPOT_DOWNLOAD_DIRECTORY_NAME = "Spot/download/";
	
	public final static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	
	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;
	
	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }
	
	    return inSampleSize;
	}
	
	public final static Bitmap decodeSampledBitmapFromPath(String path, int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(path, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(path, options);
	}
	
	public final static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeResource(res, resId, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeResource(res, resId, options);
	}
	
	public final static Bitmap decodeSampledBitmapFromURL(String urldisplay, 
	        int reqWidth, int reqHeight) {
	    try {
	    	URL url = new URL(urldisplay);
	    	// First decode with inJustDecodeBounds=true to check dimensions
	    	final BitmapFactory.Options options = new BitmapFactory.Options();
	    	options.inJustDecodeBounds = true;
	        BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, options);
	        
	        // Calculate inSampleSize
		    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		    // Decode bitmap with inSampleSize set
		    options.inJustDecodeBounds = false;
		    return BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, options);
	    } 
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	private static String fromInt(int val) {
    	return String.valueOf(val);
    }
	
	public final static void galleryAddPic(Context c, String mCurrentPhotoPath) {
	    File f = new File(mCurrentPhotoPath);
//	    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//	    Uri contentUri = Uri.fromFile(f);
//	    mediaScanIntent.setData(contentUri);
//	    this.sendBroadcast(mediaScanIntent);
	    
	    try {
			MediaStore.Images.Media.insertImage(c.getContentResolver(), mCurrentPhotoPath, f.getName(), f.getName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
//	public final static LatLng getLatLng(String path) {
//		ExifInterface ein;
//		try {
//			ein = new ExifInterface(path);
//			float[] ll = new float[2]; 
//			if (ein.getLatLong(ll)){
//				Log.d("image", "foto lat:"+ll[0]+"*** lng:"+ll[1]);
//				return new LatLng(ll[0], ll[1]);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			Log.wtf("image", e.toString());
//		}
//		return null;
//	}
	
	public final static boolean getLatLng(String path, float latlng[]) {
		if (latlng == null || latlng.length < 2) {
			return false;
		}
		ExifInterface ein;
		try {
			ein = new ExifInterface(path);
			if (ein.getLatLong(latlng)){
				Log.d("image", "foto lat:"+latlng[0]+"*** lng:"+latlng[1]);
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			Log.wtf("image", e.toString());
		}
		return false;
	}
	
	public final static String getMomentName(){
    	Calendar c = Calendar.getInstance();
    	String date = fromInt(c.get(Calendar.MONTH)+1)
    	  + fromInt(c.get(Calendar.DAY_OF_MONTH))
    	  + fromInt(c.get(Calendar.YEAR)) + "_"
    	  + fromInt(c.get(Calendar.HOUR_OF_DAY))
    	  + fromInt(c.get(Calendar.MINUTE))
    	  + fromInt(c.get(Calendar.SECOND))
    	  + ".jpg";
    	return date;
    }
	
	public final static File getNewSpotPhotoFile() {
		File spotPhotosDirectory = new File(Environment.getExternalStorageDirectory(), SPOT_PHOTOS_DIRECTORY_NAME);
		Log.d("spot", spotPhotosDirectory.getPath());
		if (!spotPhotosDirectory.exists() ){
			spotPhotosDirectory.mkdirs();
		}
		String name = getMomentName();
		File res = new File(spotPhotosDirectory, name);
		Log.d("spot", res.getPath());
		try {
			res.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			Log.wtf("spot", e.toString());
		}
		return res;
	}
    public final static File getSpotCacheDir() {
    	File res = new File(Environment.getExternalStorageDirectory(), SPOT_CACHE_DIRECTORY_NAME);
		if (!res.exists() ){
			res.mkdirs();
		}
		return res;
	}
    public final static File getSpotDownloadDir() {
    	File res = new File(Environment.getExternalStorageDirectory(), SPOT_DOWNLOAD_DIRECTORY_NAME);
		if (!res.exists() ){
			res.mkdirs();
		}
		return res;
	}
    public final static File getSpotDir() {
    	File res = new File(Environment.getExternalStorageDirectory(), SPOT_DIRECTORY);
		if (!res.exists() ){
			res.mkdirs();
		}
		return res;
	}
	
	public final static Bitmap rotateIfNeeded(String photoPath, Bitmap bitmap) throws IOException{
		ExifInterface ei = new ExifInterface(photoPath);
		int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

		switch(orientation) {
		    case ExifInterface.ORIENTATION_ROTATE_90:
				Log.d("spot", "Rotada 90");
		        return rotateImage(bitmap, 90);
//		        break;
		    case ExifInterface.ORIENTATION_ROTATE_180:
				Log.d("spot", "Rotada 180");
		        return rotateImage(bitmap, 180);
//		        break;
		    case ExifInterface.ORIENTATION_ROTATE_270:
				Log.d("spot", "Rotada 270");
		        return rotateImage(bitmap, 270);
		}
		return bitmap;
	}
	
	public final static Bitmap rotateImage(Bitmap source, float angle) {
	      Matrix matrix = new Matrix();
	      matrix.postRotate(angle);
	      Bitmap res = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
	      source.recycle();
	      return res;
	}
	
	/*
	 * Cloudinary upload
	 */
//	public final static void uploadImage(final Context context, final String path, final ResponseListener listener) {
//        new AsyncTask<Void, Void, Void>() {

//			@Override
//			protected Void doInBackground(Void... args) {
//				Cloudinary cloudinary = new Cloudinary(context);
//				try {
//					JSONObject jso = cloudinary.uploader().upload(path, Cloudinary.emptyMap());
//					listener.success(jso.toString());
//					Log.d("cloudinary", "upload JSON:"+jso.toString());
//				} catch (IOException e) {
//					e.printStackTrace();
//					Log.wtf("cloudinary", e.toString());
//					listener.error(e.toString());
//				}
//				return null;
//			}
        	
//        }.execute((Void) null);
//	}
//	
}
