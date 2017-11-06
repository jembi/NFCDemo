package jembi.org.nfcdemo.utils;

import java.io.IOException;

/**
 * From https://github.com/tumbledwyer/NearFieldHealth/tree/master/app/src/main/java/org/jembi/nearFieldHealth/nfcUtils
 * (Although this has been renamed, and refactored a bit)
 * @author tumbledwyer
 */
public interface NfcReadCallback {
    void onReadComplete(Byte[] data) throws IOException;
}
