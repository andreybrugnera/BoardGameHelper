package br.edu.ifspsaocarlos.sdm.boardgamehelper.model;

import java.io.Serializable;

import br.edu.ifspsaocarlos.sdm.boardgamehelper.R;
import br.edu.ifspsaocarlos.sdm.boardgamehelper.util.number.NumberUtils;

/**
 * Created by Andrey on 09/11/2016.
 */

public class Dice implements Serializable{
    private int number = 1;
    private int image = R.mipmap.dice_1;

    public void rollTheDice(){
        this.number = NumberUtils.generateRandomNumber(6);
        if(this.number == 1){
            this.image = R.mipmap.dice_1;
        }else if(this.number == 2){
            this.image = R.mipmap.dice_2;
        }else if(this.number == 3){
            this.image = R.mipmap.dice_3;
        }else if(this.number == 4){
            this.image = R.mipmap.dice_4;
        }else if(this.number == 5){
            this.image = R.mipmap.dice_5;
        }else{
            this.image = R.mipmap.dice_6;
        }
    }

    public int getNumber(){
        return number;
    }

    public int getImage() {
        return image;
    }
}
