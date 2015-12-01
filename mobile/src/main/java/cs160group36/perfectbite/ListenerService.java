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

        Intent metricUpdate = new Intent(this, MainActivity.class);
        startActivity(metricUpdate);

        Toast.makeText(getApplicationContext(), "Metric Data Updated", Toast.LENGTH_SHORT).show();


    }

}



