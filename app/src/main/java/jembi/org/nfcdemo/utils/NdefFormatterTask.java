package jembi.org.nfcdemo.utils;

import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Jembi Health Systems on 2017/10/09.
 */

public class NdefFormatterTask extends AsyncTask<Void, Void, Void> {

    private Tag myTag;
    private NfcFormatterCallback callback;

    public NdefFormatterTask(Tag myTag, NfcFormatterCallback callback)  {
        this.myTag = myTag;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Ndef tag = Ndef.get(myTag);
        try {
            connectTag(tag);
            tag.writeNdefMessage(createRecord());
        } catch (Exception ex) {
            callback.onFormatError(ex);
        } finally {
            disconnectTag(tag);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        callback.onFormatComplete();
    }

    private NdefMessage createRecord() throws UnsupportedEncodingException {

        final String lang = "en";
        byte[] textBytes = new byte[0];
        byte[] langBytes = lang.getBytes("UTF-8");
        int langLength = langBytes.length;
        int textLength = textBytes.length;
        final byte[] payload = new byte[1 + langLength + textLength];

        return new NdefMessage(new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload));
    }

    private void connectTag(Ndef tag) throws IOException {
        if (!tag.isConnected()) {
            tag.connect();
        }
    }

    private void disconnectTag(Ndef tag) {
        try {
            if (tag.isConnected()) {
                tag.close();
            }
        } catch (IOException e) {
            // if an exception is thrown while closing a resource, it is OK to ignore it
        }
    }
}
