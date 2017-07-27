package co.opensi.kiakia_pay;

import android.app.Activity;
import android.support.annotation.NonNull;

import co.opensi.kiakia_pay.core.PayCallback;
import co.opensi.kiakia_pay.core.UssdProcessHelper;

/**
 * Created by ALI SHADAÏ (Software Craftman) on 19/06/2017.
 */

public class KiakiaPay {

    String mtnPassword;
    UssdProcessHelper ussdProcessHelper;
    Activity ctx;
    PayCallback callback;


   public static class Builder {
       KiakiaPay kiakiaPay;
       public Builder(Activity activity) {
           kiakiaPay = new KiakiaPay(activity);
       }

       public KiakiaPay with(String pass){
           kiakiaPay.setMtnPassword(pass);
           return kiakiaPay;
       }
   }


    public KiakiaPay(Activity activity) {
        ctx = activity;
        ussdProcessHelper = new UssdProcessHelper();
    }

     void setMtnPassword(String mtnPassword) {
        this.mtnPassword = mtnPassword;
    }

    /**
     * Demander un paiement
     * @param telephone le téléphone du client (strictement un numéro Béninois)
     * @param montant  strictement le montant a débiter au client
     * @param callback le listner pour etre alerté par le statut de la requête
     */
    public void askPayement(String telephone, long montant,@NonNull PayCallback callback)
    {
        UssdProcessHelper.payCallback = callback;
        this.callback = callback;
        ussdProcessHelper.call(telephone,String.valueOf(montant),mtnPassword,ctx);
    }

}
