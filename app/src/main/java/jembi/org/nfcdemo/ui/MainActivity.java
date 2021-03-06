package jembi.org.nfcdemo.ui;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import jembi.org.nfcdemo.NFCDemoApplication;
import jembi.org.nfcdemo.R;
import jembi.org.nfcdemo.builders.ImmunizationBuilder;
import jembi.org.nfcdemo.builders.PatientBuilder;
import jembi.org.nfcdemo.database.DatabaseOpenHelper;
import jembi.org.nfcdemo.database.DatabaseResult;
import jembi.org.nfcdemo.model.Gender;
import jembi.org.nfcdemo.model.Patient;
import jembi.org.nfcdemo.utils.NfcFormatter;
import jembi.org.nfcdemo.utils.NfcReadCallback;
import jembi.org.nfcdemo.utils.NfcReader;
import jembi.org.nfcdemo.utils.NfcWriter;

public class MainActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private NfcReader nfcReader;
    private Tag myTag;
    private DatabaseOpenHelper databaseOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseOpenHelper = new DatabaseOpenHelper(this);
        setContentView(R.layout.activity_main);

        // NFC setup
        setupNFC();

        // general setup
        setupCrashHandler();

        // setup for the buttons
        setupByteButtonAction();
        setupFormatAction();
        setupCreateButtonAction();
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
            public void onReadComplete(Byte[] data) throws IOException {
                Toast.makeText(getApplicationContext(), R.string.message_read_finished, Toast.LENGTH_LONG).show();
                Log.i(NFCDemoApplication.LOG_TAG, "The data is " + data);
                Log.i(NFCDemoApplication.LOG_TAG, "The number of bytes are " + data.length);
                setInfoText(R.string.info_waiting);

                setNfcText(data);

                Patient patient = Patient.readObject(data);
                Log.i(NFCDemoApplication.LOG_TAG, "Patient data is: " + patient);
                if (patient != null) {
                    setNfcText(patient.toString());
                }
            }
        });
        nfcReader.handleIntent(getIntent());
    }

    private void setInfoText(int text) {
        TextView infoText = (TextView)findViewById(R.id.infoText);
        infoText.setText(text);
    }

    private void setNfcText(Byte[] data) {
        TextView nfcTextDate = (TextView)findViewById(R.id.nfcTextDate);
        nfcTextDate.setText(DateFormat.getDateTimeInstance().format(new Date()));
        TextView nfcText = (TextView)findViewById(R.id.nfcText);
        // convert bytes to string
        byte[] bytes = new byte[data.length];
        int i = 0;
        for (Byte b : data) {
            bytes[i++] = b;
        }
        nfcText.setText(new String(bytes)); // FIXME: note this should include an encoding e.g. UTF-8
    }

    private void setNfcText(String data) {
        TextView nfcTextDate = (TextView)findViewById(R.id.nfcTextDate);
        nfcTextDate.setText(DateFormat.getDateTimeInstance().format(new Date()));
        TextView nfcText = (TextView)findViewById(R.id.nfcText);
        nfcText.setText(data);
    }

    private void setupByteButtonAction() {
        Button hexButton = (Button) findViewById(R.id.byteButton);
        hexButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Patient patient = new PatientBuilder()
                        .withDateOfBirth(new Date())
                        .withFirstName("Clara")
                        .withSurname("Tester")
                        .withGender(Gender.FEMALE)
                        .withImmunizations(
                                Arrays.asList(
                                        new ImmunizationBuilder()
                                                .withAdministrationDate(new Date())
                                                .withAdministrationLocation("Location")
                                                .withVaccinationCode("BCG01")
                                                .withVaccinationDose("100")
                                                .withVaccinationReason("Because")
                                                .build()))
                        .build();

                new NfcWriter(myTag, getApplicationContext()).tryWrite(Patient.writeObject(patient));
            }
        });
    }

    private void setupFormatAction() {
        Button formatButton = (Button) findViewById(R.id.formatButton);
        formatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NfcFormatter(getApplicationContext(), myTag).tryFormat();
            }
        });
    }

    private void setupCrashHandler() {
        // set up crash handler to log unhandled exceptions
        // Note: in a production app, this should be a service like Crashalytics whichr will
        // allow you to monitor exceptions thrown on client devices
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Log.e(NFCDemoApplication.LOG_TAG, "App crashed!! Exception:", ex);
            }
        });
    }


    private void setupCreateButtonAction() {
        Button createButton = (Button) findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Generating data", Toast.LENGTH_LONG).show();
                databaseOpenHelper.insertTestData(new DatabaseResult<Boolean>() {

                    @Override
                    public void processResult(Boolean result) {
                        if(result) {
                            Toast.makeText(getApplicationContext(), R.string.date_created_successful, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.error_data_create, Toast.LENGTH_LONG);
                        }
                    }
                });
            }
        });
    }
}
