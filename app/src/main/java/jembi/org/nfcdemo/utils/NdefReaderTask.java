package jembi.org.nfcdemo.utils;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import jembi.org.nfcdemo.NFCDemoApplication;

/**
 * From https://github.com/tumbledwyer/NearFieldHealth/tree/master/app/src/main/java/org/jembi/nearFieldHealth/nfcUtils
 * @author tumbledwyer
 */
class NdefReaderTask extends AsyncTask<Tag, Void, Byte[]> {

    private NfcReadCallback callback;

    NdefReaderTask(NfcReadCallback callback){
        this.callback = callback;
    }

    @Override
    protected Byte[] doInBackground(Tag... params) {
        Tag tag = params[0];

        Ndef ndef = Ndef.get(tag);
        if (ndef == null) {
            return null;
        }

        NdefMessage ndefMessage = ndef.getCachedNdefMessage();

        NdefRecord[] records = ndefMessage.getRecords();
        for (NdefRecord ndefRecord : records) {
            if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                try {
                    return readBytes(ndefRecord);
                } catch (UnsupportedEncodingException e) {
                    Log.e(NFCDemoApplication.LOG_TAG, "Unsupported Encoding", e);
                }
            }
        }

        return null;
    }

    private String readText(NdefRecord record) throws UnsupportedEncodingException {
        /*
         * See NFC forum specification for "Text Record Type Definition" at 3.2.1
         *
         * http://www.nfc-forum.org/specs/
         *
         * bit_7 defines encoding
         * bit_6 reserved for future use, must be 0
         * bit_5..0 length of IANA language code
         */

        byte[] payload = record.getPayload();

        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";

        int languageCodeLength = payload[0] & 0063;

        // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
        // e.g. "en"

        Log.v(NFCDemoApplication.LOG_TAG, "TNF: " + record.getTnf());
        Log.v(NFCDemoApplication.LOG_TAG, "Type: " + record.getType());
        Log.v(NFCDemoApplication.LOG_TAG, "MimeType: " + record.toMimeType());
        Log.v(NFCDemoApplication.LOG_TAG, "Contents: " + + record.describeContents());
        Log.v(NFCDemoApplication.LOG_TAG, "textEncoding: " + textEncoding);
        Log.v(NFCDemoApplication.LOG_TAG, "languageCodeLength: " + languageCodeLength);
        Log.v(NFCDemoApplication.LOG_TAG, "payload: " + new String(payload, textEncoding));
        Log.v(NFCDemoApplication.LOG_TAG, "payload.length: " + payload.length);

        if (payload.length < languageCodeLength) {
            Log.e(NFCDemoApplication.LOG_TAG, "Issue with determining the language code");
            return new String(payload, textEncoding);
        }

        return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
    }

    private Byte[] readBytes(NdefRecord record) throws UnsupportedEncodingException {
        /*
         * See NFC forum specification for "Text Record Type Definition" at 3.2.1
         *
         * http://www.nfc-forum.org/specs/
         *
         * bit_7 defines encoding
         * bit_6 reserved for future use, must be 0
         * bit_5..0 length of IANA language code
         */
        byte[] payload = record.getPayload();
        int languageCodeLength = payload[0] & 0063;
        int startIndex = 1 + languageCodeLength; // discard the first byte and the language code
        Byte[] bytes = new Byte[0];
        if (startIndex < payload.length) {
            bytes = new Byte[payload.length - startIndex];
            for (int i = startIndex; i < payload.length; i++) {
                bytes[i - startIndex] = payload[i];
            }
        }
        return bytes;
    }

    @Override
    protected void onPostExecute(Byte[] result) {
        if (result != null) {
            try {
                callback.onReadComplete(result);
            } catch (IOException e) {
                Log.e(NFCDemoApplication.LOG_TAG, "Exception thrown while reading NFC card", e);
            }
        }
    }
}
