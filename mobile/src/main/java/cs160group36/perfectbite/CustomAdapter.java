package cs160group36.perfectbite;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jonathan on 12/5/2015.
 */
class CustomAdapter extends BaseAdapter {
    public static final String FIRST_COLUMN="First";
    public static final String SECOND_COLUMN="Second";
    public static final String THIRD_COLUMN="Third";

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;
    TextView txtThird;

    public CustomAdapter(Activity activity,ArrayList<HashMap<String, String>> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){
            convertView=inflater.inflate(R.layout.multi_list_item, parent, false);
            txtFirst=(TextView) convertView.findViewById(R.id.food);
            txtSecond=(TextView) convertView.findViewById(R.id.servings);
            txtThird=(TextView) convertView.findViewById(R.id.time);
        }



        HashMap<String, String> map=list.get(position);
        txtFirst.setText(map.get(FIRST_COLUMN));
        txtSecond.setText(map.get(SECOND_COLUMN));
        txtThird.setText(map.get(THIRD_COLUMN));

        txtFirst.setTextSize(18.0f);
        txtSecond.setTextSize(18.0f);
        txtThird.setTextSize(18.0f);

        if (position == 0) {
            txtFirst.setTypeface(null, Typeface.BOLD);
            txtSecond.setTypeface(null, Typeface.BOLD);
            txtThird.setTypeface(null, Typeface.BOLD);
        }

        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#EEEEEE"));
        }

        return convertView;
    }



}