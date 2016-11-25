package br.edu.ifspsaocarlos.sdm.boardgamehelper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import br.edu.ifspsaocarlos.sdm.boardgamehelper.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread splashScreenThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(5000);
                } catch (Exception e) {

                } finally {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        splashScreenThread.start();
    }
}
