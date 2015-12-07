package cs160group36.perfectbite;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class myGoalsActivity extends FragmentActivity {

    GoalPageAdapter mGoalPageAdapter;
    ViewPager mViewPager;
    DatabaseHelper myDbHelper;
    SQLiteDatabase myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_goals);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mGoalPageAdapter = new GoalPageAdapter(getSupportFragmentManager(), getApplicationContext());
        mViewPager.setAdapter(mGoalPageAdapter);
        myDbHelper = new DatabaseHelper(this);
        myDb = myDbHelper.getWritableDatabase();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_goals, menu);
//        mViewPager.setAdapter(mSectionsPagerAdapter);

       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
