package com.example.bindedservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.inputmethod.BaseInputConnection;

import java.util.Objects;

public class MyService extends Service {
    private final IBinder binder = new MyBinder();

    String s = "Messages from Service";

    public class MyBinder extends Binder{
        public MyService getServiceRef(){
            Log.d(s,"Returning a reference of service class");
            return MyService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(s,"Service is created");

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(s,"Service is bound and binder is returned");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(s,"Service is Unbound");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(s,"Service is destroyed");
    }

    public String askName(){
        Log.d(s,"Service function is called from the activity");
        return "My name is Alice";
    }

    public int calculate(Intent intent){
        Log.d(s,"Calculate function is running");
        int num = intent.getIntExtra("Number",0);
        Log.d(s, String.valueOf(num));
        return num*num;
    }
}
