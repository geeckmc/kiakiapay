package co.opensi.kiakia_pay.handler;

import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Shadaï ALI on 13/12/16.
 *
 * @Doc $doc
 */

public class MtnMobileSmsParser {
    private SmsMessage[] msgs;
    private static ArrayList<String> authorizedfromList;
    private static String MTN_BENIN_MOBILE_ADRESS = "MMoney";
    private static String MTN_BENIN_SMSC = "+22997976851";
    private static String MTN_BENIN_SMSC_2 = "+22997976852";

    public HashMap<String, String> getMultipartSmsAsOne(final Bundle bundle) {
        boolean notAuthorized = false;
        //Log.e("bundle",new Gson().toJson(bundle));

        HashMap<String, String> msgMap = new HashMap<>();
        if (bundle != null && bundle.containsKey("pdus")) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            //get received msg content
            if (pdus != null) {

                int nbOfPdus = pdus.length;

                msgMap = new HashMap<>(nbOfPdus);
                ArrayList<String> adressesArray = new ArrayList<>(nbOfPdus);
                msgs = new SmsMessage[nbOfPdus];


                for (int i = 0; i < nbOfPdus; i++) {


                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    else
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], bundle.getString("format"));

                    String from = msgs[i].getOriginatingAddress();
                    String smsc = msgs[i].getServiceCenterAddress();




                    notAuthorized = !isMtnMobileMoneyMsg(from, smsc);

                    if (notAuthorized) {
                        try {
                            throw new NotMtnMessageException();
                        } catch (NotMtnMessageException e) {
                            e.printStackTrace();
                        }

                        continue;
                    }


                    if (!msgMap.containsKey(from)) {
                        msgMap.put(msgs[i].getOriginatingAddress(), msgs[i].getMessageBody());
                        adressesArray.add(msgs[i].getOriginatingAddress());
                    } else {
                        String previousParts = msgMap.get(from);
                        String msgString = previousParts + msgs[i].getMessageBody();

                        msgMap.put(from, msgString); 
                        adressesArray.add(from);

                    }
                }
            }

        }
        return msgMap;
    }

    public static ArrayList<String> getAuthorizedfromList() {
        if (authorizedfromList == null) {
            authorizedfromList = new ArrayList<>();
            authorizedfromList.add(MTN_BENIN_MOBILE_ADRESS);
        }
        return authorizedfromList;
    }

    public static boolean isMtnMobileMoneyMsg(String adress, String smsc) {
        return MTN_BENIN_MOBILE_ADRESS
                .equalsIgnoreCase(adress.trim()) && ( MTN_BENIN_SMSC.equalsIgnoreCase(smsc.trim()) ||
                MTN_BENIN_SMSC_2.equalsIgnoreCase(smsc.trim()));
    }
}
