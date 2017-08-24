package diploma.gyumri.theatre.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_splash);
//        Timer RunSplash = new Timer();
//        long Delay = 1000;

        // Task to do when the timer ends
//        TimerTask ShowSplash = new TimerTask() {
//            @Override
//            public void run() {
//                // Close SplashScreenActivity.class
//                finish();
//
//                // Start MainActivity.class
//                Intent myIntent = new Intent(SplashActivity.this,
//                        MainActivity.class);
//                startActivity(myIntent);
//            }
//        };
//
//        // Start the timer
//        RunSplash.schedule(ShowSplash, Delay);
//        setContentView(R.layout.activity_splash);
//        Intent intent = new Intent(this, MainActivity.class);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                /* Create an Intent that will start the Menu-Activity. */
//                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
//                SplashActivity.this.startActivity(mainIntent);
//                SplashActivity.this.finish();
//            }
//        }, SPLASH_DISPLAY_LENGTH);
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
