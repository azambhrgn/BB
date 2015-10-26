package com.bhargain.azam.bb;


import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.Spinner;
import android.widget.TextView;




public class MainActivity extends ActionBarActivity {

    private Button btnsearch,btnadd,btninfo;
    private DBAdapter mydb;
    private Spinner spnrbldgrp,spnrantigen;
    private String city="Vadodara";
    private TextView companytitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnadd=(Button) findViewById(R.id.btnadd);
        btnsearch= (Button) findViewById(R.id.btnsearch);
        btninfo= (Button) findViewById(R.id.btninfo);
        btnsearch.setEnabled(true);

        companytitle= (TextView) findViewById(R.id.companytitle);
        //companytitle.setTextColor(Color.GREEN);

            mydb=new DBAdapter(this);

        btnadd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent("com.bhargain.azam.bb.AddEntry");
                intent.putExtra("Hello", 500);
                startActivityForResult(intent, 100);
            }
        });

        btninfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent("com.bhargain.azam.bb.BloodInfo");
                intent.putExtra("Hello", 500);
                startActivityForResult(intent, 100);
            }
        });

        btnsearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String bgrp,antign;
               // String str=mydb.getAll();
                //Toast.makeText(v.getContext(), str, Toast.LENGTH_LONG).show();
                String status="FB";

                btnsearch.setEnabled(false);
                spnrbldgrp= (Spinner) findViewById(R.id.bldgrp);
                spnrantigen= (Spinner) findViewById(R.id.antigen);

                bgrp=spnrbldgrp.getSelectedItem().toString();
                antign=spnrantigen.getSelectedItem().toString();
                if(antign.equals("-"))
                {
                    antign="0";
                }
                else if(antign.equals("+"))
                {
                    antign="1";
                }



                Intent intent = new Intent("com.bhargain.azam.bb.ListViewDisplay");
                intent.putExtra("bldgrp", bgrp);
                intent.putExtra("antigen", antign);
                startActivityForResult(intent, 100);



            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("On resume","Azam");
        btnsearch.setEnabled(true);
    }
}
