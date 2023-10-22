package elsys.project.modules.phone_calls;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CallRecordRetriever {
    public static final int MISSED = CallLog.Calls.MISSED_TYPE;
    public static final int REJECTED = CallLog.Calls.REJECTED_TYPE;


    public String checkMissedCalls(Context context, int callState) {

        ContentResolver contentResolver = context.getContentResolver();

        String[] projection = {
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.NUMBER,
                CallLog.Calls.TYPE,
                CallLog.Calls.DATE
        };

        String selection = CallLog.Calls.TYPE + " = ?";
        String[] selectionArgs = {
                Integer.toString(callState),
        };

        Cursor cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, projection, selection, selectionArgs,
                CallLog.Calls.DATE + " DESC");

        String name = "";
        long callDate = 0;
        String number = "";

        if (cursor != null) {
            cursor.moveToFirst();
            int nameColumnIndex = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
            int numberColumnIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int dateColumnIndex = cursor.getColumnIndex(CallLog.Calls.DATE);

            if (nameColumnIndex >= 0) {
                name = cursor.getString(nameColumnIndex);
            }

            if (numberColumnIndex >= 0) {
                number = cursor.getString(numberColumnIndex);
            }

            if (dateColumnIndex >= 0) {
                callDate = cursor.getLong(dateColumnIndex);
            }
            cursor.close();
        }

        Date date = new Date(callDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return name + " " + number + " " + sdf.format(date);
    }
}
