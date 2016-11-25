package br.edu.ifspsaocarlos.sdm.boardgamehelper.model;

import java.io.Serializable;

/**
 * Created by Andrey on 15/11/2016.
 */
public class TimerHelper implements Serializable {
    private int seconds;

    public TimerHelper() {
    }

    public TimerHelper(int seconds) {
        setSeconds(seconds);
    }

    /**
     * Atualiza timer com o novo
     * número de segundos
     *
     * @param seconds número de segundos, deve
     *                ser maior ou igual a zero
     */
    public final void setSeconds(int seconds) {
        if (seconds >= 0) {
            this.seconds = seconds;
        } else {
            this.seconds = 0;
        }
    }

    /**
     * Retorna o tempo no formato hh:mm:ss
     * de acordo com o número de segundos
     *
     * @return valor do timer no formato hh:mm:ss
     */
    public String getTimer() {
        String timer = "00:00:00";
        if (seconds >= 0) {
            long h = seconds / 3600;
            long m = (seconds / 60) % 60;
            long s = seconds % 60;
            timer = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
        }
        return timer;
    }

    /**
     * Aumenta timer em 1 segundo
     */
    public void increaseTimer(){
        seconds++;
    }

    /**
     * Diminui timer em 1 segundo
     */
    public void decreaseTimer(){
        if (seconds > 0) {
            seconds--;
        }
    }

    public int getSeconds() {
        return seconds;
    }
}
