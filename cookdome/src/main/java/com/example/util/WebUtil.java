package com.example.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;



public class WebUtil{
	/**
	 * ?????????????????????
	 * @param url
	 * @return
	 * @throws IOException 
	 */
		public static InputStream  getByWeb(String urlStr) throws IOException{
			URL url = new URL(urlStr);
			HttpURLConnection conn =(HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0");//???????403????
			if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
				InputStream is = conn.getInputStream();
				return is;
			}else{
				return null;
			}
			
		}
		
		/**
		 * ?????inputstream????????????????string
		 * @param in
		 * @return
		 * @throws IOException 
		 */
		public static String  getStringByInputStream(InputStream in) throws IOException{
			String str = null;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = in.read(buf)) != -1) {
				bos.write(buf, 0, len);
			}
			str = new String(bos.toByteArray());
			
			return str;
		}
		/**
		 * ?????inputstream????????????????Bitmap
		 * @param in
		 * @return
		 * @throws IOException 
		 */		
		public static Bitmap  getBitmapByInputStream(InputStream in) throws IOException{
			return BitmapFactory.decodeStream(in);
		}
}
