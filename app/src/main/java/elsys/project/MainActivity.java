package elsys.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import elsys.project.modules.phone_calls.CallRecordRetriever;
import elsys.project.modules.phone_calls.CallStateReceiver;
import elsys.project.modules.phone_calls.NewCallHandler;

public class MainActivity extends AppCompatActivity implements NewCallHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CallStateReceiver callStateReceiver = new CallStateReceiver();
        CallStateReceiver.bindHandler(this);
        IntentFilter intentFilter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        registerReceiver(callStateReceiver, intentFilter);
    }


    @Override
    public void callHandler() {
        ((TextView)findViewById(R.id.call_info)).setText(CallRecordRetriever.returnLastCall(this));
    }
}