package com.bhargain.azam.bb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.bhargain.azam.bb.R;

/**
 * Created by Azam on 10/11/2015.
 */
public class DBAdapter {


    MyDB helper;
    Context context;
    public DBAdapter(Context context) {
        // TODO Auto-generated constructor stub
        this.context=context;
        helper=new MyDB(context);

    }


    public long insert(String name,String fathername,String mohalla,String city,String phone,String bldgrp,String antigen)
    {
        SQLiteDatabase db=helper.getWritableDatabase();

        ContentValues content=new ContentValues();
        content.put(MyDB.col2, name);
        content.put(MyDB.col3, fathername);
        content.put(MyDB.col4, mohalla);
        content.put(MyDB.col5, city);
        content.put(MyDB.col6, phone);
        content.put(MyDB.col7, bldgrp);
        content.put(MyDB.col8, antigen);

        long id=db.insert(MyDB.tablename, null, content);

        return id;
    }


    public String getAll()
    {
        SQLiteDatabase db=helper.getWritableDatabase();
        Toast.makeText(context, "Hello", Toast.LENGTH_LONG).show();
        String[] columns={"antigen",helper.col2};
        Cursor cursor=db.query(helper.tablename, columns, null, null, null, null,null);
        StringBuffer bufferd=new StringBuffer();
        while(cursor.moveToNext())
        {
            int index1=cursor.getColumnIndex("antigen");
            int col1=cursor.getInt(index1);//index1=0

            String str=cursor.getString(1);
            bufferd.append(col1+" "+str+"\n");
        }

        return bufferd.toString();
    }

    public SimpleCursorAdapter getListToDisplay(String bldgrp,String antigen) {
        SQLiteDatabase sqlite = helper.getWritableDatabase();
        SimpleCursorAdapter simple = null;
        String[] columns = {"_id", "name", "fathername", "mohalla", "city", "phone","bldgrp","antigen"};
        Cursor cursor = sqlite.query(helper.tablename, columns, "bldgrp=? and antigen=?", new String[]{bldgrp,antigen}, null, null, null);

        while (cursor.moveToNext()) {

            String[] from = {"name", "fathername","mohalla","phone"};
            int[] to = {R.id.lvname, R.id.lvfathername,R.id.lvmohalla,R.id.lvphone};
            simple = new SimpleCursorAdapter(context, R.layout.listview, cursor, from, to);
        }

        // adapter = null;
        //cursor.close();
        //sqlite.close();
        return simple;
    }


    static class MyDB extends SQLiteOpenHelper
    {

        private static final String dbname="database";
        private static final String tablename="bloodbank";
        private static final int dbversion=1;
        private static final String col1="_id";
        private static final String col2="name";
        private static final String col3="fathername";
        private static final String col4="mohalla";
        private static final String col5="city";
        private static final String col6="phone";
        private static final String col7="bldgrp";
        private static final String col8="antigen";
        private static final String query="create table bloodbank (_id INTEGER primary key,name varchar(40), fathername varchar(40), mohalla varchar(40), city varchar(40),phone varchar(10)," +
                                            "bldgrp varchar(4),antigen varchar(4),insertedby varchar(20));";

        //"CREATE TABLE user (_id integer primary key,name varchar(20));";

        //
        private Context context;



        public MyDB(Context context) {

            super(context, dbname, null, dbversion);
            // TODO Auto-generated constructor stub
            this.context=context;
            //Toast.makeText(context, "Constructor called", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub


            try {
                db.execSQL(query);
                Toast.makeText(context, "Table Created", Toast.LENGTH_SHORT).show();
                Log.d("Azam","Table created");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                Toast.makeText(context, ""+e, Toast.LENGTH_LONG).show();
                Log.d("Azam DB ERROR",e+"");
                System.out.println(e);
                //Message.message(context,""+e);
            }


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub

        }
    }


}
