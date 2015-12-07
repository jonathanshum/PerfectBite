package cs160group36.perfectbite;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
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

        Bitmap bitmap = Bitmap.createBitmap(320,320, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.argb(255, 251, 185, 87));//orange

        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);
        String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
        double rand = Math.random();
        NotificationCompat.Builder notificationBuilder;
        NotificationCompat.BigTextStyle secondPageStyle;
        String toeat = "";
        Log.d(value, "value");
        if (value.equals("You are low on Carbohydrates.")) {
            toeat += "Gypsy's is nearby.\nTheir Crazy Alfredo pasta is popular.";
        } else if (value.equals("You are low on Fat.")) {
            toeat += "Dot Island Grill is nearby.\nEat some salmon for healthy fats!";
        } else if (value.equals("You are low on Protein.")) {
            toeat += "Bongo Burger is nearby. \nTry their black bean burger!";
        } else {
            toeat += "Bongo Burger is nearby. \nTry their black bean burger!";
        }
        if (rand >= .5) {//meal reminder
            notificationBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.logo)
                            .setContentTitle("Meal Reminder")
                            .setContentText("Eat Lunch")
                            .setContentIntent(viewPendingIntent);

            // Create a big text style for the second page
            secondPageStyle = new NotificationCompat.BigTextStyle();
            String page2 = value + " " + toeat;
            secondPageStyle.setBigContentTitle("Meal Reminder").bigText(page2);
        } else {//nutrient reminder
            notificationBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.logo)
                            .setContentTitle("Nutrient Reminder")
                            .setContentText(value)
                            .setContentIntent(viewPendingIntent);

            // Create a big text style for the second page
            secondPageStyle = new NotificationCompat.BigTextStyle();
            secondPageStyle.setBigContentTitle("Nutrient Reminder").bigText(toeat);
        }

        // Create second page notification
        Notification secondPageNotification = new NotificationCompat.Builder(this).setStyle(secondPageStyle).build();

        // Extend the notification builder with the second page
        Notification notification = notificationBuilder.extend(new NotificationCompat
                .WearableExtender()
                .setBackground(bitmap)
                .addPage(secondPageNotification)).build();

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
        Log.d("did something", "something");
    }

}
