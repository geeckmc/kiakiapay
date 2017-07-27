package co.opensi.kiakia_pay.handler;

/**
 * Created by Shadaï ALI on 13/12/16.
 *
 * @Doc $doc
 */

public class NotMtnMessageException extends Exception {

    @Override
    public String getMessage() {
         super.getMessage();
        return "ATTENTION FRAUDE : Ce message ne vient pas de MTN";
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }
}
