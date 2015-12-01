package cs160group36.perfectbite;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RecommendationService extends Service {
    //there is a boolean that knows if you have inputted food in the last 2 hours
    //would be taken from the database. For now, I am using this as a placeholder
    boolean eatenRecently = false;

    //Map of nutrients that the user is tracking.
    //Has value(percentage of goal) and name of nutrient
    //Would be taken from the database, for now this is a placeholder
    Map<String, Integer> nutrients = new HashMap<String, Integer>();

    public RecommendationService() {
    }

    public void onCreate() {
        String mealMessage = "";
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
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
