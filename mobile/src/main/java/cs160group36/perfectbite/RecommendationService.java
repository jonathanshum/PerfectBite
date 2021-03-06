package cs160group36.perfectbite;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.Random;

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

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        double x = Math.random();
        String finalmessage = "";
        String mealMessage = "";
        //dummy nutrient values for now
        nutrients.put("Protein",10);
        nutrients.put("Carbohydrates",20);
        nutrients.put("Fat",15);

        Map.Entry<String, Integer> min = null;
        for (Map.Entry<String, Integer> entry : nutrients.entrySet()) {
            if (min == null || min.getValue() > entry.getValue()) {
                min = entry;
            }
        }
        Log.d(min.getKey(), "minnutrient");
        DatabaseHelper datah = new DatabaseHelper(this);
        Calendar c = Calendar.getInstance();
        String date = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH);//assuming date format is YYYY-MM-DD
        SQLiteDatabase myDb = datah.getWritableDatabase();
        Log.d(date, "date");
        String nutrientNeeded = datah.getLowestProgress(myDb, date);
        Log.d(nutrientNeeded, "fromdata");

        if (false) {
            Calendar cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            Log.d(Integer.toString(hour), "time");
            if (hour >= 6 && hour <= 10 && !eatenRecently) {
                mealMessage += "Now is a good time to eat breakfast.";
                Log.d("breakfast", "whatmeal");
            } else if (hour > 10 && hour <= 13 && !eatenRecently) {
                mealMessage += "Now is a good time to eat lunch.";
                Log.d("lunch", "whatmeal");
            } else if (hour >= 16 && hour <= 9 && !eatenRecently) {
                mealMessage += "Now is a good time to eat dinner.";
                Log.d("dinner", "whatmeal");
            } else {
                mealMessage += "Now is a good time to eat lunch.";
                Log.d("lunch", "whatmeal");
            }
            String message = "Try to eat some ";
            message += nutrientNeeded + ".";
            finalmessage = mealMessage + "\n" + message;
            System.out.println(finalmessage);
        } else {
            finalmessage += "You are low on ";
            finalmessage += nutrientNeeded + ".";
        }

        toSend = finalmessage;

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
        return START_STICKY;
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
}
