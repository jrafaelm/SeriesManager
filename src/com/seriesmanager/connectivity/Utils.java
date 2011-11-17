package com.seriesmanager.connectivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.seriesmanager.SeriesManagerActivity;
import com.seriesmanager.business.Serie;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Utils {
	public static boolean isNetworkAvailable(Context context) { 
		
		//@LuizTiago
		//Got in: http://groups.google.com/group/android-developers/browse_thread/thread/65cdc456b93ad2d9
		
        ConnectivityManager connectivity = (ConnectivityManager) context 
                .getSystemService(Context.CONNECTIVITY_SERVICE); 
        if (connectivity == null) { 
            Log.w("AndroidIMDB", "couldn't get connectivity manager"); 
        } else { 
            NetworkInfo[] info = connectivity.getAllNetworkInfo(); 
            if (info != null) { 
                for (int i = 0; i < info.length; i++) { 
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) { 
                        return true; 
                    } 
                } 
            } 
        } 
        return false; 
    }
	
	
	public static void downloadImage(Serie serie) {
		try {
			
			URL u = new URL(serie.getPosterUrl());
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setRequestProperty("Request-Method", "GET");
			conn.setDoInput(true);
			conn.setDoOutput(false);
			conn.connect();
			InputStream in = conn.getInputStream();
			byte[] bytes = readBytes(in);
			String imageFileStr = "";

			imageFileStr = serie.getDefaultImageFileString();

			conn.disconnect();
			File imageFile = new File(imageFileStr);
			FileOutputStream fos = new FileOutputStream(imageFile);
			BitmapFactory.decodeByteArray(bytes, 0, bytes.length).compress(
					CompressFormat.JPEG, 90, fos);

			bytes = null;


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static byte[] readBytes(InputStream in) throws IOException {
		ByteArrayOutputStream boss = new ByteArrayOutputStream();
		try {
			byte[] buffer = new byte[1024];
			int len;
			while ((len = in.read(buffer)) > 0) {
				boss.write(buffer, 0, len);
			}
			byte[] bytes = boss.toByteArray();
			return bytes;
		} finally {
			boss.close();
			in.close();
		}
	}
}
