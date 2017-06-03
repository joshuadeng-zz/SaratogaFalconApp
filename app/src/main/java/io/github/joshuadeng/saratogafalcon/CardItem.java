package io.github.joshuadeng.saratogafalcon;

/**
 * Created by Joshua on 5/23/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
public class CardItem {
    private String title;
    private String author;
    private String date;
    private String url;


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
