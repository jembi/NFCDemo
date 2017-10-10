package jembi.org.nfcdemo.utils;

import android.content.Context;
import android.nfc.NdefMessage;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import jembi.org.nfcdemo.NFCDemoApplication;
import jembi.org.nfcdemo.R;

/**
 * Created by Jembi Health Systems on 2017/10/09.
 */

public class NfcFormatter {

    private Context context;
    private Tag myTag;

    public NfcFormatter(Context context, Tag myTag) {
        this.context = context;
        this.myTag = myTag;
    }

    public void tryFormat() {
        if (myTag == null) {
            Toast.makeText(context, R.string.no_nfc_tag, Toast.LENGTH_LONG).show();
        } else new NdefFormatterTask(myTag, new NfcFormatterCallback() {
            @Override
            public void onFormatComplete() {
                Toast.makeText(context, R.string.tag_format_successful, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFormatError(Throwable ex) {
                Toast.makeText(context, R.string.error_format, Toast.LENGTH_LONG).show();
                Log.e(NFCDemoApplication.LOG_TAG, "Error occurred while formatting NFC card", ex);
            }
        }).execute();
    }

}
