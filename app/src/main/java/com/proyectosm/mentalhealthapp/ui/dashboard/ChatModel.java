package com.proyectosm.mentalhealthapp.ui.dashboard;

public class ChatModel {

    // Variables que indican el texto que lleva y si es o no un bocadillo de la parte izquierda
    public String text;
    public boolean left;

    public ChatModel(String text, boolean left) {
        this.text = text;
        this.left = left;
    }
}
