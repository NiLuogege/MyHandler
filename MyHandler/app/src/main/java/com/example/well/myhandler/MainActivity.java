package com.example.well.myhandler;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化looper
        Looper.prepare();

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                super.handleMessage(message);
                Log.e("收消息", "what= " + message.toString());
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    while (true) {
                        Message message = new Message();
                        message.what = 1;
                        synchronized (UUID.class) {
                            message.obj = Thread.currentThread().getName() + ",send Message:" + UUID.randomUUID().toString();
                        }
                        Log.e("发消息", message.toString());
                        handler.sendMasage(message);

                        SystemClock.sleep(1000);
                    }
                }
            }.start();

        }


        //开始轮询
        Looper.loop();

    }
}
