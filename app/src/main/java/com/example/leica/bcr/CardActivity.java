package com.example.leica.bcr;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class CardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);


        ActionBar actionBar = getActionBar();
        actionBar.hide();
    }

    int n = 0;
    public void cardRotate(View view){
        ImageView cardView = (ImageView)findViewById(R.id.cardView);

        n = 1 - n;

        if(n == 0){
            cardView.setImageResource(R.drawable.card);
        } else {
            cardView.setImageResource(R.drawable.card2);
        }
    }

}
