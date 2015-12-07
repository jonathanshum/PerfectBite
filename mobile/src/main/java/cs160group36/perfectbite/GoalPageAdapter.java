package cs160group36.perfectbite;

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

    public GoalPageAdapter(FragmentManager fm) {
        super(fm);

        titles = new ArrayList<String>();
        titles.add("Protein");
        titles.add("Calories");
        titles.add("Fat");

        descriptions = new ArrayList<String>();
        descriptions.add("Eat 20-30g of Protein per day");
        descriptions.add("Consume 1500 - 200 calories per day");
        descriptions.add("Eat less than 70g of Fat per day");

        progress = new ArrayList<String>();
        progress.add("Historically you meet your goal on 70% of days.  Deficiencies in protein can lead to exhaustion and headaches.");
        progress.add("You're doing great! You have eaten an average of 1800 calories per day over the last 2 weeks! Proper calorie intake is essential in maintaining a healthy weight");
        progress.add("This is a new goal of yours so we don't have much to grade your performance off of.  Keep working hard and eating lean foods!");



    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        bundle.putString(goalFragment.goalTitleKey, titles.get(position));
        bundle.putString(goalFragment.goalDescKey, descriptions.get(position));
        bundle.putString(goalFragment.goalProgKey, progress.get(position));
        bundle.putString(goalFragment.numGoalsKey, titles.size()+"");
        bundle.putString(goalFragment.currPositionKey, position+1+"");
        goalFragment goalFragment = new goalFragment();
        goalFragment.setArguments(bundle);


        return goalFragment;
    }

    @Override
    public int getCount() {
        return (titles.size());
    }
}
