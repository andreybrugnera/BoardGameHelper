package br.edu.ifspsaocarlos.sdm.boardgamehelper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

import br.edu.ifspsaocarlos.sdm.boardgamehelper.R;
import br.edu.ifspsaocarlos.sdm.boardgamehelper.model.Dice;

public class RollTheDiceActivity extends Activity {
    private Dice[] dices;
    private ImageView diceImage1;
    private ImageView diceImage2;
    private TextToSpeech speech;
    //O default é sempre 2 dados
    private int numberOfDices = 2;

    private static final int INTENT_SET_NUMBER_OF_DICES = 1;
    public static final String NUMBER_OF_DICES = "NUMBER_OF_DICES";
    public static final String DICE_1 = "DICE_1";
    public static final String DICE_2 = "DICE_2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_the_dice);

        //Cria dados
        createDices();

        this.diceImage1 = (ImageView) findViewById(R.id.dice_image_1);
        this.diceImage2 = (ImageView) findViewById(R.id.dice_image_2);

        if(savedInstanceState != null){
            /* Se a view foi recriada, recupera o número de dados
               anterior e seus valores */
            this.numberOfDices = savedInstanceState.getInt(NUMBER_OF_DICES);
            this.dices[0] = (Dice)savedInstanceState.getSerializable(DICE_1);
            this.dices[1] = (Dice)savedInstanceState.getSerializable(DICE_2);
            this.diceImage1.setImageResource(dices[0].getImage());
            this.diceImage2.setImageResource(dices[1].getImage());
            updateView();
        }else{
            //Joga os dados
            roolTheDices();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupSpeak();
    }

    /**
     * Cria objeto speech para
     * falar os valores dos dados usando voz
     */
    private void setupSpeak(){
        speech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    speech.setLanguage(Locale.getDefault());
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Quando a activity é recriada, salva a quantidade de dados
        outState.putInt(NUMBER_OF_DICES, numberOfDices);
        outState.putSerializable(DICE_1, dices[0]);
        outState.putSerializable(DICE_2, dices[1]);
    }

    /**
     * Cria os dois dados
     */
    private void createDices(){
        dices = new Dice[2];
        dices[0] = new Dice();
        dices[1] = new Dice();
    }

    /**
     * Joga os dados
     */
    private void roolTheDices(){
        rollTheDice(dices[0], this.diceImage1);
        rollTheDice(dices[1], this.diceImage2);
    }

    /**
     * Joga o dado especificado e
     * altera a imagem representativa na view
     * @param dice
     * @param image
     */
    private void rollTheDice(Dice dice, ImageView image) {
        dice.rollTheDice();
        image.setImageResource(dice.getImage());
    }

    /**
     * Chama a ação
     * de jogas os dados e fala
     * os números selecionados
     *
     * @param v
     */
    public void actionRollTheDice(View v) {
        roolTheDices();
        speak();
    }

    /**
     * Abre nova activity para
     * selecionar o número de dados
     * @param v
     */
    public void actionSelectNumberOfDices(View v){
        Intent intent = new Intent(this, NumberOfDicesActivity.class);
        //Manda quantos dados estão sendo exibidos agora
        intent.putExtra(NUMBER_OF_DICES, this.numberOfDices);
        startActivityForResult(intent,INTENT_SET_NUMBER_OF_DICES);
    }

    /**
     * Altera o valor do número de dados
     * de acordo com o valor vindo da activity NumberOfDicesActivity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == INTENT_SET_NUMBER_OF_DICES){
                this.numberOfDices = data.getIntExtra(NUMBER_OF_DICES,2);
                updateView();
                Toast.makeText(this, R.string.number_of_dices_changed, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Atualiza view de acordo
     * com o número de dados selecionado
     */
    private void updateView(){
        if(this.numberOfDices == 1){
            this.diceImage1.setVisibility(View.VISIBLE);
            this.diceImage2.setVisibility(View.GONE);
        }else if(this.numberOfDices == 2){
            this.diceImage1.setVisibility(View.VISIBLE);
            this.diceImage2.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Diz os valores dos dados
     * no idioma padrão do aparelho
     */
    private void speak() {
        String text = String.valueOf(dices[0].getNumber());
        if(numberOfDices == 2){
            text += "  "+String.valueOf(dices[1].getNumber());
        }
        speech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (speech != null) {
            speech.stop();
            speech.shutdown();
        }
    }
}
