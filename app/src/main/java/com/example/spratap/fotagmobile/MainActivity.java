package com.example.spratap.fotagmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Observer{

    public Model model;
    public GridView galleryView;
    public ToolBarView toolBarView;
    public ArrayList<ImgView> imgViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            this.model = (Model) savedInstanceState.getSerializable("model");
            this.model.context = this;
        } else {
            this.model = new Model(this);
        }

        this.model.addObserver(this);
        imgViews = new ArrayList<>();

        setContentView(R.layout.activity_main);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.searchbtn);
//        fab.setOnClickListener(new View.OnClickListener() { // url loader
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setTitle("Enter a url to load image from");
//
//                final EditText input = new EditText(MainActivity.this);
//
//                input.setInputType(InputType.TYPE_CLASS_TEXT);
//                builder.setView(input);
//
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String url = input.getText().toString();
//                        model.addImage(new ImageModel(url, MainActivity.this, MainActivity.this.model));
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//                builder.show();
//            }
//        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        this.galleryView = (GridView) findViewById(R.id.gridview);

        galleryView.setAdapter(new ImgsView(this, this.model));


        this.toolBarView = new ToolBarView(this.model, this);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.clear();
        savedInstanceState.putSerializable("model", this.model);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            galleryView.setNumColumns(-1);
        }
        else {
            galleryView.setNumColumns(1);
        }
    }

    public void update(Object observable) {
        ((ImgsView)this.galleryView.getAdapter()).getImagesToDisplay();
        ((ImgsView)this.galleryView.getAdapter()).notifyDataSetChanged();
    }
}
