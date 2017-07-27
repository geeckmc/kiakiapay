package co.opensi.kiakia_pay.core;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

/**
 * Created by geecko on 17/05/16.
 */
public class Ussd {

    public static int STATE = 0;
    public static int INITAIL_AMOUNT;
    public static int FINAL_AMOUNT;
    private static String VERIFY_ACOUNT_AMOUNT = "*400*2*45110#";
    public Intent intent;
    int i = 0;
    private Uri askPaymentDialCode;
    private Uri temp;
    private Context context;
    private Intent service;
    private boolean interceptUssdDialog;


    public Ussd(String askPaymentDialCode, Context context, boolean interceptUssdDialog) {
        this.askPaymentDialCode = ussdToCallableUri(askPaymentDialCode);
        this.context = context;
        STATE = 0;
        service = new Intent(context, UssdService.class);
        intent = new Intent(Intent.ACTION_CALL);
        this.interceptUssdDialog = interceptUssdDialog;
        if(interceptUssdDialog)
        {
            context.stopService(service);
            context.startService(service);
        }
    }



    public void process() throws InterruptedException {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        intent.setData(askPaymentDialCode);
        context.startActivity(intent);
        if(UssdProcessHelper.payCallback != null){
            UssdProcessHelper.payCallback.onStart();
        }
        if(interceptUssdDialog) {
            context.stopService(service);
            context.startService(service);
        }

    }


    private Uri ussdToCallableUri(String ussd) {
        String uriString = "";
        if (!ussd.startsWith("tel:"))
            uriString += "tel:";
        for (char c : ussd.toCharArray()) {
            if (c == '#')
                uriString += Uri.encode("#");
            else
                uriString += c;
        }
        return Uri.parse(uriString);
    }
}
