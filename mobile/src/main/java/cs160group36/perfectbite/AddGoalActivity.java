package cs160group36.perfectbite;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class AddGoalActivity extends AppCompatActivity {

    String[] categories;
    String[] type;
    String[] options;
    String currCategory;
    DatabaseHelper myDbHelper;
    SQLiteDatabase myDb;
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);
        type = new String[2];
        type[0] = "FDA Recommended";
        type[1] = "Custom";

        categories = new String[5];
        categories[0] = "Calories";
        categories[1] = "Protein";
        categories[2] = "Fat";
        categories[3] = "Sodium";
        categories[4] = "Carbohydrates";

        options = new String[2];
        options[0] = "less than";
        options [1] = "more than";

        myDbHelper = new DatabaseHelper(getApplicationContext());
        myDb = myDbHelper.getWritableDatabase();


        TextView textView = (TextView) findViewById(R.id.consumeText);
        textView.setText("");
        textView = (TextView) findViewById(R.id.unitsText);
        textView.setText("");
        textView = (TextView) findViewById(R.id.customEndText);
        textView.setText("");
        textView = (TextView) findViewById(R.id.inputText);
        textView.setVisibility(View.GONE);
        Spinner spinner = (Spinner) findViewById(R.id.optionsSpinner);
        spinner.setVisibility(View.GONE);
        textView = (TextView) findViewById(R.id.RecommendedTextView);
        textView.setText("");
        textView.setVisibility(View.VISIBLE);


        Spinner categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        categorySpinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currCategory = categories[position];
                TextView textView = (TextView) findViewById(R.id.customEndText);
                textView.setText(currCategory + " per day ");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        Spinner typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        typeSpinner.setAdapter(adapter1);
//        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1){
                    TextView textView = (TextView) findViewById(R.id.consumeText);
                    textView.setText("Consume");
                    textView = (TextView) findViewById(R.id.unitsText);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("g of ");
                    textView = (TextView) findViewById(R.id.customEndText);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(currCategory + " per day ");
                    Spinner spinner = (Spinner) findViewById(R.id.optionsSpinner);
                    spinner.setVisibility(View.VISIBLE);
                    textView = (TextView) findViewById(R.id.RecommendedTextView);
                    textView.setVisibility(View.GONE);
                    textView = (TextView) findViewById(R.id.inputText);
                    textView.setVisibility(View.VISIBLE);

                }
                else if (position == 0){
                    TextView textView = (TextView) findViewById(R.id.consumeText);
                    textView.setText("");
                    textView = (TextView) findViewById(R.id.unitsText);
                    textView.setText("");
                    textView = (TextView) findViewById(R.id.customEndText);
                    textView.setText("");
                    Spinner spinner = (Spinner) findViewById(R.id.optionsSpinner);
                    spinner.setVisibility(View.GONE);
                    textView = (TextView) findViewById(R.id.RecommendedTextView);
                    textView.setText("This is the USDA Recommended amount!");
                    textView.setVisibility(View.VISIBLE);
                    textView = (TextView) findViewById(R.id.inputText);
                    textView.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        Spinner optionsSpinner = (Spinner) findViewById(R.id.optionsSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        optionsSpinner.setAdapter(adapter2);
//        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        optionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        Button saveButton;
        saveButton = (Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView textView = (TextView) findViewById(R.id.inputText);
                String valueString = textView.getText().toString();
                value = Integer.parseInt(valueString);
                String progressString;
                myDbHelper.modifyEntireGoal(myDb, currCategory, 1, value, "Eat less than " + valueString +"g"+" per day", "This goal is too new to give you progress feedback.  Keep eating healthy and we'll have data for you soon!" );
                Intent intent = new Intent(getApplicationContext(), myGoalsActivity.class);
                startActivity(intent);
            }
        });

        Button cancelButton;
        cancelButton = (Button)findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
                alertDialogBuilder.setMessage("Discard Goal?");

                alertDialogBuilder.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(getApplicationContext(), myGoalsActivity.class);
                        startActivity(intent);
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                Intent intent = new Intent(getApplicationContext(), myGoalsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_goal, menu);
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

