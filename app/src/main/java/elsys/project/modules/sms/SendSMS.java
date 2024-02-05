package elsys.project.modules.sms;

import android.telephony.SmsManager;
import android.util.Log;

public class SendSMS {
    private String phoneNo;
    private String message;

    public SendSMS(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public boolean sendMessage(String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            Log.d("lalala", "sending sms to " + phoneNo + ": " + message);
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Log.d("lalala", "sending sms to " + phoneNo + ": " + message);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
