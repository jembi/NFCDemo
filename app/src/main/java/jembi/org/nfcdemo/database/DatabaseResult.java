package jembi.org.nfcdemo.database;

/**
 * Allows the Database to return data from an AsyncTask without blocking the main UI thread.
 *
 * [T] is the return type of the AsyncTask.
 */

public interface DatabaseResult<T> {
    /**
     * Process the data returned by the AsyncTask
     * @param result T, the specified generic Task Return Type
     */
    public void processResult(T result);

}
