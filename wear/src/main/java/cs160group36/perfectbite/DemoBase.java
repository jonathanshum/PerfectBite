package cs160group36.perfectbite;

import android.support.v4.app.FragmentActivity;

/**
 * Created by jonathan on 12/1/2015.
 */
public abstract class DemoBase extends FragmentActivity {

    protected String[] mMonths = new String[] {
            "Mon", "Tues", "Wed", "Thurs", "Fri", "Sat", "Sun"
    };

    protected String[] mParties = new String[] {
            "Calories", "Proteins", "Fats", "Carbs", "Sugar"
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
