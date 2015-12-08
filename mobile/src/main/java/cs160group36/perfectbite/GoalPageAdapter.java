package cs160group36.perfectbite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by avishah1 on 12/5/15.
 */
public class GoalPageAdapter extends FragmentPagerAdapter {

    ArrayList<String> titles;
    ArrayList<String> descriptions;
    ArrayList<String> progress;
    DatabaseHelper myDbHelper;
    SQLiteDatabase myDb;
    String category;



    public static final String TABLE3_NAME = "usergoals";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_GOAL = "isgoal";
    public static final String KEY_VALUE = "value";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_PROGRESS = "progressmessage";


    public GoalPageAdapter(FragmentManager fm, Context context) {
        super(fm);


        myDbHelper = new DatabaseHelper(context);
        myDb = myDbHelper.getWritableDatabase();

        Cursor goals = myDbHelper.fetchGoals(myDb);
        titles = new ArrayList<String>();
        descriptions = new ArrayList<String>();
        progress = new ArrayList<String>();




        for (goals.moveToFirst(); !goals.isAfterLast(); goals.moveToNext()){
            String title = goals.getString(goals.getColumnIndex(KEY_CATEGORY));
            String description = goals.getString(goals.getColumnIndex(KEY_DESCRIPTION));
            String prog = goals.getString(goals.getColumnIndex(KEY_PROGRESS));

            category = title;
            titles.add(title);
            descriptions.add(description);
            progress.add(prog);

        }

        goals.close();

        if (titles.size() < 1){
            titles.add("You Have No Goals In Your Life!");
            descriptions.add("Click on the + at the top of the screen to create a goal");
            progress.add("");
        }
        else{
            titles.add("Add A New Goal");
            descriptions.add("Click on the + at the top of the screen to add a goal");
            progress.add("");
        }

//        titles.add("Protein");
//        titles.add("Calories");
//        titles.add("Fat");
//
//        descriptions.add("Eat 20-30g of Protein per day");
//        descriptions.add("Consume 1500 - 200 calories per day");
//        descriptions.add("Eat less than 70g of Fat per day");





    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        if (progress.get(position).equalsIgnoreCase("")){
            bundle.putString(goalFragment.goalTitleKey, titles.get(position));
        }
        else{
            bundle.putString(goalFragment.goalTitleKey, "Goal " + (position+1) + " - " + titles.get(position));
        }
        bundle.putString(goalFragment.goalDescKey, descriptions.get(position));
        bundle.putString(goalFragment.goalProgKey, progress.get(position));
        bundle.putString(goalFragment.numGoalsKey, titles.size()+"");
        bundle.putString(goalFragment.currPositionKey, position+1+"");
        bundle.putString(goalFragment.category, category);
        goalFragment goalFragment = new goalFragment();
        goalFragment.setArguments(bundle);


        return goalFragment;
    }

    @Override
    public int getCount() {
        return (titles.size());
    }
}
