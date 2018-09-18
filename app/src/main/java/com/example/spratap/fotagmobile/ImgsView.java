package com.example.spratap.fotagmobile;

import android.view.View;
import android.content.Context;
import android.widget.BaseAdapter;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import java.util.ArrayList;

public class ImgsView extends BaseAdapter {
    public Context context;
    public Model model;
    public ArrayList<ImgView> imgViews;
    public ArrayList<ImageModel> filteredImageModels;

    public ImgsView(Context context, Model model) {
        this.context = context;
        this.model = model;
        this.imgViews = new ArrayList<>();
        getImagesToDisplay();

    }

    public void getImagesToDisplay() {
        this.filteredImageModels = new ArrayList<>();
        if (this.model.FilterValue > 0) {
            for (ImageModel imageModel: this.model.imageModels) {
                if (imageModel.userRate >= this.model.FilterValue) {
                    this.filteredImageModels.add(imageModel);
                }
            }
        }
        else {
            this.filteredImageModels = this.model.imageModels;
        }
    }

    public int getCount() {
        return this.filteredImageModels.size();
    }

    public Object getItem(int position) {
        return this.filteredImageModels.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public ImgView getImgViewForAView(View view) {
        for (ImgView imgView : this.imgViews) {
            if (imgView.view == view) {
                return imgView;
            }
        }

        return null;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            View view = LayoutInflater.from(this.context).inflate(R.layout.image_view, parent, false);

            ImgView imgView = new ImgView(this.filteredImageModels.get(position), view, this.context);

            this.imgViews.add(imgView);

            imgView.update(0);

            return imgView.view;
        }
        else {
            ImgView imgView = getImgViewForAView(convertView);
            imgView.imageModel = filteredImageModels.get(position);
            imgView.update(0);

            return convertView;
        }
    }
}
