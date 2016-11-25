package br.edu.ifspsaocarlos.sdm.boardgamehelper.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.boardgamehelper.R;
import br.edu.ifspsaocarlos.sdm.boardgamehelper.model.HelperFunction;

/**
 * Created by Andrey on 07/11/2016.
 */
public class FunctionAdapter extends ArrayAdapter<HelperFunction> {

    private LayoutInflater inflater;

    public FunctionAdapter(Activity activity, List<HelperFunction> functionList) {
        super(activity, R.layout.function_layout, functionList);
        inflater = activity.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.function_layout,null);
        }

        ImageView imageV = (ImageView)convertView.findViewById(R.id.function_image);
        TextView textV = (TextView)convertView.findViewById(R.id.function_name);

        HelperFunction func = getItem(position);
        textV.setText(func.getFunctionName());
        imageV.setImageResource(func.getImage());

        return convertView;
    }
}
