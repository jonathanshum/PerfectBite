package cs160group36.perfectbite;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.filter.Approximator;
import com.github.mikephil.charting.data.filter.Approximator.ApproximatorType;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class EditMetricsActivity extends DemoBase implements OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    public static final int[] BLUE_COLORS = {
            Color.parseColor("#6B9FD2"), Color.parseColor("#5C6BC0"), Color.parseColor("#0D47A1"),
            Color.parseColor("#00B8D4"), Color.parseColor("#303F9F"), Color.parseColor("#0091EA")
    };
    String DEBUG_TAG = "EditMetricsActivity";
    private GestureDetectorCompat mDetector;

    private GoogleApiClient mApiClient;

    protected BarChart mChart;
    private ScrollView scrollView;
    private ArrayList<HashMap<String, String>> list;
    public static final String FIRST_COLUMN="First";
    public static final String SECOND_COLUMN="Second";
    public static final String THIRD_COLUMN="Third";
    DatabaseHelper myDbHelper;
    SQLiteDatabase myDb;

    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metrics_detailed);

        myDbHelper = new DatabaseHelper(this);
        myDb = myDbHelper.getWritableDatabase();

        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("category");

//        Cursor goals = myDbHelper.fetchGoals(myDb);
//        ArrayList<String> titles = new ArrayList<String>();
//        ArrayList<String> values = new ArrayList<String>();
//
//        for (goals.moveToFirst(); !goals.isAfterLast(); goals.moveToNext()){
//            String title = goals.getString(goals.getColumnIndex("category"));
//            String value = goals.getString(goals.getColumnIndex("value"));
//            titles.add(title);
//            values.add(value);
//        }
//
//        goals.close();
//        String val = values.get(titles.indexOf(message));
//
//        String goal = message + ": " + val;
        TextView txt = (TextView) findViewById(R.id.textView);
        txt.setText(message);

        mChart = (BarChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawBarShadow(false);

        mChart.setDrawValueAboveBar(true);
        mChart.setDescription("");
        mChart.setMaxVisibleValueCount(60);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);

        XAxis xl = mChart.getXAxis();
        xl.setPosition(XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.0f);

        setData(7, 100, mDays);
        mChart.animateY(2500);

        Legend l = mChart.getLegend();
        l.setPosition(LegendPosition.BELOW_CHART_LEFT);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);

        mChart.getLegend().setEnabled(false);
        mChart.getAxisLeft().setEnabled(false);
        mChart.getAxisRight().setEnabled(false);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        }, 5);

        ImageView settings = (ImageView) findViewById(R.id.settingsView);
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), myGoalsActivity.class);
                startActivity(i);
            }
        });

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

        final Button allTimeButton = (Button) findViewById(R.id.AllTimeButton);
        final Button weeklyButton = (Button) findViewById(R.id.WeeklyButton);
        final Button dailyButton = (Button) findViewById(R.id.DailyButton);

        allTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setData(5, 100, mAllTime);
                mChart.animateY(2500);
                allTimeButton.setBackgroundColor(Color.parseColor("#01579B"));
                weeklyButton.setBackgroundColor(Color.parseColor("#6B9FD2"));
                dailyButton.setBackgroundColor(Color.parseColor("#6B9FD2"));
            }
        });

        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setData(7, 100, mDays);
                mChart.animateY(2500);
                allTimeButton.setBackgroundColor(Color.parseColor("#6B9FD2"));
                weeklyButton.setBackgroundColor(Color.parseColor("#01579B"));
                dailyButton.setBackgroundColor(Color.parseColor("#6B9FD2"));

            }
        });

        dailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setData(6, 100, mWeeks);
                mChart.animateY(2500);
                allTimeButton.setBackgroundColor(Color.parseColor("#6B9FD2"));
                weeklyButton.setBackgroundColor(Color.parseColor("#6B9FD2"));
                dailyButton.setBackgroundColor(Color.parseColor("#01579B"));
            }
        });

        ImageView addFood = (ImageView) findViewById(R.id.addFood);
        addFood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), DatabaseActivity.class);
                startActivity(i);
            }
        });

        updateListView();
    }

    public void updateListView(){
        ListView listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<HashMap<String,String>>();

        HashMap<String,String> temp1 = new HashMap<String, String>();
        temp1.put(FIRST_COLUMN, "Recent Bites");
        temp1.put(SECOND_COLUMN, message);
        temp1.put(THIRD_COLUMN, "Time");
        list.add(temp1);

        Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.AM_PM);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        Calendar c = Calendar.getInstance();
        String date = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH);

        Cursor data = myDbHelper.fetchLogDataFromDate(myDb,date);
        for (data.moveToLast(); !data.isBeforeFirst(); data.moveToPrevious())  {
            String name = data.getString(data.getColumnIndex("name"));
            name = name.substring(0,1).toUpperCase() + name.substring(1);
            String serving = data.getString(data.getColumnIndex("Calories"));
            String time = data.getString(data.getColumnIndex("time"));
            HashMap<String,String> tmp = new HashMap<String, String>();
            tmp.put(FIRST_COLUMN, name);
            tmp.put(SECOND_COLUMN, Integer.toString((int) Math.round(Math.random()*50.0)));
            tmp.put(THIRD_COLUMN, time);
            if (tmp != null) {
                list.add(tmp);
            }
        }
        data.close();

        CustomAdapter adapter = new CustomAdapter(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                int pos = position + 1;
                Toast.makeText(EditMetricsActivity.this, Integer.toString(pos) + " Clicked", Toast.LENGTH_SHORT).show();
            }

        });

        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        setListViewHeightBasedOnChildren(listView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                for (DataSet<?> set : mChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {
                if(mChart.getData() != null) {
                    mChart.getData().setHighlightEnabled(!mChart.getData().isHighlightEnabled());
                    mChart.invalidate();
                }
                break;
            }
            case R.id.actionTogglePinch: {
                if (mChart.isPinchZoomEnabled())
                    mChart.setPinchZoom(false);
                else
                    mChart.setPinchZoom(true);

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleAutoScaleMinMax: {
                mChart.setAutoScaleMinMaxEnabled(!mChart.isAutoScaleMinMaxEnabled());
                mChart.notifyDataSetChanged();
                break;
            }
            case R.id.actionToggleHighlightArrow: {
                if (mChart.isDrawHighlightArrowEnabled())
                    mChart.setDrawHighlightArrow(false);
                else
                    mChart.setDrawHighlightArrow(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleStartzero: {
                mChart.getAxisLeft().setStartAtZero(!mChart.getAxisLeft().isStartAtZeroEnabled());
                mChart.getAxisRight().setStartAtZero(!mChart.getAxisRight().isStartAtZeroEnabled());
                mChart.invalidate();
                break;
            }
            case R.id.animateX: {
                mChart.animateX(3000);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(3000);
                break;
            }
            case R.id.animateXY: {

                mChart.animateXY(3000, 3000);
                break;
            }
            case R.id.actionToggleFilter: {

                Approximator a = new Approximator(ApproximatorType.DOUGLAS_PEUCKER, 25);

                if (!mChart.isFilteringEnabled()) {
                    mChart.enableFiltering(a);
                } else {
                    mChart.disableFiltering();
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionSave: {
                if (mChart.saveToGallery("title" + System.currentTimeMillis(), 50)) {
                    Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
                            .show();
                break;
            }
        }
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    private void setData(int count, float range, String[] cat) {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add(cat[i % 12]);
            yVals1.add(new BarEntry((float) (Math.random() * range), i));
        }
        BarDataSet set1 = new BarDataSet(yVals1, "DataSet 1");
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : BLUE_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        set1.setColors(colors);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);
        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        mChart.setData(data);
    }

    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        if (e == null)
            return;
        RectF bounds = mChart.getBarBounds((BarEntry) e);
        PointF position = mChart.getPosition(e, mChart.getData().getDataSetByIndex(dataSetIndex)
                .getAxisDependency());
        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());
        mApiClient.connect();
        sendMessage("WatchtoPhone", "MessageSentToPhone");
    }

    public void onNothingSelected() {
    };

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

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}