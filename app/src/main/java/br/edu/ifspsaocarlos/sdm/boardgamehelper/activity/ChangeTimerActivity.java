package br.edu.ifspsaocarlos.sdm.boardgamehelper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import br.edu.ifspsaocarlos.sdm.boardgamehelper.R;

public class ChangeTimerActivity extends Activity {
    private Spinner spinnerNumberOfSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_timer);

        this.spinnerNumberOfSeconds = (Spinner)findViewById(R.id.spinner_numer_of_seconds);
    }

    public void changeTimer(View view){
        //Se acionar o botão "back" o resultado será CANCELED
        int result = RESULT_CANCELED;
        Intent intentRet = new Intent();
        if(view == findViewById(R.id.bt_ok)){
            String selectedNumber = (String)this.spinnerNumberOfSeconds.getSelectedItem();
            result = RESULT_OK;
            intentRet.putExtra(TimerActivity.NUMBER_OF_SECONDS, Integer.valueOf(selectedNumber));
        }
        //Envia resultado para activity que chamou
        setResult(result,intentRet);
        //Termina activity
        finish();
    }
}
