package br.edu.ifspsaocarlos.sdm.boardgamehelper.activity;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import br.edu.ifspsaocarlos.sdm.boardgamehelper.model.TimerHelper;

/**
 * View padrão para representação de um timer
 * Created by Andrey on 16/11/2016.
 */
public class DefaultTimerActivity extends Activity {
    protected TimerHelper timerHelper;
    protected TextView txtViewTimer;
    protected Button btStart;
    protected Button btPause;
    protected Button btStop;
    protected Button btResume;

    //O timer é excecutado com threads
    protected Timer timer;
    protected TimerTask timerTask;
    protected final Handler handler = new Handler();

    //Estado do timer
    protected final int STATE_PAUSED = 0;
    protected final int STATE_STOPPED = 1;
    protected final int STATE_RUNNING = 2;
    protected int timerState = STATE_STOPPED;
    protected static final String TIMER_STATE = "TIMER_STATE";
    protected static final String TIMER_HELPER = "TIMER_HELPER";

    @Override
    protected void onResume() {
        super.onResume();
        if (this.timerState == STATE_RUNNING) {
            resumeTimer(null);
        }
        //Atualiza a view com o valor do timer
        updateTimer();
        updateView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cancelTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cancelTimer();
    }

    /**
     * Atualiza o textView do timer de acordo
     * com o valor atual
     */
    protected void updateTimer() {
        this.txtViewTimer.setText(this.timerHelper.getTimer());
    }

    /**
     * Inicia o timer
     *
     * @param v
     */
    protected void startTimer(View v) {
        if (this.timer == null) {
            timer = new Timer();
        }
        createTimerTask();
        this.timer.schedule(timerTask, 0, 1000);
        this.timerState = STATE_RUNNING;
        updateView();
    }

    /**
     * Pausa o timer
     *
     * @param v
     */
    protected void pauseTimer(View v) {
        this.timerTask.cancel();
        this.timerState = STATE_PAUSED;
        updateView();
    }

    /**
     * Continua execução do timer
     *
     * @param v
     */
    protected void resumeTimer(View v) {
        createTimerTask();
        this.timer.schedule(timerTask, 0, 1000);
        this.timerState = STATE_RUNNING;
        updateView();
    }

    /**
     * Interrompe o timer
     *
     * @param v
     */
    protected void stopTimer(View v) {
        cancelTimer();
        this.timerState = STATE_STOPPED;
        timerHelper.setSeconds(0);
        updateTimer();
        updateView();
    }

    /**
     * Atualiza view de
     * acordo com o estado do timer
     */
    protected void updateView() {
        switch (timerState) {
            case STATE_STOPPED:
                this.btStart.setVisibility(View.VISIBLE);
                this.btPause.setVisibility(View.GONE);
                this.btStop.setVisibility(View.GONE);
                this.btResume.setVisibility(View.GONE);
                break;
            case STATE_PAUSED:
                this.btStart.setVisibility(View.GONE);
                this.btPause.setVisibility(View.GONE);
                this.btStop.setVisibility(View.VISIBLE);
                this.btResume.setVisibility(View.VISIBLE);
                break;
            case STATE_RUNNING:
                this.btStart.setVisibility(View.GONE);
                this.btPause.setVisibility(View.VISIBLE);
                this.btStop.setVisibility(View.VISIBLE);
                this.btResume.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * Cria TimerTask que irá atualizar
     * a view com o valor do timer a cada 1 segundo
     */
    protected void createTimerTask() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        timerHelper.increaseTimer();
                        updateTimer();
                        updateView();
                    }
                });
            }
        };
    }

    /**
     * Cria o objeto timer
     */
    protected void createTimer() {
        this.timerHelper = new TimerHelper();
    }

    /**
     * Cancela execução do timer
     */
    protected void cancelTimer() {
        if (timerState == STATE_RUNNING || timerState == STATE_PAUSED) {
            if (this.timerTask != null) {
                this.timerTask.cancel();
            }
        }
    }
}
