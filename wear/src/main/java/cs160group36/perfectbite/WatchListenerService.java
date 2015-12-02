package cs160group36.perfectbite;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

public class WatchListenerService extends WearableListenerService {
    public WatchListenerService() {


    }
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("made a listener", "something");
        String path = messageEvent.getPath();
        if (path.equalsIgnoreCase("WatchtoPhone")){
            Toast.makeText(getApplicationContext(), "Metric Data Updated", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MetricsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        int notificationId = 001;
        // Build intent for notification content
        Intent viewIntent = new Intent(this, MetricsActivity.class);
        //viewIntent.putExtra(EXTRA_EVENT_ID, eventId);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);
        String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.watchhome)
                        .setContentTitle("Snack Reminder")
                        .setContentText(value)
                        .setContentIntent(viewPendingIntent);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
        Log.d("did something", "something");
    }

}
