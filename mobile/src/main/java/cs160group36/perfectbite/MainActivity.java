package cs160group36.perfectbite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
                // Switch between database, metrics, and setting depending on current settings
                if (false) {
                    Intent i = new Intent(v.getContext(), DatabaseActivity.class);
                    startActivity(i);
                }
                else if (false) {
                    Intent i = new Intent(v.getContext(), MetricsActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(v.getContext(), SettingsActivity.class);
                    startActivity(i);
                }
            }
        });
//        Intent intent = new Intent(this, RecommendationService.class);
//        startService(intent);
    }
}
