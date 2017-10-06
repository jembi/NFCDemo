package jembi.org.nfcdemo.ui;

import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import jembi.org.nfcdemo.NFCDemoApplication;
import jembi.org.nfcdemo.R;
import jembi.org.nfcdemo.utils.NfcReadCallback;
import jembi.org.nfcdemo.utils.NfcReader;

public class MainActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private NfcReader nfcReader;
    private Tag myTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // NFC setup
        setupNFC();

        // general setup
        setupCrashHandler();

        // setup for the buttons
        setupButton1Action();

        setupFormatAction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**
         * It's important, that the activity is in the foreground (resumed). Otherwise
         * an IllegalStateException is thrown.
         */
        if (nfcAdapter != null) {
            nfcReader.setupForegroundDispatch(this, nfcAdapter);
        }
    }

    @Override
    protected void onPause() {
        /**
         * Call this before onPause, otherwise an IllegalArgumentException is thrown as well.
         */
        if(nfcAdapter != null) {
            nfcReader.stopForegroundDispatch(this, nfcAdapter);
        }
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        //if(!NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) return;

        myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        setInfoText(R.string.info_reading);
        nfcReader.handleIntent(intent);
    }

    private void setupNFC() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            // Stop here, we definitely need NFC
            setInfoText(R.string.no_nfc_support);
            return;
        } else {
            if (!nfcAdapter.isEnabled()) {
                setInfoText(R.string.info_disabled);
            } else {
                setInfoText(R.string.info_waiting);
            }
        }

        nfcReader = new NfcReader(new NfcReadCallback() {
            @Override
            public void onReadComplete(String data) throws IOException {
                Toast.makeText(getApplicationContext(), "Finished reading card", Toast.LENGTH_LONG).show();
                Log.i(NFCDemoApplication.LOG_TAG, "The data is " + data);
                setInfoText(R.string.info_waiting);
            }
        });
        nfcReader.handleIntent(getIntent());
    }

    private void setInfoText(int text) {
        TextView infoText = (TextView)findViewById(R.id.infoText);
        infoText.setText(text);
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

    private void setupFormatAction() {
        Button formatButton = (Button) findViewById(R.id.formatButton);
        formatButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Ndef tag = Ndef.get(myTag);
                try {
                    /*since we are formatting the tag, we assume it is already prepared to store data
                    so we need only overwrite */
                    if(!tag.isConnected()) {
                        tag.connect();
                        tag.writeNdefMessage(new NdefMessage(new NdefRecord(NdefRecord.TNF_EMPTY, new byte[0], new byte[0], new byte[0])));
                        Toast.makeText(getApplicationContext(), "Tag has been formatted", Toast.LENGTH_LONG);
                    }
                } catch (IOException | FormatException ex) {
                    setupCrashHandler();
                }
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
