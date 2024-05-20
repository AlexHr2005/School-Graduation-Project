package elsys.project.modules.sms;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SendSMS {
    private String phoneNo;
    private Context context;

    public SendSMS(String phoneNo, Context context) {
        this.phoneNo = phoneNo;
        this.context = context;
    }

    public void sendMessage(String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
        } catch (Exception e) {
            Toast.makeText(context, "Automation app failed to send SMS", Toast.LENGTH_LONG).show();
        }
    }
}
