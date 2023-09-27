package com.widgets.widgey.helpers;

import com.google.gson.annotations.SerializedName;

public class Quotes {

    @SerializedName(value ="quote")
    private String quote;

    @SerializedName(value ="author")
    private String author;

    public Quotes() {

    }

    public Quotes(String quote, String author) {

        this.quote = quote;
        this.author = author;
    }

    public String getQuote() { return quote;}

    public String getAuthor() { return author;}

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

