package com.example.spratap.fotagmobile;



import java.util.ArrayList;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class ImageModel implements java.io.Serializable {

    /** The observers that are watching this model for changes. */
    public transient ArrayList<Observer> observers;

    public transient Model model;

    public int userRate;
    public int imageID;
    public String URL;
    public transient Bitmap bitmap;
    public transient Context context;


    public ImageModel(Model model, Context context, int imageID) {
        this.model = model;
        this.context = context;
        this.imageID = imageID;

        this.observers = new ArrayList<>();
        this.userRate = 0;
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), imageID);
        this.URL = "";

    }

    public ImageModel(Model model, Context context, String URL) {
        this.model = model;
        this.context = context;
        this.URL = URL;

        this.observers = new ArrayList<>();
        this.userRate = 0;
        this.imageID = -1;
        new ImageDownload(this).execute(this.URL);

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

    class ImageDownload extends AsyncTask<String, Void, Bitmap> {
        ImageModel imageModel;
        ImageDownload(ImageModel imageModel) {
            this.imageModel = imageModel;
        }

        protected Bitmap doInBackground(String... URLS) {
            String URL = URLS[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new java.net.URL(URL).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            }
            catch (Exception exception) {
                Log.e("Error downloading Image ", exception.getMessage());

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        model.imageModels.remove(imageModel);
                        model.notifyObservers();
                    }
                });

            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap bitmap) {
            this.imageModel.bitmap = bitmap;
            this.imageModel.notifyObservers();
            model.notifyObservers();
        }
    }

    public void updateUserRate(int newUserRate) {
        this.userRate = newUserRate;
        this.notifyObservers();
        this.model.notifyObservers();
    }
}
