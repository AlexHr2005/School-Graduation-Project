package elsys.project.modules.sms;

import android.telephony.SmsManager;

public class SendSMS {
    private String phoneNo;
    private String message;

    public SendSMS(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public boolean sendMessage(String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
