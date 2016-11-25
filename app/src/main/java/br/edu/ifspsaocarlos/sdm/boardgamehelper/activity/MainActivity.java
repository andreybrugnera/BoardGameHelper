package br.edu.ifspsaocarlos.sdm.boardgamehelper.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.boardgamehelper.R;
import br.edu.ifspsaocarlos.sdm.boardgamehelper.adapter.FunctionAdapter;
import br.edu.ifspsaocarlos.sdm.boardgamehelper.model.HelperFunction;

public class MainActivity extends ListActivity {
    private List<HelperFunction> functionList;
    private FunctionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Gera as funções do app
        generateFunctionList();

        //Cria o adapter
        createListAdapter();
    }

    /**
     * Cria e adiciona as funções
     * disponíveis no list
     */
    private final void generateFunctionList() {
        this.functionList = new ArrayList();

        HelperFunction rollTheDice = new HelperFunction(getResources().getString(R.string.function_dice), RollTheDiceActivity.class, R.mipmap.dice);
        HelperFunction timer = new HelperFunction(getResources().getString(R.string.function_timer), TimerActivity.class, R.mipmap.timer);
        HelperFunction stopwatch = new HelperFunction(getResources().getString(R.string.function_stopwatch), StopwatchActivity.class, R.mipmap.stop_watch);

        functionList.add(rollTheDice);
        functionList.add(timer);
        functionList.add(stopwatch);
    }

    /**
     * Cria e seta o adapter da view
     */
    private final void createListAdapter() {
        this.adapter = new FunctionAdapter(this, functionList);
        setListAdapter(adapter);
    }

    /**
     * Ação disparada quando
     * uma função é selecionada na listView
     *
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        HelperFunction func = (HelperFunction) getListAdapter().getItem(position);
        //Abre a activity responsável por controlar a função selecionada
        Intent intent = new Intent(this, func.getActivity());
        startActivity(intent);
    }
}
