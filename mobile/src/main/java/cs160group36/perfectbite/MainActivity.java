package cs160group36.perfectbite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView img = (ImageView) findViewById(R.id.imageView);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Switch between metrics and setting depending on current settings
                if (true) {
                    Intent i = new Intent(v.getContext(), MetricsActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(v.getContext(), SettingsActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}
