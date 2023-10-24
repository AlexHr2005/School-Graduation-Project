package elsys.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import elsys.project.modules.phone_calls.CallStateReceiver;
import elsys.project.modules.phone_calls.StateChangeHandler;

public class MainActivity extends AppCompatActivity implements StateChangeHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CallStateReceiver.bindHandler(this);
    }

    @Override
    public void onStateChange(String state) {
        Log.d("lalala", state);
        ((TextView)findViewById(R.id.call_state)).setText(state);
    }
}