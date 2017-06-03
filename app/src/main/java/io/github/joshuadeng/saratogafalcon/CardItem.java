package io.github.joshuadeng.saratogafalcon;

/**
 * Created by Joshua on 5/23/2017.
 */

public class CardItem {//class for the object CardItem, displays the story info in the cardview
    private String title;
    private String author;
    private String date;
    private String url;

    public CardItem(String title, String author, String date) {
        this.title = title;
        this.author = author;
        this.date = date;
    }

    public CardItem(String title, String author, String date, String url) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }


}
