package cs160group36.perfectbite;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by avishah1 on 12/5/15.
 */
public class goalFragment extends Fragment {



    public static final String goalTitleKey = "goalTitle";
    public static final String goalDescKey = "goalDesc";
    public static final String goalProgKey = "goalProg";
    public static final String numGoalsKey = "numGoals";
    public static final String currPositionKey = "currPos";
    String numGoals;
    String goalTitle;
    String goalDesc;
    String goalProg;
    String currPos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_goals, container, false);


        Bundle bundle = getArguments();
        if (bundle != null){
            goalTitle = bundle.getString(goalTitleKey);
            goalDesc = bundle.getString(goalDescKey);
            goalProg = bundle.getString(goalProgKey);
            numGoals = bundle.getString(numGoalsKey);
            currPos = bundle.getString(currPositionKey);
        }

        setValues(view, numGoals, goalTitle, goalDesc, goalProg, currPos);




        return view;


    }

    private void setValues(View view, String numGoals, String goalTitle, String goalDesc, String goalProg, String currPos){
        ImageView imageView = (ImageView) view.findViewById(R.id.goalImageView);
        imageView.setImageResource(R.drawable.trash);

        TextView goalTitleText = (TextView) view.findViewById(R.id.goalTitleText);
        goalTitleText.setText(goalTitle);

        TextView goalDescText = (TextView) view.findViewById(R.id.goalDetailText);
        goalDescText.setText(goalDesc);


        TextView goalProgText = (TextView) view.findViewById(R.id.goalProgText);
        goalProgText.setText(goalProg);

        TextView statusText = (TextView) view.findViewById(R.id.statusText);
        statusText.setText(currPos + " of " + numGoals);

    }
}
