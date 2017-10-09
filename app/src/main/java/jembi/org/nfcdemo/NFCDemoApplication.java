package jembi.org.nfcdemo;

import android.app.Application;

/**
 * NFC App main class. Defined in the AndroidManifest and is a singleton which should be used
 * only in very specific scenarios to access thread-safe data that should be available to any
 * code within the app.
 */
public class NFCDemoApplication extends Application {
    public static final String LOG_TAG = "NFCDemo";
    public static final String TEST_DATA = "This is some test data";
}
