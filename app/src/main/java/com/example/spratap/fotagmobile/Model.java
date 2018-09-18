package com.example.spratap.fotagmobile;

import java.util.ArrayList;
import android.content.Context;


public class Model implements java.io.Serializable{
    /** The observers that are watching this model for changes. */
    private ArrayList<Observer> observers;
    public transient ArrayList<ImageModel> imageModels;
    public transient int FilterValue;
    public transient Context context;



    /**
     * Create a new model.
     */
    public Model(Context context) {

        this.observers = new ArrayList<>();
        this.imageModels = new ArrayList<>();
        this.FilterValue = 0;
        this.context = context;
    }

    /**
     * Add an observer to be notified when this model changes.
     */
    public void addObserver(Observer observer) {

        this.observers.add(observer);
    }

    /**
     * Remove an observer from this model.
     */
    public void removeObserver(Observer observer) {

        this.observers.remove(observer);
    }

    /**
     * Notify all observers that the model has changed.
     */
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(this);
        }
    }

    public void addImage(ImageModel imageModel){

        this.imageModels.add(imageModel);
        this.notifyObservers();
    }

    public void showDefaultImages() {
        this.imageModels.add(new ImageModel(this, this.context, R.drawable.batman));
        this.imageModels.add(new ImageModel(this, this.context, R.drawable.charmander));
        this.imageModels.add(new ImageModel(this, this.context, R.drawable.deadpool));
        this.imageModels.add(new ImageModel(this, this.context, R.drawable.duck));
        this.imageModels.add(new ImageModel(this, this.context, R.drawable.fries));
        this.imageModels.add(new ImageModel(this, this.context, R.drawable.ironman));
        this.imageModels.add(new ImageModel(this, this.context, R.drawable.mario));
        this.imageModels.add(new ImageModel(this, this.context, R.drawable.minion));
        this.imageModels.add(new ImageModel(this, this.context, R.drawable.pooh));
        this.imageModels.add(new ImageModel(this, this.context, R.drawable.stitch));
        this.imageModels.add(new ImageModel(this, this.context, R.drawable.yinyang));
        this.notifyObservers();
    }

    public void clearImages() {
        this.imageModels = new ArrayList<>();
        this.notifyObservers();
    }

    public void setFilterValue(int value) {
        this.FilterValue = value;
        this.notifyObservers();
    }

}
