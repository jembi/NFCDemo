package jembi.org.nfcdemo.utils;

/**
 * Created by Jembi Health Systems on 2017/10/09.
 */

public interface NfcFormatterCallback {

    void onFormatComplete(Boolean success);
    void onFormatError(Throwable ex);
}
