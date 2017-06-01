package com.example.jinhwan.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Main2Activity extends AppCompatActivity {

    EditText e1;
    TextView t1;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        e1=(EditText)findViewById(R.id.editText1);
        t1=(TextView)findViewById(R.id.textView);
        b1=(Button)findViewById(R.id.button);
    }

    String urlstr;
    public void onClick(View v){
        urlstr = e1.getText().toString();
        myThread.start();
    }
    Handler myHandler = new Handler();
    Thread myThread = new Thread(){
        @Override
        public void run() {
            URL url = null;
            try {
                url = new URL(urlstr);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    final String data = readData(urlConnection.getInputStream());
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            t1.setText(data);
                        }
                    });
                    urlConnection.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };
    String readData(InputStream is){
        String data="";
        Scanner s = new Scanner(is);
        while(s.hasNext())
            data+=s.nextLine()+ "\n";
        s.close();
        return data;
    }
}
