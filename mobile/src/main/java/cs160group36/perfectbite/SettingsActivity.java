package cs160group36.perfectbite;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    String DEBUG_TAG = "SettingsActivity";
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final ListView listview = (ListView) findViewById(R.id.listView);
        String[] values = new String[] { "Account Setup", "My Goals", "Help"};
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        ArrayAdapter itemsAdapter;
        itemsAdapter =
                new myAdapter(this, android.R.layout.simple_list_item_1);

        itemsAdapter.addAll(values);
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(itemsAdapter);
//        ImageView settings = (ImageView) findViewById(R.id.homeView);
//        settings.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent i = new Intent(v.getContext(), MetricsActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                // TODO Auto-generated method stub
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);
                if (position == 1){
                    Intent intent = new Intent(getApplicationContext(), myGoalsActivity.class);
                    startActivity(intent);
                }

//                // Show Alert
//                Toast.makeText(getApplicationContext(),
//                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
//                        .show();
            }
        });
        TextView rec = (TextView) findViewById(R.id.recTextView);
        rec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Clicked", "clicked");
                //Intent i = new Intent(v.getContext(), RecommendationService.class);
                //startActivity(i);
                //finish();
                sendRec();
            }
        });
    }
    public void sendRec() {
        Intent intent = new Intent(this, RecommendationService.class);
        startService(intent);
        Log.d("did it", "clicked");
    }
}

class myAdapter extends ArrayAdapter {
    public myAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        if (position % 2 == 1) {
            view.setBackgroundColor(Color.parseColor("#59C0E3"));
        } else {
            view.setBackgroundColor(Color.parseColor("#5094AB"));
        }
        return view;
    }



}

