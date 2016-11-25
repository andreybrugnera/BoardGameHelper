package br.edu.ifspsaocarlos.sdm.boardgamehelper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import br.edu.ifspsaocarlos.sdm.boardgamehelper.R;

public class NumberOfDicesActivity extends Activity {
    private Spinner spinnerNumberOfDices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_of_dices);
        //Pega qual é o valor atual de número de dados
        int numDices = getIntent().getIntExtra(RollTheDiceActivity.NUMBER_OF_DICES,2);

        this.spinnerNumberOfDices = (Spinner)findViewById(R.id.spinner_number_of_dices);
        this.spinnerNumberOfDices.setSelection(numDices - 1);
    }

    public void changeNumberOfDices(View view){
        //Se acionar o botão "back" o resultado será CANCELED
        int result = RESULT_CANCELED;
        Intent intentRet = new Intent();
        if(view == findViewById(R.id.bt_ok)){
            String selectedNumber = (String)this.spinnerNumberOfDices.getSelectedItem();
            result = RESULT_OK;
            intentRet.putExtra(RollTheDiceActivity.NUMBER_OF_DICES, Integer.valueOf(selectedNumber));
        }
        //Envia resultado para activity que chamou
        setResult(result,intentRet);
        //Termina activity
        finish();
    }
}
