package com.example.androidthreadexam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

//        안드로이드 Thread
//
//        1. Thread 클래스를 extends 한 클래스 생성 -> run() 메서드를 override -> 클래스 instance 생성 -> start()메서드 호출
//        2. UI에 대한 접근은 오직 메인 스레드(함수) 만이 권한을 가지고 있으며, 그 외의 스레드에서 UI에 대해 접근할 시 에러 발생함
//        3. 서브 스레드에서 일정한 시간마다 스레드를 run을 통해 변경된 값을 메인 스레드에 전달시켜주고 메인스레드에서 UI접근하는게 바람직한 방법임.
//        4. Handler를 사용하여 메인 스레드로 메시지를 보낼 수 있다.

public class MainActivity extends AppCompatActivity {


    TextView clockTextView ;

    //private static Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clockTextView = findViewById(R.id.clock) ;

//        //mHandler = new Handler(){
//            @Override
//            public void handleMessage(Message msg){
//                Calendar cal = Calendar.getInstance();
//
//                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//                String strTime = sdf.format(cal.getTime());
//
//                clockTextView = findViewById(R.id.clock);
//                clockTextView.setText(strTime);
//            }
//        };
        // 핸들러로 전달하듯이 Runnable 객체를 생성해서 UI에 접근할 수 있는 코드 작성 -> 메인에 전달
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Calendar cal = Calendar.getInstance();

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String strTime = sdf.format(cal.getTime());

                clockTextView = findViewById(R.id.clock);
                clockTextView.setText(strTime);
            }
        };

        class NewRunnable implements Runnable {

            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    // Main Method 에 전달할 수 있게 Activity에 기본적으로 정의 되어 있는 메서드
                    runOnUiThread(runnable);
                }
            }
        }

        NewRunnable nr = new NewRunnable();
        Thread t = new Thread(nr);
        t.start();
    }
}
