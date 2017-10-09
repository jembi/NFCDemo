package jembi.org.nfcdemo.utils;


/**
 * Callback for the NdefWriterTask that allows for callers to display a success or error messaeg
 * (or continue processing) after writing to an NFC chip
 */
public interface NfcWriteCallback {
    void onWriteComplete(Boolean success);
    void onWriteError(Throwable e);
}
