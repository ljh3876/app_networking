package com.example.jinhwan.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class Main4Activity extends AppCompatActivity {
    TextView msg;
    EditText id,pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        msg = (TextView)findViewById(R.id.msg);
        id = (EditText)findViewById(R.id.editID);
        pw = (EditText)findViewById(R.id.editPW);
    }
    public void onClick(View v){
        userid = id.getText().toString();
        password = pw.getText().toString();
        int length=userid.length() + password.length();
        System.out.println(userid + ":" + password + "  " + length);
        if(userid.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();

        }
        myThread.start();
    }
    String urlstr="http://jerry1004.dothome.co.kr/info/login.php";
    String userid;
    String password;
    Handler myHandler = new Handler();
    Thread myThread = new Thread() {
        @Override
        public void run() {
            try {

                URL url = new URL(urlstr);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                String postData = "userid=" + URLEncoder.encode(userid) + "&password=" + URLEncoder.encode(password);

                OutputStream outputStream = urlConnection.getOutputStream();
                outputStream.write(postData.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                InputStream inputStream;
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                    inputStream = urlConnection.getInputStream();
                else
                    inputStream = urlConnection.getErrorStream();
                final String result = loginResult(inputStream);
                myHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        if(result.equals("FAIL"))
                            msg.setText("로그인이 실패했습니다.");
                        else
                            msg.setText(result + "님 로그인 성공");
                    }
                });

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    private String loginResult(InputStream is){
        String data="";
        Scanner s = new Scanner(is);
        data+=s.nextLine();
        s.close();
        return data;

    }
}
