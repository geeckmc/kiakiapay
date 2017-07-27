package co.opensi.kiakia_pay.core;

import android.app.Activity;
import android.os.CountDownTimer;
import android.util.Log;

/**
 * Created by ALI SHADAÏ (Software Crafstman) on 08/03/2017.
 */

public class UssdProcessHelper {

    public static PayCallback payCallback;
    public static boolean succes = false;
    public static String clientNum;
    /**
     * Cette methode permet de declencher un appel ussd
     * de demande de paiement mobile money
     * @param numero numero du client
     * @param montant montant a prendre chez le client
     * @param ctx
     */
    public void call(String numero, String montant , String password,Activity ctx) {
        succes = false;
        if (numero.contains("00229")) numero = numero.substring(5);
        else  if (numero.contains("+229")) numero = numero.substring(4);
        else  if (numero.contains("229")) numero = numero.substring(3);

        clientNum = numero;
        String ussdCode = GSM.getMobileMoneyUssd(numero,
                password, montant);
        Ussd ussd = new Ussd(ussdCode, ctx,true);
       // ctx.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        try {
            ussd.process();
            new CountDownTimer(60000, 60000) {
                @Override
                public void onTick(long l) {}
                @Override
                public void onFinish() {
                    if(!succes){
                        payCallback.onTimeOut();
                    }
                }
            }.start();

        } catch (Exception e) {
            Log.e("Kiakia pay API","in catch in UssdProcessHelper");
            e.printStackTrace();
        }
    }

}