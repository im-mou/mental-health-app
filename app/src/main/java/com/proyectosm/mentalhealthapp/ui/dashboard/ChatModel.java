package com.proyectosm.mentalhealthapp.ui.dashboard;

public class ChatModel {

    // Variables que indican el texto que lleva y si es o no un bocadillo de la parte izquierda
    public int journal_id;
    public boolean left;
    public String text;

    public ChatModel(int journal_id, boolean left, String text) {
        this.journal_id = journal_id;
        this.left = left;
        this.text = text;
    }


}
