package com.bhargain.azam.bb;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
 * Created by Azam on 10/11/2015.
 */
public class AddEntry extends ActionBarActivity {

    private Button btncancel, btnsave;
    private EditText etname, etfathername, etmohalla, etphone;
    private Spinner spnbldgrp, spnantigen;
    private String status,name,fathername,mohalla,city,phone,bldgrp,antigen,insertedby;

    ProgressBar pb;

    private DBAdapter mdb;
    JSONObject src;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addentry);

        btncancel = (Button) findViewById(R.id.btncancel);
        btnsave = (Button) findViewById(R.id.btnsave);



       setTitle("B-One");



        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //mdb = new DBAdapter(v.getContext());

                pb= (ProgressBar) findViewById(R.id.progressBar);
                pb.setVisibility(v.getVisibility());

                btnsave.setEnabled(false);
                etname = (EditText) findViewById(R.id.etname);
                etfathername = (EditText) findViewById(R.id.etfatherName);
                etmohalla = (EditText) findViewById(R.id.etmohalla);
                etphone = (EditText) findViewById(R.id.etphone);
                spnbldgrp = (Spinner) findViewById(R.id.spnbld);
                spnantigen = (Spinner) findViewById(R.id.spnantigen);


                //Toast.makeText(v.getContext(),phone+"",Toast.LENGTH_LONG).show();

                status = "IB";
                name = etname.getText().toString();
                fathername = etfathername.getText().toString();
                mohalla = etmohalla.getText().toString();
                city = "Vadodara";
                phone = etphone.getText().toString();
                bldgrp = spnbldgrp.getSelectedItem().toString();
                antigen = spnantigen.getSelectedItem().toString();

                insertedby = "Azam";


                if (antigen.equals("-")) {
                    antigen = "0";
                } else if (antigen.equals("+")) {
                    antigen = "1";
                }


                try {
                    Log.e("In Add Entry azam", name);
                    JSONObject jsonobj = new AsyncTaskJsonParse2(v.getContext(),status, name, fathername, mohalla, city, phone, bldgrp, antigen, insertedby).execute().get();

                    //src=jsonobj.getJSONObject("data");
                    if ((jsonobj.getString("success")).equals("1")) {
                        Toast.makeText(v.getContext(), jsonobj.getString("message"), Toast.LENGTH_LONG).show();
                        Log.e("In Finish azam", "finished");
                        finish();
                    } else {
                        Toast.makeText(v.getContext(), jsonobj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });



        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent("MainActivity.class");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
                finish();

            }
        });
    }



    class AsyncTaskJsonParse2 extends AsyncTask<String, Integer, JSONObject>
    {
        final String TAG = "Azam khan";
        private String url = "http://bhargain.in/webservices/";
        List<NameValuePair> param=new ArrayList<NameValuePair>();

        private Context context;
        //private ProgressDialog progress;

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




        public AsyncTaskJsonParse2(Context context,String status,String name,String fathername,String mohalla,String city,String phone,String bldgrp,String antigen,String insertedby)
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
            this.context=context;


            // progress=new ProgressDialog(context);
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






        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("In preexecution  azam", "Preexecution 1");


        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
           // pb.setProgress(values[0]);

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

            /*if(progress.isShowing())
            {
                Log.e("In onPost  azam", "Showing 2");
            }*/
            //progress.dismiss();


        }


    }
}
