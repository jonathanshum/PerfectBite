package cs160group36.perfectbite;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.io.UnsupportedEncodingException;
import java.util.GregorianCalendar;

/**
 * Created by avishah1 on 12/1/15.
 */

public class ListenerService extends WearableListenerService {
    DatabaseHelper myDbHelper;
    SQLiteDatabase myDb;

    @Override
    public void onCreate(){
        super.onCreate();
        myDbHelper = new DatabaseHelper(this);
        myDb = myDbHelper.getWritableDatabase();
    }


    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("getting here", "Starting Activity");
        String path = messageEvent.getPath();
        if (path.equalsIgnoreCase("WatchtoPhone")){
            byte[] text = messageEvent.getData();
            String food = "";
            try {
                food = new String(text, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                food = "";
            }
            GregorianCalendar now = new GregorianCalendar();
            myDbHelper.insertLogData(myDb,food,now.toString(),now.getTime().toString(),1.0);

            Toast.makeText(getApplicationContext(), "Metric Data Updated", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MetricsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }





    }

}



