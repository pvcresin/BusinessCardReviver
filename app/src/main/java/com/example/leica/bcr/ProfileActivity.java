package com.example.leica.bcr;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class ProfileActivity extends Activity {
    Activity activity = this;

    String name, sub, sex, company, department, post, tell, mobile, fax, mail, url, tags;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        sub = intent.getStringExtra("sub");
        sex = intent.getStringExtra("sex");
        company = intent.getStringExtra("company");
        department = intent.getStringExtra("department");
        post = intent.getStringExtra("post");
        tell = intent.getStringExtra("tell");
        mobile = intent.getStringExtra("mobile");
        fax = intent.getStringExtra("fax");
        mail = intent.getStringExtra("mail");
        url = intent.getStringExtra("url");
        tags = intent.getStringExtra("tags");

        ListView ListPro = (ListView) findViewById(R.id.listProfile);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        adapter.addAll(name, sub, sex, company, department, post, tell, mobile, fax, mail, url, tags);

        ListPro.setAdapter(adapter);

        ListPro.setOnItemClickListener( new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("list profile", position + " " + adapter.getItem(position) );
            }
        });

    }

    public void button_onClick(View view){
        int id = view.getId();

        if (id == R.id.cardImage) {
            Intent intent = new Intent(this, CardActivity.class);
            intent.putExtra("id", 1);
            startActivityForResult(intent, 0);
            overridePendingTransition(0, 0);

        } else if (id == R.id.editButton ){
            Toast.makeText(activity, "edit", Toast.LENGTH_SHORT).show();

        } else if ( id == R.id.tagPlusButton ){
            Toast.makeText(activity, "tag plus", Toast.LENGTH_SHORT).show();
        }
    }

}
