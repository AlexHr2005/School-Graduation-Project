package elsys.project.modules.phone_calls;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.telecom.Call;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CallRecordRetriever {
    public static final int MISSED = CallLog.Calls.MISSED_TYPE;
    public static final int REJECTED = CallLog.Calls.REJECTED_TYPE;


    public static String returnLastCall(Context context) {

        ContentResolver contentResolver = context.getContentResolver();

        String[] projection = {
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.NUMBER,
                CallLog.Calls.TYPE,
                CallLog.Calls.DATE
        };


        String[] selectionArgs = new String[0];

        Cursor cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, projection, null, selectionArgs,
                CallLog.Calls.DATE + " DESC");

        String name = "";
        long callDate = 0;
        String number = "";
        String type = "";

        if (cursor != null) {
            cursor.moveToFirst();
            int nameColumnIndex = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
            int numberColumnIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int dateColumnIndex = cursor.getColumnIndex(CallLog.Calls.DATE);
            int typeColumnIndex = cursor.getColumnIndex(CallLog.Calls.TYPE);

            if (nameColumnIndex >= 0) {
                name = cursor.getString(nameColumnIndex);
            }

            if (numberColumnIndex >= 0) {
                number = cursor.getString(numberColumnIndex);
            }

            if (dateColumnIndex >= 0) {
                callDate = cursor.getLong(dateColumnIndex);
            }

            if (typeColumnIndex >= 0) {
                type = cursor.getString(dateColumnIndex);
            }
            cursor.close();
        }

        Date date = new Date(callDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String call = name + " " + number + " " + sdf.format(date) + " " + type;
        return call;
    }
}
