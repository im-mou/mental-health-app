package com.proyectosm.mentalhealthapp.ui.dashboard;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.proyectosm.mentalhealthapp.R;

import java.util.ArrayList;

public class ChatListAdapter extends ArrayAdapter<ChatModel> {
    private Activity context;
    private ArrayList<ChatModel> journals;

    public ChatListAdapter(Activity context, ArrayList<ChatModel> journals) {
        super(context, R.layout.interest_list_item, journals);
        this.context = context;
        this.journals = journals;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Recupera los datos según la posición
        ChatModel chatModel = getItem(position);

        // Comprueba si hay otra view
        if (convertView == null) {
            if(chatModel.left == true) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_left_item, null, false);
            } else if (chatModel.left == false) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_right_item, null, false);
            }
        }

        TextView textViewMessage = (TextView) convertView.findViewById(R.id.chat_item);

        // Rellena la plantilla con los datos proporcionados por chatModel
        textViewMessage.setText(chatModel.text);

        // Devuelve la vista completa para mostrar
        return convertView;

    }


}