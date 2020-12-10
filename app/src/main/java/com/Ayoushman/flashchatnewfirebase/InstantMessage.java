package com.Ayoushman.flashchatnewfirebase;

public class InstantMessage {
private  String message ;

private  String Author ;

    public InstantMessage(String message, String author) {
        this.message = message;
        Author = author;
    }

    public InstantMessage() {

    }

    public String getMessage() {
        return message;
    }



    public String getAuthor() {
        return Author;
    }


}

