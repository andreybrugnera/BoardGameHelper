package br.edu.ifspsaocarlos.sdm.boardgamehelper.util.number;

import java.util.Random;

/**
 * Created by Andrey on 07/11/2016.
 */

public class NumberUtils {

    /**
     * Generates a number between 1 and max
     * @param max
     * @return a number between 1 and max
     */
    public static int generateRandomNumber(int max){
        Random rdn = new Random();
        int ret = rdn.nextInt(max) + 1;
        return ret;
    }
}
