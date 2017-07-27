package co.opensi.kiakia_pay.handler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Date;
import java.util.HashMap;

import co.opensi.kiakia_pay.core.GSM;
import co.opensi.kiakia_pay.core.UssdProcessHelper;
import co.opensi.kiakia_pay.data.Paiement;

public class SMSRecevier extends BroadcastReceiver {

    final SmsManager sms = SmsManager.getDefault();
    MtnMobileSmsParser mtnMobileSmsParser;
    private SmsMessage[] msgs;
    private boolean notAuthorized;
    private HashMap<String, String> listOfOKMsg;

    @Override
    public void onReceive(Context context, Intent intent) {



        if (mtnMobileSmsParser == null) mtnMobileSmsParser = new MtnMobileSmsParser();

        ///pushOnFireBase(newPayment("5200", "USD", "Flooz", "false", "18722614", "0022967434270"));
        final Bundle bundle = intent.getExtras();
        if (null != bundle) {

            Log.e("on receive",intent.getExtras().toString());

            listOfOKMsg = mtnMobileSmsParser.getMultipartSmsAsOne(bundle);
            for (String key : listOfOKMsg.keySet()) {
                String msg = listOfOKMsg.get(key);

                //TODO Broadcast pay end

                if(UssdProcessHelper.payCallback!= null){
                    Log.e("on sms handler num",UssdProcessHelper.clientNum);
                    UssdProcessHelper.payCallback
                            .onFinish(msg.contains(UssdProcessHelper.clientNum));
                }// EventBus.getDefault().post(getPaymentData(msg));
                 }
        }
    }


    public static Paiement getPaymentData(String sms) {
        Paiement paiement = new Paiement();
        //TODO : make it clean
        String regex_payementof = "payment of";
        Log.e("in paiement",sms);
        Log.e("mysms",sms);
        try {
            String amount = sms.substring(sms.indexOf(regex_payementof) + regex_payementof.length(), sms.indexOf("XOF")).trim();
            String numMoneySender = sms.substring(sms.indexOf("(") + 1, sms.indexOf(")")).trim();
            //String transactionId = sms.substring(sms.indexOf("ID:")+3, sms.indexOf("Frais")).trim();
            paiement.setNumClient(numMoneySender);
            paiement.setMontant(amount);
            //TODO: manage for multiple provider
            Log.e("sub num cli", paiement.getNumClient().substring(4));
            paiement.setProvider(GSM.getOperator(paiement.getNumClient().substring(4)));
            paiement.setDate(new Date());

        }
        catch (Exception e){
            e.printStackTrace();
            if(UssdProcessHelper.payCallback != null)
            UssdProcessHelper.payCallback.onFinish(false);
        }
        return paiement;
    }

}