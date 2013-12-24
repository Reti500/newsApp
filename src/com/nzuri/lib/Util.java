package com.nzuri.lib;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

public class Util {

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	public final static int getAppVersion(Context context) {
	    try {
	    	if(context == null) Log.i("version","context null");
	    	if(context.getPackageManager() == null) Log.i("version","context.getPackageManager() null");
	        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
	    	if(packageInfo == null) Log.i("version","packageInfo null");

	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	        // should never happen
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	public final static SharedPreferences getPreferences(Context context) {
	    return PreferenceManager.getDefaultSharedPreferences(context);
	    //getSharedPreferences(LoginActivity.class.getSimpleName(),Context.MODE_PRIVATE);
	}
	
	public final static void copyStream(InputStream input, OutputStream output)
            throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }
	
	public final static void ocultaTeclado(Activity c) {
		InputMethodManager imm = (InputMethodManager) c.getSystemService(
			      Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(c.getCurrentFocus().getWindowToken(), 0);
		c.getCurrentFocus().clearFocus();
	}

}
