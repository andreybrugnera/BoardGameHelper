package br.edu.ifspsaocarlos.sdm.boardgamehelper.model;

/**
 * Represents an available function
 * Created by Andrey on 07/11/2016.
 */

public class HelperFunction {
    private final int image;
    private final String functionName;
    private final Class<?> activity;

    public HelperFunction(String functionName, Class<?> activity, int image) {
        this.functionName = functionName;
        this.activity = activity;
        this.image = image;
    }

    public String getFunctionName() {
        return functionName;
    }

    public Class<?> getActivity() {
        return activity;
    }

    public int getImage() {
        return image;
    }
}
