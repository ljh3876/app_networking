package com.example.jinhwan.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        if(v.getId() == R.id.btn1){
            Intent intent = new Intent(this,Main2Activity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.btn2){
            Intent intent = new Intent(this,Main3Activity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.btn3){
            Intent intent = new Intent(this,Main4Activity.class);
            startActivity(intent);
        }

    }
}