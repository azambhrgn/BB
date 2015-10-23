package com.bhargain.azam.bb;


import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bhargain.azam.bb.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Azam on 10/11/2015.
 */
public class AddEntry extends ActionBarActivity {

    private Button btncancel, btnsave;
    private EditText etname, etfathername, etmohalla, etphone;
    private Spinner spnbldgrp, spnantigen;
    private String status,name,fathername,mohalla,city,phone,bldgrp,antigen,insertedby;

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

                mdb = new DBAdapter(v.getContext());


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
                    JSONObject jsonobj = new AsyncTaskJsonParse(status, name, fathername, mohalla, city, phone, bldgrp, antigen, insertedby).execute().get();

                    //src=jsonobj.getJSONObject("data");
                    if (jsonobj.getString("success") == "1") {
                        Toast.makeText(v.getContext(), jsonobj.getString("message"), Toast.LENGTH_LONG).show();
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
}
