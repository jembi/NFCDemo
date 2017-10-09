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
 * Adapted from https://github.com/tumbledwyer/NearFieldHealth/tree/master/app/src/main/java/org/jembi/nearFieldHealth/nfcUtils
 * @author tumbledwyer
 */
class NdefWriterTask extends AsyncTask<String, Void, Void> {

    public static final String LANGUAGE_CODE = "en";
    public static final String ENCODING = "UTF-8";

    private NfcWriteCallback callback;
    private Tag myTag;

    NdefWriterTask(Tag myTag, NfcWriteCallback callback) {
        this.myTag = myTag;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(String... data) {
        try {
            write(myTag, data[0]);
        } catch (Throwable e) {
            callback.onWriteError(e);
        }
        return null;
    }

    private void write(Tag myTag, String data) throws IOException, FormatException {
        // Create the Ndef Record
        NdefRecord[] records = {createRecord(data)};
        NdefMessage message = new NdefMessage(records);
        // Get an instance of Ndef for the tag.
        Ndef ndef = Ndef.get(myTag);
        // Enable I/O
        ndef.connect();
        // Write the message
        ndef.writeNdefMessage(message);
        // Close the connection
        ndef.close();
    }

    private NdefRecord createRecord(String data) throws UnsupportedEncodingException {

        byte[] langBytes = LANGUAGE_CODE.getBytes(ENCODING);
        int langLength = langBytes.length;

        byte[] textBytes = data.getBytes();
        int textLength = textBytes.length;

        byte[] payload = new byte[1 + langLength + textLength];

        // set status byte (see NDEF spec for actual bits)
        payload[0] = (byte) langLength;

        // copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);
    }

    @Override
    protected void onPostExecute(Void returnValue) {
        callback.onWriteComplete();
    }
}
