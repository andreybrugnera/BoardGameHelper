package br.edu.ifspsaocarlos.sdm.boardgamehelper.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;

import br.edu.ifspsaocarlos.sdm.boardgamehelper.R;
import br.edu.ifspsaocarlos.sdm.boardgamehelper.model.TimerHelper;

/**
 * Cronômetro
 */
public class StopwatchActivity extends DefaultTimerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        this.txtViewTimer = (TextView) findViewById(R.id.txt_stopwatch);
        this.btStart = (Button) findViewById(R.id.bt_start_stopwatch);
        this.btStop = (Button) findViewById(R.id.bt_stop_stopwatch);
        this.btPause = (Button) findViewById(R.id.bt_pause_stopwatch);
        this.btResume = (Button) findViewById(R.id.bt_resume_stopwatch);

        //Se recritou a tela (girou a tela), recupera o estado anterior
        if (savedInstanceState != null) {
            this.timerHelper = (TimerHelper) savedInstanceState.getSerializable(TIMER_HELPER);
            this.timerState = savedInstanceState.getInt(TIMER_STATE);
            this.timer = new Timer();
        } else {
            //Cria o timer
            createTimer();
        }
    }

    /**
     * Se a view precisar ser recriada (girou a tela, por exemplo)
     * salva o estado do cronômetro e termina a thread de
     * execução do timer se ele estiver rodando.
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Guarda o objeto timer
        outState.putSerializable(TIMER_HELPER, this.timerHelper);
        //Guarda o estado do timer (em execução, pausado, etc.)
        outState.putInt(TIMER_STATE, this.timerState);
        cancelTimer();
    }
}