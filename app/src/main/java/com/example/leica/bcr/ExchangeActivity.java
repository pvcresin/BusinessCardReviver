package com.example.leica.bcr;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


public class ExchangeActivity extends Activity {
    MyOpenHelper helper;
    SQLiteDatabase db;
    String strAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        setTitle("Exchange");

        helper = new MyOpenHelper(this);
        db = helper.getWritableDatabase();


        loadProfileText();

        // NFC

        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter == null){                                                        // nfc check
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        nfcAdapter.setNdefPushMessageCallback(
                new NfcAdapter.CreateNdefMessageCallback() {
                    public NdefMessage createNdefMessage(NfcEvent event) {
                        return createMessage();
                    }
                },
                this
        );
    }


    private NdefMessage createMessage() {
        String mimeType = "application/com.example.leica.bcr";

        byte[] mimeBytes = mimeType.getBytes( Charset.forName("UTF-8") );

        byte[] payLoad = strAll.getBytes();                    //generate payload (data body)

        return new NdefMessage(                                                     //generate nfc message
                new NdefRecord[]{
                        new NdefRecord( NdefRecord.TNF_MIME_MEDIA, mimeBytes, null, payLoad)
                        ,NdefRecord.createApplicationRecord("com.example.leica.bcr")
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        if ( NfcAdapter.ACTION_NDEF_DISCOVERED.equals( getIntent().getAction() ) ) {        //received message

            Parcelable[] messages = getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            NdefMessage message = (NdefMessage) messages[0];
            NdefRecord record = message.getRecords()[0];

            String payload = new String( record.getPayload() );

            String data[] = payload.split(",");

            ContentValues insertValues = new ContentValues();

            insertValues.put("name", data[0]);
            insertValues.put("sub", data[1]);
            insertValues.put("sex", data[2]);
            insertValues.put("company", data[3]);
            insertValues.put("department", data[4]);
            insertValues.put("post", data[5]);
            insertValues.put("tell", data[6]);
            insertValues.put("mobile", data[7]);
            insertValues.put("fax", data[8]);
            insertValues.put("mail", data[9]);
            insertValues.put("url", data[10]);
            insertValues.put("tags", data[11]);

            db.insert("person", "no data", insertValues);

            Toast.makeText(ExchangeActivity.this, "new card", Toast.LENGTH_SHORT).show();

            finish();
        }
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


            ListView EListPro = (ListView) findViewById(R.id.ElistProfile);

            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

            adapter.addAll(text);

            EListPro.setAdapter(adapter);


            Log.v("txt file", "loaded");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.v("txt file", "not found");

        } catch (IOException e) {
            e.printStackTrace();
            Log.v("txt file", "IOException");
        }
    }

}
