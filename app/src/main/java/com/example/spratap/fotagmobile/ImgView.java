package com.example.spratap.fotagmobile;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.view.View;
import android.widget.ImageView;
import android.app.Dialog;
import android.view.ViewGroup;

public class ImgView extends LinearLayout implements Observer {

    //public Model model;
    public ImageModel imageModel;
    public View view;
    public RatingBar rateBar;
    public ImageView imageView;
    public Context context;

    public ImgView(final ImageModel imageModel, View view, Context context) {
        super(context);
        this.imageModel = imageModel;
        this.view = view;
        this.context = context;

        this.imageView = (ImageView)this.view.findViewById(R.id.image_view);

        this.imageView.setImageResource(this.imageModel.imageID);





    }
    public void update(Object observable) {
        this.rateBar = (RatingBar) this.view.findViewById(R.id.rating_bar);
        this.rateBar.setRating(this.imageModel.userRate);

        this.rateBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                imageModel.updateUserRate((int) rating);
            }
        });

        imageView = (ImageView) this.view.findViewById(R.id.image_view);
        imageView.setImageBitmap(this.imageModel.bitmap);

        this.view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog fullScreenImage = new Dialog(view.getContext(), R.style.FullScreenDialog);
                fullScreenImage.setContentView(R.layout.image_full_screen_view);
                ImageView iview = (ImageView) fullScreenImage.findViewById(R.id.image_full_screen_view);
                iview.setImageBitmap(imageModel.bitmap);
                //iview.setImageResource(imageModel.imageID);
                iview.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fullScreenImage.dismiss();
                    }
                });
                fullScreenImage.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                fullScreenImage.setCancelable(true);
                fullScreenImage.show();
            }
        });

        ImageButton clearBtn = this.view.findViewById(R.id.clearbtn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageModel.updateUserRate(0);

            }
        });
    }
}
