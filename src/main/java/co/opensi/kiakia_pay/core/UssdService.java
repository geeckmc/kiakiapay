package co.opensi.kiakia_pay.core;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.Collections;
import java.util.List;

public class UssdService extends AccessibilityService {


    public static String TAG = UssdService.class.getSimpleName();
    public boolean IN_PROCESS = false;
    private List<CharSequence> eventText;


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "onAccessibilityEvent");
        Log.e("event",String.valueOf(event.getClassName()));
        Log.e("event type",String.valueOf(event.getEventType()));

        AccessibilityNodeInfo source = event.getSource();

       /* if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED && !String.valueOf(event.getClassName()).contains("AlertDialog")) {
            return;
        }*/
        /*if (String.valueOf(event.getClassName()).contains("ProgressDialog")) {
          *//*  LoadingAnimation loadingAnimation = new LoadingAnimation();
            loadingAnimation.show(PaiementFragment.getManager(),"loadingDiaalog");*//*
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                Log.e("ussd","perform global");
                performGlobalAction(GLOBAL_ACTION_BACK);
            }
        }*/
        if (String.valueOf(event.getClassName()).contains("UssdAlertActivity")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                Log.e("ussd","perform global");
                performGlobalAction(GLOBAL_ACTION_BACK);
            }
            return;
        }
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED && (source == null || !source.getClassName().equals("android.widget.TextView"))) {
            return;
        }
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED && TextUtils.isEmpty(source.getText())) {
            return;
        }
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            eventText = event.getText();
            Log.e("event text1",event.getText().toString());
        } else {
            eventText = Collections.singletonList(source.getText());
            Log.e("event text2",event.getText().toString());
        }
        //checkIfFailed(eventText, PaiementFragment.numero.getText().toString());
       return;

       /* List<CharSequence> eventText;

        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            eventText = event.getText();
        } else {
            eventText = Collections.singletonList(source.getText());
        }

        String text = processUSSDText(eventText);

        getAmount(text);


        if (TextUtils.isEmpty(text)) return;*/

        // Close dialog
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // performGlobalAction(GLOBAL_ACTION_BACK);
        }

        Intent intent = new Intent ("message"); //put the same message as in the filter you used in the activity when registering the receiver
        intent.putExtra("TEST", "HELLO");
        //intent.putExtra("state", state);
        sendBroadcast(intent);

        Log.e(TAG, text);*/


    }

    private void checkIfFailed(List<CharSequence> eventText,String number) {
        String text = "";
        for (CharSequence s : eventText) {
            text = new StringBuilder(text).append(s).toString();
            // Return text if text is the expected ussd response
        }

        Log.e("event text3",text);
       /* if (!text.contains(number)){
            if (!PaiementFragment.loadingAnimation.isSucces)
                PaiementFragment.loadingAnimation.startFailedAnim();
            //TODO : notify error
        }*/
    }

    private String processUSSDText(List<CharSequence> eventText) {
        IN_PROCESS = true;
        for (CharSequence s : eventText) {
            String text = String.valueOf(s);
            // Return text if text is the expected ussd response
            if (true) {
                return text;
            }
        }
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent intento = new Intent("message");
        intent.putExtra("TEST", "HELLO");
        //intent.putExtra("state", state);
        Log.e("onstartcommand", "");
        sendBroadcast(intento);
        return super.onStartCommand(intent, flags, startId);
    }

    private int getAmount(String text) throws NumberFormatException {
        Log.e("amount_", "enter");
        if (!text.trim().startsWith("Solde")) {
            Log.e("amount_", "not start with solde :" + text);
            return 0;
        }
        Log.e("amount_", "start with solde");
        int i = Integer.parseInt(text.trim().split(":")[1].trim().split("FCFA")[0].trim());
        Log.e("amount_", String.valueOf(i));
        if (Ussd.STATE == 1)
            Ussd.INITAIL_AMOUNT = i;
        Ussd.FINAL_AMOUNT = i;
        return i;
    }

    @Override
    public void onInterrupt() {
        Log.e("on", "interrupt");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.e(TAG, "onServiceConnected");
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.flags = AccessibilityServiceInfo.DEFAULT;
        info.packageNames = new String[]{"com.android.phone"};
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED | AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
    }
}