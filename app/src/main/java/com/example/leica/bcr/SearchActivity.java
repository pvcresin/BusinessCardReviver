package com.example.leica.bcr;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;


public class SearchActivity extends Activity {
    MyOpenHelper helper;
    SQLiteDatabase db;

    SearchView searchView;
    String searchWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("BCR");

        helper = new MyOpenHelper(this);
        db = helper.getWritableDatabase();
    }

    // search box

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.searchWord);

        searchView = (SearchView)menuItem.getActionView();

        searchView.setIconifiedByDefault(true);                 // 虫眼鏡アイコンを最初表示するかの設定
        searchView.setSubmitButtonEnabled(false);               // Submitボタンを表示するかどうか

        if (searchWord != "") {
            searchView.setQuery(searchWord, false);             // TextView.setTextみたいなもの
        } else {
            searchView.setQueryHint("hint");                         // placeholderみたいなもの
        }

        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchWord) {            // Submit or EnterKey を押されたら呼び出されるメソッド
                return setSearchWord(searchWord);
            }

            @Override
            public boolean onQueryTextChange(String newText) {            // 入力される度に呼び出される
                return false;
            }
        });

        return true;
    }

    private boolean setSearchWord(String sWord) {
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(sWord);
        actionBar.setDisplayShowTitleEnabled(true);

        if (sWord != null && !sWord.equals("")) {            // searchWordがあることを確認
            searchWord = sWord;
        }

        searchView.setIconified(false);              // 虫眼鏡アイコンを隠す
        searchView.onActionViewCollapsed();          // SearchViewを隠す
        searchView.clearFocus();                     // Focusを外す


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        Cursor c = db.rawQuery("SELECT name FROM person WHERE name LIKE ?;", new String[]{ "%" + searchWord + "%" });

        boolean mov = c.moveToFirst();

        while (mov) {
            adapter.add( c.getString(0) );

            mov = c.moveToNext();
        }

        ListView listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(adapter);

        c.close();

        return false;
    }

}
