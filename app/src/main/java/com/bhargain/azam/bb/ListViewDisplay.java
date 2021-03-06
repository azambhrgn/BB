package com.bhargain.azam.bb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bhargain.azam.bb.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Azam on 10/12/2015.
 */
public class ListViewDisplay extends ActionBarActivity {


    Context context;

    private String bgrp, antigen,city;
    private DBAdapter mydb;

    ListView list;
    String[] name_array = null;
    String[] fathername_array = null;
    String[] mohalla_array = null;
    String[] phone_array = null;
    String[] city_array = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_list);


        context = getApplicationContext();


        mydb = new DBAdapter(context);
        //context=getApplicationContext();

        //helper=new HelperClass(context,"AZDB");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            bgrp = (String) bundle.get("bldgrp");
            antigen = (String) bundle.get("antigen");
            city= (String) bundle.get("city");
        }

        String temp = null;
        if (antigen.equals("0")) {
            temp = "Negative(-)";
        }
        if (antigen.equals("1")) {
            temp = "Positive(+)";
        }

        setTitle(bgrp + " " + temp);
        //Toast.makeText(this,bgrp+"",Toast.LENGTH_LONG).show();


        Log.d("Az", "Inside cust2.2");
        //list.setAdapter(mydb.getListToDisplay(bgrp, antigen));
        //list.setAdapter(adapter);

        String status = "FB";
        //String city = "Vadodara";

        //async task executing
        //new AsyncTaskJsonParse().execute();
        try {
            JSONObject output = new AsyncTaskJsonParse1(ListViewDisplay.this, status, bgrp, antigen, city).execute().get();

            Log.e("Azam after json", "Check");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }


    class AsyncTaskJsonParse1 extends AsyncTask<String, String, JSONObject> {
        final String TAG = "Azam khan";
        private String url = "http://bhargain.in/webservices/";
        List<NameValuePair> param = new ArrayList<NameValuePair>();

        private Context context;
        private ProgressDialog progress;

        String msg;

        ListView list;
        String[] name_array = null;
        String[] fathername_array = null;
        String[] mohalla_array = null;
        String[] phone_array = null;
        String[] city_array = null;

        //
        String status;
        String uname, pass;
        String name, fathername, mohalla, city, phone, insertedby;
        String bldgrp, antigen;

        // contacts JSONArray
        JSONArray dataJsonArr = null;
        JSONObject jsonobj, src;
        String str = "checking";


        public AsyncTaskJsonParse1(Context context, String status, String bldgrp, String antigen, String city) {
            this.bldgrp = bldgrp;
            this.antigen = antigen;
            this.city = city;
            this.context = context;
            //progress=new ProgressDialog(context);

            Log.e("Inconstructor  azam", bldgrp);
            param.add(new BasicNameValuePair("status", status));
            param.add(new BasicNameValuePair("bldgrp", bldgrp));
            param.add(new BasicNameValuePair("antigen", antigen));
            param.add(new BasicNameValuePair("city", city));

            url = url + "getFromBB.php";

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("In preexecution  azam", "Preexecution 1");

            /*progress = new ProgressDialog(context,R.style.MyTheme);
            progress.setCancelable(false);
            progress.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progress.show();*/
            progress=new ProgressDialog(context);
        progress.setMessage("Processing...");
        progress.setIndeterminate(false);

        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setCancelable(true);
        Log.e("In preexecution  azam", "Preexecution 2");
        progress.show();
            if (progress.isShowing()) {
                Log.e("In preexecution  azam", "Showing 2");
            }
        }


        @Override
        protected JSONObject doInBackground(String... arg0) {
            // TODO Auto-generated method stub

            try {
                JsonParsor parse = new JsonParsor();
                Log.e("diInbackgrnd  azam", bldgrp);

                Log.e("Inbackgrnd params azam", url + "");
                jsonobj = parse.getJSONFromUrl(url, param);

                //src=jsonobj.getJSONObject("data");


                // data=(jsonobj.get("data"));
                //JSONArray array = (JSONArray)data;

                // msg=src.getString("uname");

                Log.e(TAG, "firstname: " + jsonobj.getString("message"));

            } catch (Exception e) {
                Log.e(TAG, " " + e);
            }


            return jsonobj;
        }

        @Override
        protected void onPostExecute(JSONObject output) {
            // TODO Auto-generated method stub
            super.onPostExecute(output);

            try {

                JSONObject src = output.getJSONObject("data");
                Log.e("Error", "found" + output.getString("success"));

                String flag = output.getString("success");
                String flagmsg = output.getString("message");


                if (flag.equals("1")) {

                    Log.e("debug", "flag" + flag);
                    JSONArray jarr_name = new JSONArray(src.getString("name"));
                    JSONArray jarr_fathername = null;

                    jarr_fathername = new JSONArray(src.getString("fathername"));

                    JSONArray jarr_mohalla = new JSONArray(src.getString("mohalla"));
                    JSONArray jarr_city = new JSONArray(src.getString("city"));
                    JSONArray jarr_phone = new JSONArray(src.getString("phone"));

                    int n = jarr_name.length();

                    name_array = new String[n];
                    fathername_array = new String[n];
                    mohalla_array = new String[n];
                    phone_array = new String[n];
                    city_array = new String[n];
                    for (int i = 0; i < n; i++) {
                        name_array[i] = (String) jarr_name.get(i);
                        fathername_array[i] = (String) jarr_fathername.get(i);
                        mohalla_array[i] = (String) jarr_mohalla.get(i);
                        phone_array[i] = (String) jarr_phone.get(i);
                        city_array[i] = (String) jarr_city.get(i);

                        Log.d("Inside StringArray", i + "");
                    }

                    Log.d("msglength", name_array[0]);


                    //JSONArray jsonarray=output.toJSONArray("data");
                    Log.d("Size ", output.getString("size"));
                    String msg = src.getString("name");
                    Log.d("msg", msg);
                    //Log.d("JsonArray", (String) arr.get(1));
                    Log.d("Nameza", src.getString("name"));
                    //Convert Json array to list/Array  and pass to Adaptor
                    //JSONObject json_name=src.getJSONObject("name");


                    // Toast.makeText(this, "Az 1" + src.getString("name"), Toast.LENGTH_LONG).show();


                    list = (ListView) findViewById(R.id.listView);
                    CustomListAdapter custAdaptor = new CustomListAdapter((Activity) context, name_array, fathername_array, mohalla_array, city_array, phone_array);
                    list.setAdapter(custAdaptor);
                    Log.d("Az", "Inside cust3");

                } else {
                    Toast.makeText(context, "Data not found" + flagmsg, Toast.LENGTH_LONG).show();

                }

            } catch (JSONException je) {

            }


            progress.dismiss();

            if (progress.isShowing()) {
                Log.e("In onPost  azam", "Showing 2");
            }
            //progress.dismiss();


        }


    }
}


