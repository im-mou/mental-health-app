package com.proyectosm.mentalhealthapp.ui.dashboard;

public class JournalModel {
    // Variables que indican si el objeto pertenece a un tipo de globo o a otro
    public int journal_id;
    public int user_id;
    public String date;
    public String color;
    public double sentiment_index;
    ChatModel[] chat;

    public JournalModel(int journal_id, int user_id, String date, String color, double sentiment_index, ChatModel[] chat) {
        this.journal_id = journal_id;
        this.user_id = user_id;
        this.date = date;
        this.color = color;
        this.sentiment_index = sentiment_index;
        this.chat = chat;
    }

    public ChatModel[] getChat() {
        return chat;
    }
}
