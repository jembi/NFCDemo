package jembi.org.nfcdemo.utils;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import jembi.org.nfcdemo.NFCDemoApplication;
import jembi.org.nfcdemo.R;

/**
 * Adapted from https://github.com/tumbledwyer/NearFieldHealth/tree/master/app/src/main/java/org/jembi/nearFieldHealth/nfcUtils
 *
 * @author tumbledwyer
 */
public class NfcWriter {

    private Context context;
    private Tag myTag;

    public NfcWriter(Tag myTag, Context context) {
        this.myTag = myTag;
        this.context = context;
    }

    public void tryWrite(String data) {
        if (myTag == null) {
            Toast.makeText(context, R.string.no_nfc_tag, Toast.LENGTH_LONG).show();
        } else {
            new NdefWriterTask(myTag, new NfcWriteCallback() {

                @Override
                public void onWriteComplete() {
                    Toast.makeText(context, R.string.message_write_successful, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onWriteError(Throwable e) {
                    Toast.makeText(context, R.string.error_write, Toast.LENGTH_LONG).show();
                    Log.e(NFCDemoApplication.LOG_TAG, "Error occurred while writing NFC card", e);
                }
            }).execute(data);
        }
    }
}
