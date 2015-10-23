package com.bhargain.azam.bb;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Azam on 10/17/2015.
 */
public class AsyncTaskJsonParse extends AsyncTask<String, String, JSONObject>
{
    final String TAG = "Azam khan";
    private String url = "http://bhargain.in/webservices/";
    List<NameValuePair> param=new ArrayList<NameValuePair>();

    String msg;



    //
    String status;
    String uname,pass;
    String name,fathername,mohalla,city,phone,insertedby;
    String bldgrp,antigen;

    // contacts JSONArray
    JSONArray dataJsonArr = null;
    JSONObject jsonobj,src;
    String str="checking";

    public AsyncTaskJsonParse(String status,String uname,String pass)
    {
        //Constructor to login in
        uname=this.uname;
        pass=this.pass;
        status=this.status;

        param.add(new BasicNameValuePair("status",status));
        param.add(new BasicNameValuePair("uname",uname));
        param.add(new BasicNameValuePair("password",pass));

    }


    public AsyncTaskJsonParse(String status,String name,String fathername,String mohalla,String city,String phone,String bldgrp,String antigen,String insertedby)
    {
        //constructor to insert in to bloodbank

        this.name=name;
        this.fathername=fathername;
        this.mohalla=mohalla;
        this.city=city;
        this.phone=phone;
        this.bldgrp=bldgrp;
        this.antigen=antigen;
        this.insertedby=insertedby;

       // Log.e("Inconstructor  azam", name);
        //Log.d("Azam",name);

        param.add(new BasicNameValuePair("status",status));
        param.add(new BasicNameValuePair("name",name));
        param.add(new BasicNameValuePair("fathername",fathername));
        param.add(new BasicNameValuePair("mohalla",mohalla));
        param.add(new BasicNameValuePair("city",city));
        param.add(new BasicNameValuePair("phone",phone));
        param.add(new BasicNameValuePair("bldgrp",bldgrp));
        param.add(new BasicNameValuePair("antigen",antigen));
        param.add(new BasicNameValuePair("insertedby",insertedby));

        url=url+"insertToBloodBank.php";
    }

    public AsyncTaskJsonParse(String status,String bldgrp,String antigen,String city)
    {
        this.bldgrp=bldgrp;
        this.antigen=antigen;
        this.city=city;
        Log.e("Inconstructor  azam", bldgrp);
        param.add(new BasicNameValuePair("status",status));
        param.add(new BasicNameValuePair("bldgrp",bldgrp));
        param.add(new BasicNameValuePair("antigen",antigen));
        param.add(new BasicNameValuePair("city",city));

        url=url+"getFromBB.php";

    }


    @Override
    protected JSONObject doInBackground(String... arg0) {
        // TODO Auto-generated method stub

        try
        {
            JsonParsor parse=new JsonParsor();
            Log.e("diInbackgrnd  azam",bldgrp);

            Log.e("Inbackgrnd params azam",url+"");
            jsonobj = parse.getJSONFromUrl(url, param);

            //src=jsonobj.getJSONObject("data");


            // data=(jsonobj.get("data"));
            //JSONArray array = (JSONArray)data;

           // msg=src.getString("uname");

            Log.e(TAG, "firstname: " + jsonobj.getString("message"));

        }
        catch(Exception e)
        {
            Log.e(TAG, " "+e );
        }


        return jsonobj;
    }
    @Override
    protected void onPostExecute(JSONObject result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        //pDialog.dismiss();
        if (msg != null)
        {
            //Toast.makeText(this, "hllo " + msg, Toast.LENGTH_LONG).show();
        }

    }


}
