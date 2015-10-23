package com.bhargain.azam.bb;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Azam on 10/17/2015.
 */
public class JsonParsor {



    final String TAG = "JsonParser.java";

    static InputStream is = null;
    static JSONObject jObj = null;
    static String str = "";

    public JSONObject getJSONFromUrl(String url,List<NameValuePair> params) {

        try {
            HttpClient client = new DefaultHttpClient();

            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse response = client.execute(post);

            HttpEntity entity = response.getEntity();

            Log.e("Azam", "json params: " + params.get(1).getValue());

            is = entity.getContent();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




        try
        {
            BufferedReader br=new BufferedReader(new InputStreamReader(is,"iso-8859-1"), 8);

            StringBuilder builder=new StringBuilder();
            String line=null;

            while((line=br.readLine())!=null)
            {
                builder.append(line + "\n");
            }

            is.close();

            str=builder.toString();

            Log.e("Azam", "json inside: " + str);
        }
        catch(Exception e)
        {

        }

        try {
            jObj=new JSONObject(str);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return jObj;
    }
}
