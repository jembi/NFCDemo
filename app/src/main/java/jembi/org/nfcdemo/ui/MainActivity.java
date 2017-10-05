package jembi.org.nfcdemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import jembi.org.nfcdemo.NFCDemoApplication;
import jembi.org.nfcdemo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // general setup
        setupCrashHandler();

        // setup for the buttons
        setupButton1Action();
    }

    private void setupButton1Action() {
        Button btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // do something
            }
        });
    }

    private void setupCrashHandler() {
        // set up crash handler to log unhandled exceptions
        // Note: in a production app, this should be a service like Crashalytics which will
        // allow you to monitor exceptions thrown on client devices
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Log.e(NFCDemoApplication.LOG_TAG, "App crashed!! Exception:", ex);
            }
        });
    }
}
