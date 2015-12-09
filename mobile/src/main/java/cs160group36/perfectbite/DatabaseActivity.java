package cs160group36.perfectbite;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static android.database.DatabaseUtils.dumpCursorToString;

public class DatabaseActivity extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper myDbHelper;
    SQLiteDatabase myDb;
    TextView results, results2;
    EditText editName, edit1, edit2, edit3, edit4, edit5, edit6, edit7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        myDbHelper = new DatabaseHelper(this);
        myDb = myDbHelper.getWritableDatabase();

        results = (TextView) findViewById(R.id.textView3);
        results2 = (TextView) findViewById(R.id.textView12);
        edit1 = (EditText) findViewById(R.id.editText2);
        edit2 = (EditText) findViewById(R.id.editText3);
        edit3 = (EditText) findViewById(R.id.editText4);
        edit4 = (EditText) findViewById(R.id.editText5);
        edit5 = (EditText) findViewById(R.id.editText6);
        edit6 = (EditText) findViewById(R.id.editText7);
        edit7 = (EditText) findViewById(R.id.editText8);

        Button insertButton = (Button) findViewById(R.id.button);
        insertButton.setOnClickListener(this);
        ImageView settings = (ImageView) findViewById(R.id.settingsView);
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), myGoalsActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.button2:
//                if (editName.getText().toString().equals("")) {
//                    results.setText("Please input a food name above.");
//                }
//                else {
//                    Cursor c = myDbHelper.fetchDataFromName(myDb, editName.getText().toString());
//                    if (!c.moveToFirst()) {
//                        results.setText("No such entry found.");
//                    }
//                    else {
//                        String s = dumpCursorToString(c);
//                        results.setText(s);
//                    }
//                    c.close();
//                }
//                break;
            case R.id.button:
                String s1 = edit1.getText().toString();
                String s2 = edit2.getText().toString();
                String s3 = edit3.getText().toString();
                String s4 = edit4.getText().toString();
                String s5 = edit5.getText().toString();
                String s6 = edit6.getText().toString();
                String s7 = edit7.getText().toString();
                if (s1.equals("") || s2.equals("") || s3.equals("") || s4.equals("") || s5.equals("") || s6.equals("") || s7.equals("")) {
                    results2.setText("Incomplete information.");
                }
                else {
                    try {
                        boolean success = myDbHelper.insertData(myDb, s1, s2, Integer.parseInt(s3), Integer.parseInt(s4), Integer.parseInt(s5), Integer.parseInt(s6), Integer.parseInt(s7));
                        if (success) {
                            results2.setText("Success.");
                        }
                        else {
                            results2.setText("Failure.");
                        }
                    } catch(NumberFormatException nfe) {
                        results2.setText("Unexpected string instead of integer.");
                    }
                }
                break;
            default:
                break;
        }
    }
}
