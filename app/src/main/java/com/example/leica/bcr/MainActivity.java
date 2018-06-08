package com.example.leica.bcr;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity {
    MyOpenHelper helper;
    SQLiteDatabase db;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if( !profileExist() ) {                                       // make profile first
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivityForResult(intent, 0);
        }

        helper = new MyOpenHelper(this);
        db = helper.getWritableDatabase();

        showDB();

    }

    public void showDB(){
        final List<Map<String, String>> dataList = new ArrayList<>();

        // load from SQL

        Cursor c = db.query("person",
                new String[] { "name", "sub", "sex", "company", "department", "post", "tell", "mobile", "fax", "mail", "url", "tags" },
                null, null, null, null, null
        ); // query


        boolean mov = c.moveToFirst();

        while (mov) {
            Map<String, String> data = new HashMap<>();
            data.put("name", c.getString(0));
            data.put("sub",  c.getString(1));
            data.put("sex",  c.getString(2));
            data.put("company", c.getString(3));
            data.put("department", c.getString(4));
            data.put("post", c.getString(5));
            data.put("tell", c.getString(6));
            data.put("mobile", c.getString(7));
            data.put("fax", c.getString(8));
            data.put("mail", c.getString(9));
            data.put("url", c.getString(10));
            data.put("tags", c.getString(11));

            dataList.add(data);

            mov = c.moveToNext();
        }

        c.close();

        /*
        for (int i = 0; i < 5; i++) {
            Map<String, String> data2 = new HashMap<>();
            data2.put("name", "氏名 " + i);
            data2.put("sub",  "フリガナ " + i);
            data2.put("sex",  "male");
            data2.put("company", "company" + i);
            data2.put("department", "department" + i);
            data2.put("post", "post" + i);
            data2.put("tell", "03-0000-00" + i);
            data2.put("mobile", "090-0000-00" + i);
            data2.put("fax", "03-0000-00" + i);
            data2.put("mail", "example" + i + "@mail.com");
            data2.put("url", "http://example" + i + ".com");
            data2.put("tags", "+++.---.lllll.OO." + i);
            dataList.add(data2);
        }
        */

        MySimpleAdapter adapter = new MySimpleAdapter(
                this,
                dataList,
                R.layout.row,
                new String[]{"name", "sub", "sex", "company", "department", "post", "tell", "mobile", "fax", "mail", "url", "tags"},
                new int[]{ R.id.nameText1, R.id.nameText2 }
        );

        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("list profile: ", "" + position);

                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);

                intent.putExtra("name", dataList.get(position).get("name") );
                intent.putExtra("sub", dataList.get(position).get("sub") );
                intent.putExtra("sex", dataList.get(position).get("sex") );
                intent.putExtra("company", dataList.get(position).get("company") );
                intent.putExtra("department", dataList.get(position).get("department") );
                intent.putExtra("post", dataList.get(position).get("post") );
                intent.putExtra("tell", dataList.get(position).get("tell") );
                intent.putExtra("mobile", dataList.get(position).get("mobile") );
                intent.putExtra("fax", dataList.get(position).get("fax") );
                intent.putExtra("mail", dataList.get(position).get("mail") );
                intent.putExtra("url", dataList.get(position).get("url") );
                intent.putExtra("tags", dataList.get(position).get("tags") );

                startActivityForResult(intent, 0);
                overridePendingTransition(0, 0);
            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();

       showDB();
    }


    public boolean profileExist(){  // load = check existence
        try {
            FileInputStream input = this.openFileInput("profile.txt");

            BufferedReader reader = new BufferedReader( new InputStreamReader( input ) );
            StringBuffer strBuffer = new StringBuffer();
            String line;

            while ( (line = reader.readLine() ) != null) {
                strBuffer.append(line);
            }
            reader.close();

            Log.v("txt file", "profile exist!!");
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.v("txt file", "not found");
            return false;

        } catch (IOException e) {
            e.printStackTrace();
            Log.v("txt file", "IOException");
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_settings){
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivityForResult(intent, 0);
            Log.v("jump", "setting");

        } else if (id == R.id.action_sort){
            Log.v("jump", "sort");

        } else if (id == R.id.search){
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivityForResult(intent, 0);
            overridePendingTransition(0, 0);
            Log.v("jump", "search");

        } else if (id == R.id.exchange){
            Intent intent = new Intent(MainActivity.this, ExchangeActivity.class);
            startActivityForResult(intent, 0);
            overridePendingTransition(0, 0);
            Log.v("jump", "exchange");
        }
        return true;
    }

}