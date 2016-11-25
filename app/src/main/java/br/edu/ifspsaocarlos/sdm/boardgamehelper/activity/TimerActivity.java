package br.edu.ifspsaocarlos.sdm.boardgamehelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import br.edu.ifspsaocarlos.sdm.boardgamehelper.R;
import br.edu.ifspsaocarlos.sdm.boardgamehelper.model.TimerHelper;

/**
 * Timer
 */
public class TimerActivity extends DefaultTimerActivity {
    //Número de segundos do timer, o padrão é 30
    private final int DEFAULT_SECONDS = 30;
    //Guarda o número de milisegundos iniciais
    private int oldSeconds;

    private static final int INTENT_SET_NUMBER_OF_SECONDS = 1;
    public static final String NUMBER_OF_SECONDS = "NUMBER_OF_SECONDS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        this.txtViewTimer = (TextView) findViewById(R.id.txt_timer);
        this.btStart = (Button) findViewById(R.id.bt_start_timer);
        this.btStop = (Button) findViewById(R.id.bt_stop_timer);
        this.btPause = (Button) findViewById(R.id.bt_pause_timer);
        this.btResume = (Button) findViewById(R.id.bt_resume_timer);

        //Se recritou a tela (girou a tela), recupera o estado anterior
        if (savedInstanceState != null) {
            this.timerHelper = (TimerHelper) savedInstanceState.getSerializable(TIMER_HELPER);
            this.oldSeconds = savedInstanceState.getInt(NUMBER_OF_SECONDS);
            this.timerState = savedInstanceState.getInt(TIMER_STATE);
            this.timer = new Timer();
        } else {
            //Cria o timer
            createTimer();
        }
    }

    /**
     * Se a view precisar ser recriada (girou a tela, por exemplo)
     * salva o estado do timer e termina a thread de
     * execução do timer se ele estiver rodando.
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Guarda o objeto timer
        outState.putSerializable(TIMER_HELPER, this.timerHelper);
        //Guarda o número de milisegundos inicial
        outState.putInt(NUMBER_OF_SECONDS, this.oldSeconds);
        //Guarda o estado do timer (em execução, pausado, etc.)
        outState.putInt(TIMER_STATE, this.timerState);
        cancelTimer();
    }

    /**
     * Cria o objeto timer
     * com o número de segundos
     */
    @Override
    protected void createTimer() {
        this.timerHelper = new TimerHelper(DEFAULT_SECONDS);
    }

    /**
     * Inicia o timer
     *
     * @param v
     */
    @Override
    protected void startTimer(View v) {
        if (this.timer == null) {
            timer = new Timer();
        }
        //Guarda o total de segundos iniciais
        this.oldSeconds = this.timerHelper.getSeconds();
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
    @Override
    protected void stopTimer(View v) {
        cancelTimer();
        this.timerState = STATE_STOPPED;
        //Volta os segundos iniciais
        timerHelper.setSeconds(oldSeconds);
        updateTimer();
        updateView();
    }

    /**
     * Abre nova activity para
     * alterar o númro de segundos.
     * O timer será interrompido se estiver rodando.
     *
     * @param v
     */
    public void changeTimer(View v) {
        //Interrompe timer se estiver rodando
        if (timerState == STATE_RUNNING || timerState == STATE_PAUSED) {
            stopTimer(null);
        }
        Intent intent = new Intent(this, ChangeTimerActivity.class);
        startActivityForResult(intent, INTENT_SET_NUMBER_OF_SECONDS);
    }

    /**
     * Cria TimerTask que irá atualizar
     * a view com o valor do timer a cada 1 segundo
     */
    @Override
    protected void createTimerTask() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        timerHelper.decreaseTimer();
                        //Para timer quando chegar em zero
                        if (timerHelper.getSeconds() == 0) {
                            //Cancela thread que execute o timer
                            cancel();
                            timerState = STATE_STOPPED;
                            //Volta os segundos iniciais
                            timerHelper.setSeconds(oldSeconds);
                        }
                        updateTimer();
                        updateView();
                    }
                });
            }
        };
    }

    /**
     * Altera o valor dos segundos
     * de acordo com o valor vindo da activity ChangeTimerActivity
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == INTENT_SET_NUMBER_OF_SECONDS) {
                //Guarda o novo tempo selecionado
                this.oldSeconds = data.getIntExtra(NUMBER_OF_SECONDS, DEFAULT_SECONDS);
                this.timerHelper.setSeconds(oldSeconds);
                updateTimer();
                updateView();
            }
        }
    }
}
