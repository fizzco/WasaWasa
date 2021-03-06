package net.fizzco.wassrclient.wasawasa.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HttpClient {

	static final String APP_NM = "WasaWasa!";

	/**
	 *
	 * @param strUrl
	 * @return
	 */
    public static byte[] getByteArrayFromURL(String urlStr) {
        byte[] byteArray = new byte[1024];
        byte[] result = null;
        HttpURLConnection con = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        int size = 0;
        try {
            URL url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            in = con.getInputStream();

            out = new ByteArrayOutputStream();
            while ((size = in.read(byteArray)) != -1) {
                out.write(byteArray, 0, size);
            }
            result = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null)
                    con.disconnect();
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static Bitmap getImage(String url) {
        byte[] byteArray = getByteArrayFromURL(url);
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    // ここをログイン情報を付加する形に直す
    public static byte[] getByteArrayFromURL(String sUrl,String id, String pass) {
        byte[] byteArray = new byte[1024];
        byte[] result = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        int size = 0;
        
		DefaultHttpClient httpClient = new DefaultHttpClient();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(id, pass);
		AuthScope scope = new AuthScope("api.wassr.jp", 80);
		httpClient.getCredentialsProvider().setCredentials(scope, credentials);
		HttpPost httppost = new HttpPost(sUrl);
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("source", APP_NM));
		
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePair,HTTP.UTF_8));
			HttpResponse response = httpClient.execute(httppost);
			
			in = response.getEntity().getContent(); 
            out = new ByteArrayOutputStream();
            while ((size = in.read(byteArray)) != -1) {
                out.write(byteArray, 0, size);
            }
            result = out.toByteArray();
        } catch (Exception e) {
        	Log.d(APP_NM,e.toString());
		} finally {
			// 解放
            try {
            	httpClient.getConnectionManager().shutdown();
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
            } catch (Exception e) {
            	Log.d(APP_NM,e.toString());
            }
        }
        return result;
    }

    public static Bitmap getImage(String url,String id, String ps) {
        byte[] byteArray = getByteArrayFromURL(url,id,ps);
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

/**
 * doPost
 *
 * @param url
 * @param id
 * @param pass
 * @param params
 * @return int StatusCode
 */
	public static int doPost(String url , String id , String pass, String params) {

		DefaultHttpClient httpClient = new DefaultHttpClient();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
				id, pass);

		AuthScope scope = new AuthScope("api.wassr.jp", 80);
		httpClient.getCredentialsProvider().setCredentials(scope, credentials);

		HttpPost httppost = new HttpPost(url);
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("status", params));
		nameValuePair.add(new BasicNameValuePair("source", APP_NM));

		int statusCode = -1;

		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePair,
					HTTP.UTF_8));
			HttpResponse response = httpClient.execute(httppost);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			response.getEntity().writeTo(byteArrayOutputStream);

			
			statusCode = response.getStatusLine().getStatusCode();
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			Log.d(APP_NM,e.toString());
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			Log.d(APP_NM,e.toString());
		} finally {
			// 解放
			httpClient.getConnectionManager().shutdown();
		}
		return statusCode;
	}
	
	public static String doPost1(String url , String id , String pass, String params) {
		String json=null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
				id, pass);

		AuthScope scope = new AuthScope("api.wassr.jp", 80);
		httpClient.getCredentialsProvider().setCredentials(scope, credentials);

		HttpPost httppost = new HttpPost(url);
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("status", params));
		nameValuePair.add(new BasicNameValuePair("source", APP_NM));

		//int statusCode = -1;
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePair,
					HTTP.UTF_8));
			HttpResponse response = httpClient.execute(httppost);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			response.getEntity().writeTo(byteArrayOutputStream);
			//statusCode = response.getStatusLine().getStatusCode();
			
			json = response.getEntity().getContent().toString();
 
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			Log.d(APP_NM,e.toString());
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			Log.d(APP_NM,e.toString());
		} finally {
			// 解放
			httpClient.getConnectionManager().shutdown();
		}
		return json;
	}
}
