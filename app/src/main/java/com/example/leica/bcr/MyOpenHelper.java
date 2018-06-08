package com.example.leica.bcr;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class MyOpenHelper extends SQLiteOpenHelper {   // name sub sex company department position tell mobile fax mail url tags

    public MyOpenHelper(Context context) {
        super(context, "BCRdb", null, 1);
        Log.v("MyOpenHelper", "create");
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        String strSQL = "create table person( ";
        strSQL += "name text , sub text, sex text, company text, department text, post text, ";
        strSQL += "tell text, mobile text, fax text, mail text, url text , tags text);";
        DB.execSQL( strSQL );
        Log.v("MyOpenHelper", "schema");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {  }
}
