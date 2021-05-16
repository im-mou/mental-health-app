package com.proyectosm.mentalhealthapp.ui.home;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.proyectosm.mentalhealthapp.R;

public class RecomendationListAdapter extends ArrayAdapter {
    private String[] titles;
    private String[] descriptions;
    private Uri[] imageuri;
    private Activity context;

    public RecomendationListAdapter(Activity context, String[] titles, String[] descriptions, Uri[] imageuri) {
        super(context, R.layout.recomendation_list_item, titles);
        this.context = context;
        this.titles = titles;
        this.descriptions = descriptions;
        this.imageuri = imageuri;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();

        if(convertView==null)
            row = inflater.inflate(R.layout.recomendation_list_item, null, true);

        TextView textViewTitle = (TextView) row.findViewById(R.id.rec_item_title);
        TextView textViewDesc = (TextView) row.findViewById(R.id.rec_item_description);
        SimpleDraweeView imageAvatar = (SimpleDraweeView) row.findViewById(R.id.rec_item_image);

        textViewTitle.setText(titles[position]);
        textViewDesc.setText(descriptions[position]);
        imageAvatar.setImageURI(imageuri[position]);
        return row;
    }
}