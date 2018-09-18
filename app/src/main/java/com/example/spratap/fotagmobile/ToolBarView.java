package com.example.spratap.fotagmobile;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.app.Activity;
import android.content.Context;
import android.widget.RatingBar;

public class ToolBarView implements Observer {
    public Model model;
    public Context context;
    public View view;
    public boolean imagesDisplayed;
    public RatingBar filterBar;

    public ToolBarView(final Model model, Context context) {
        this.model = model;
        this.context = context;
        this.model.addObserver(this);
        this.view = ((Activity) context).findViewById(R.id.tool_bar_view);
        this.filterBar = (RatingBar) this.view.findViewById(R.id.filter_bar);
        this.filterBar.setIsIndicator(false);
        this.filterBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float value, boolean b) {
                model.setFilterValue((int)value);
            }
        });

        ImageButton clearBtn = this.view.findViewById(R.id.clearbtn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.clearImages();
                imagesDisplayed = false;

            }
        });

        ImageButton loadBtn = this.view.findViewById(R.id.loadbtn);
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imagesDisplayed == false) {
                    model.showDefaultImages();
                    imagesDisplayed = true;
                }
            }
        });

        ImageButton refreshBtn = this.view.findViewById(R.id.refreshbtn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setFilterValue(0);
            }
        });

    }

    public void update(Object observable) {
        filterBar.setRating(this.model.FilterValue);
    }



}
