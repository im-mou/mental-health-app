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
    // Clase auxiliar que recibe los valores de fuera y construye la lista de recomendación
    private Activity context;
    private HomeFragment.Rec_list rec_list[];
    // Obtiene el contexto, títulos, descripciones y paths de las fotos
    public RecomendationListAdapter(Activity context, HomeFragment.Rec_list[] rec_list) {
        super(context, R.layout.recomendation_list_item, rec_list);
        this.context = context;
        this.rec_list = rec_list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();

        if(convertView==null)
            row = inflater.inflate(R.layout.recomendation_list_item, null, true);

        // Enlaza los items a variables locales
        TextView textViewTitle = (TextView) row.findViewById(R.id.rec_item_title);
        TextView textViewDesc = (TextView) row.findViewById(R.id.rec_item_description);
        SimpleDraweeView imageAvatar = (SimpleDraweeView) row.findViewById(R.id.rec_item_image);

        // Cambia los textos por los obtenidos
        textViewTitle.setText(rec_list[position].getTitulo());
        textViewDesc.setText(rec_list[position].getDescription());
        imageAvatar.setImageURI(rec_list[position].getIcon());
        return row;
    }
}