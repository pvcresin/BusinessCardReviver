package com.example.leica.bcr;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


public class SettingActivity extends Activity {
    MyOpenHelper helper;
    SQLiteDatabase db;
    String strAll;

    EditText myName, mySub, mySex, myCompany, myDepartment, myPost, myTell, myMobile, myFax, myMail, myUrl, myTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("BCR");

        helper = new MyOpenHelper(this);
        db = helper.getWritableDatabase();

        myName = (EditText)findViewById(R.id.myNameEdit);
        mySub = (EditText)findViewById(R.id.mySubEdit);
        mySex  = (EditText)findViewById(R.id.mySexEdit);
        myCompany  = (EditText)findViewById(R.id.myCompanyEdit);
        myDepartment  = (EditText)findViewById(R.id.myDepartmentEdit);
        myPost  = (EditText)findViewById(R.id.myPostEdit);
        myTell  = (EditText)findViewById(R.id.myTellEdit);
        myMobile  = (EditText)findViewById(R.id.myMobileEdit);
        myFax  = (EditText)findViewById(R.id.myFaxEdit);
        myMail  = (EditText)findViewById(R.id.myMailEdit);
        myUrl  = (EditText)findViewById(R.id.myUrlEdit);
        myTags  = (EditText)findViewById(R.id.myTagsEdit);

        loadProfileText();
    }


    public void loadProfileText(){
        try {
            FileInputStream input = this.openFileInput("profile.txt");

            BufferedReader reader = new BufferedReader( new InputStreamReader( input ) );
            StringBuffer strBuffer = new StringBuffer();
            String line;

            while ( (line = reader.readLine() ) != null) {
                strBuffer.append(line);
            }
            reader.close();

            String textStr = strBuffer.toString();

            strAll = textStr;

            String[] text = textStr.split(",", 0);

            myName.setText( text[0] );
            mySub.setText( text[1] );
            mySex.setText( text[2] );
            myCompany.setText( text[3] );
            myDepartment.setText(text[4]);
            myPost.setText( text[5] );
            myTell.setText( text[6] );
            myMobile.setText( text[7] );
            myFax.setText( text[8] );
            myMail.setText( text[9] );
            myUrl.setText( text[10] );
            myTags.setText( text[11] );

            Log.v("txt file", "loaded");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.v("txt file", "not found");

        } catch (IOException e) {
            e.printStackTrace();
            Log.v("txt file", "IOException");
        }
    }

    public void saveProfile(){
        try {

            OutputStream out = openFileOutput("profile.txt", MODE_PRIVATE );                    //OutputStream取得
            PrintWriter writer = new PrintWriter( new OutputStreamWriter( out, "UTF-8" ) );

            String str = myName.getText().toString() + "," + mySub.getText().toString() + "," + mySex.getText().toString() + "," + myCompany.getText().toString() + ",";
            str += myDepartment.getText().toString() + "," + myPost.getText().toString() + "," + myTell.getText().toString() + "," + myMobile.getText().toString() + ",";
            str += myFax.getText().toString() + "," + myMail.getText().toString() + "," + myUrl.getText().toString() + "," + myTags.getText().toString();

            strAll = str;

            writer.append( str );                        // save
            writer.close();
            Log.v("txt file make", "saved");
            finish();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.v("txt file make", "not found");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.v("txt file make", "IO exception");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.setting_save) {
            saveProfile();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
