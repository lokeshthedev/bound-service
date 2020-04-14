package com.example.bindedservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MyService myService;
    boolean flag = false;

    String s = "Messages from activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(s,"Activity is created");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(s,"Activity is started");
        Intent in = new Intent(getApplicationContext(),MyService.class);
        bindService(in,con,Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(s,"Activity is paused");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(s,"Activity is resumed");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(s,"Activity is restarted");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(s,"Activity is stopped");
        unbindService(con);
        flag = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(s,"Activity is destroyed");
    }

    public void onButtonClick(View v){
        Log.d(s,"Button is clicked");
//        String name = myService.askName();
//        Log.d(s,name);
//        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        EditText textBox = (EditText) findViewById(R.id.editText);
        String text = textBox.getText().toString();
        if(text.equals("")){
            Log.d(s,"Please enter a valid number");
            Toast.makeText(this,"Please enter a number",Toast.LENGTH_SHORT).show();
        }else{
            int value = 0;
            value = Integer.parseInt(text);
            Intent intent = new Intent(this,MyService.class);
            intent.putExtra("Number",value);
            int result = myService.calculate(intent);
            Log.d(s, String.valueOf(result));
            Toast.makeText(this,"The square is: "+result,Toast.LENGTH_SHORT).show();
        }
    }

    private ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyBinder binder = (MyService.MyBinder) service;
            myService = binder.getServiceRef();
            flag = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            flag = false;
        }
    };

}
