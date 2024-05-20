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


    public static String returnLastCall(Context context) {

        ContentResolver contentResolver = context.getContentResolver();

        String[] projection = {
                CallLog.Calls.NUMBER,
        };

        String[] selectionArgs = {
                String.valueOf(CallLog.Calls.MISSED_TYPE),
                String.valueOf(CallLog.Calls.REJECTED_TYPE)
        };

        String selection = CallLog.Calls.TYPE + " = ? OR " +
                CallLog.Calls.TYPE + " = ?";

        Cursor cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, projection, selection, selectionArgs,
                CallLog.Calls.DATE + " DESC");

        String number = "";

        if (cursor != null) {
            cursor.moveToFirst();
            int numberColumnIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);

            if (numberColumnIndex >= 0) {
                number = cursor.getString(numberColumnIndex);
            }
            cursor.close();
        }
        return number;
    }
}
