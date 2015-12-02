package cs160group36.perfectbite;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by avishah1 on 12/1/15.
 */

public class ListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
//            Intent intent = new Intent(this, InstaService.class);
//            startService(intent);
        Log.d("getting here", "Starting Activity");
        String path = messageEvent.getPath();
        if (path.equalsIgnoreCase("WatchtoPhone")){
            Toast.makeText(getApplicationContext(), "Metric Data Updated", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MetricsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }





    }

}



