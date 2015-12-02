package cs160group36.perfectbite;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RecommendationService extends Service {
    //there is a boolean that knows if you have inputted food in the last 2 hours
    //would be taken from the database. For now, I am using this as a placeholder
    boolean eatenRecently = false;
    private GoogleApiClient mApiClient;

    //Map of nutrients that the user is tracking.
    //Has value(percentage of goal) and name of nutrient
    //Would be taken from the database, for now this is a placeholder
    Map<String, Integer> nutrients = new HashMap<String, Integer>();
    String toSend;
    public RecommendationService() {
        toSend = "nothing";
    }

    public void onCreate() {
        String mealMessage = "";
        //dummy nutrient values for now
        nutrients.put("Protein",50);
        nutrients.put("Carbohydrates",20);
        nutrients.put("Fat",30);
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        Log.d(Integer.toString(hour), "time");
        if (hour >= 6 && hour <= 10 && !eatenRecently) {
            mealMessage += "Now is a good time to eat breakfast.";
            Log.d("breakfast", "whatmeal");
        } else if (hour > 10 && hour <= 13 && !eatenRecently) {
            mealMessage += "Now is a good time to eat lunch.";
            Log.d("lunch", "whatmeal");
        } else if (hour >= 16 && hour <=9 && !eatenRecently) {
            mealMessage += "Now is a good time to eat dinner.";
            Log.d("dinner", "whatmeal");
        } else {
            mealMessage += "If you are hungry, eat a snack.\n If not, wait for the next mealtime.";
            Log.d("snack?", "whatmeal");
        }

        Map.Entry<String, Integer> min = null;
        for (Map.Entry<String, Integer> entry : nutrients.entrySet()) {
            if (min == null || min.getValue() > entry.getValue()) {
                min = entry;
            }
        }

        Log.d(min.getKey(), "minnutrient");
        String nutrientNeeded = min.getKey();
        String message = "Try to eat some ";
        message += nutrientNeeded + ".";
        String finalmessage = mealMessage + "\n" + message;
        System.out.println(finalmessage);//The notification to send to watch, send at intervals for now?
        toSend = finalmessage;
        //notifyWatch();

        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                        /* Successfully connected */
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                        /* Connection was interrupted */
                    }
                })
                .build();

        mApiClient.connect();
        sendMessage("Recommendation", toSend);
    }

    private void sendMessage( final String path, final String text ) {
        Log.d("got here", "something");
        new Thread( new Runnable() {
            @Override
            public void run() {
                Log.d("SendingMessage", "MessageBeingSent");
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes( mApiClient ).await();
                for(Node node : nodes.getNodes()) {
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            mApiClient, node.getId(), path, text.getBytes() ).await();
                }
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void notifyWatch() {
        // Create a WearableExtender to add functionality for wearables
        android.support.v4.app.NotificationCompat.WearableExtender wearableExtender =
                new android.support.v4.app.NotificationCompat.WearableExtender()
                        .setHintHideIcon(true);

        // Create a NotificationCompat.Builder to build a standard notification
        // then extend it with the WearableExtender
        Notification notif = new android.support.v4.app.NotificationCompat.Builder(this)
                .setContentTitle("Title")
                .setContentText(toSend)
                .setSmallIcon(R.drawable.gear)
                .extend(wearableExtender)
                .build();

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Issue the notification with notification manager.
        notificationManager.notify(28, notif);
        Log.d("notified", "hope");
    }
}