class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] name;
    private final String[] fathername;
    private final String[] mohalla;
    private final String[] city;
    private final String[] phone;

    public CustomListAdapter(Activity context, String[] name, String[] fathername, String[] mohalla, String[] city, String[] phone) {
        super(context, R.layout.listview, name);
        // TODO Auto-generated constructor stub
        Log.d("Azam", "Adapter 1");
        this.context = context;
        this.name = name;
        this.fathername = fathername;
        this.mohalla = mohalla;
        this.city = city;
        this.phone = phone;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.listview, null, true);

        TextView t_name = (TextView) rowView.findViewById(R.id.lvname);
        TextView t_fathername = (TextView) rowView.findViewById(R.id.lvfathername);
        TextView t_mohalla = (TextView) rowView.findViewById(R.id.lvmohalla);
        //TextView t_city=rowView.findViewById(R.id.lvcity);
        TextView t_phone = (TextView) rowView.findViewById(R.id.lvphone);
        TextView t_city= (TextView) rowView.findViewById(R.id.lvcity);

        t_name.setText(name[position]);
        t_fathername.setText(fathername[position]);
        t_mohalla.setText(mohalla[position]);
        t_phone.setText(phone[position]);
        t_city.setText(city[position]);


        return rowView;
    }


}




/*
class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final String[] itemaddr;


    public CustomListAdapter(Activity context, String[] itemname,String[] itemaddr) {
        super(context,R.layout.listview,itemname);
        // TODO Auto-generated constructor stub
        Log.d("Azam", "Adapter 1");
        this.context= context;
        this.itemname=itemname;
        this.itemaddr=itemaddr;
    }


    public View getView(int position,View view,ViewGroup parent) {
        Log.d("Azam", "Adapter 2");
        LayoutInflater inflater=context.getLayoutInflater();

        View rowView=inflater.inflate(R.layout.listview, null,true);

        TextView t_name = (TextView) rowView.findViewById(R.id.lvname);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView t_addr = (TextView) rowView.findViewById(R.id.lvmohalla);

        t_name.setText(itemname[position]);
        //imageView.setImageResource(imgid[position]);
        t_addr.setText("Description "+itemaddr[position]);
        return rowView;

    };

}*/
