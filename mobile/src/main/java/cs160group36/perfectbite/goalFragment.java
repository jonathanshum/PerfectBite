package cs160group36.perfectbite;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by avishah1 on 12/5/15.
 */
public class goalFragment extends Fragment {



    public static final String goalTitleKey = "goalTitle";
    public static final String goalDescKey = "goalDesc";
    public static final String goalProgKey = "goalProg";
    public static final String numGoalsKey = "numGoals";
    public static final String currPositionKey = "currPos";
    public static final String category = "category";
    boolean empty = false;
    String numGoals;
    String goalTitle;
    String goalDesc;
    String goalProg;
    String currPos;
    String goalCategory;

    DatabaseHelper myDbHelper;
    SQLiteDatabase myDb;


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
            goalCategory = bundle.getString(category);
            if (goalProg.equalsIgnoreCase("")){
                ImageView delete = (ImageView) view.findViewById(R.id.deleteImage);
                delete.setVisibility(View.GONE);
                boolean empty = true;
            }

        }

        setValues(view, numGoals, goalTitle, goalDesc, goalProg, currPos);

        myDbHelper = new DatabaseHelper(view.getContext());
        myDb = myDbHelper.getWritableDatabase();

        ImageView add = (ImageView) view.findViewById(R.id.addFood);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), AddGoalActivity.class);
                startActivity(i);
            }
        });

        ImageView delete = (ImageView) view.findViewById(R.id.deleteImage);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
                builder.setMessage("Delete Goal?")
                        .setTitle("");
                builder.setPositiveButton("Yes, Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Goal Deleted!",
                                Toast.LENGTH_LONG).show();
                        myDbHelper.modifyIsGoal(myDb, goalCategory, 0);
                        Log.d("GoalCat", goalCategory);
                        Intent i = new Intent(getContext(), myGoalsActivity.class);
                        startActivity(i);
                    }
                });
                builder.setNegativeButton("No", null);
                android.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        ImageView back = (ImageView) view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MetricsActivity.class);
                startActivity(i);
            }
        });

        return view;


    }

    private void setValues(View view, String numGoals, String goalTitle, String goalDesc, String goalProg, String currPos){
//        ImageView imageView = (ImageView) view.findViewById(R.id.goalImageView);
//        imageView.setImageResource(R.drawable.trash);

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
