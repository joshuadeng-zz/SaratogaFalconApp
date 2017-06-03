package io.github.joshuadeng.saratogafalcon;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;

public class Article extends AppCompatActivity {//Class that displays the article when story card is clicked

    private String title;
    private String author;
    private String date;
    private String url;

    TextView viewtitle ;
    TextView viewauthor;
    TextView viewdate ;
    TextView viewtext;

    public Article(){}//necessary empty constructor


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);


        Intent intent = getIntent();//transfer cardview info from MyAdapter
        title = intent.getStringExtra("title");
        author = intent.getStringExtra("author");
        date = intent.getStringExtra("date");
        url = intent.getStringExtra("url");

        viewtitle = (TextView) findViewById(R.id.title);
        viewauthor = (TextView) findViewById(R.id.author);
        viewdate = (TextView) findViewById(R.id.date);
        viewtext = (TextView) findViewById(R.id.text);

        viewtitle.setText(title);
        viewauthor.setText(author);
        viewdate.setText(date);

        new Scrape().execute();
    }

    private class Scrape extends AsyncTask<Void, Void, String> {//Scrape the story

        protected String doInBackground(Void... params) {
            Document doc;
            String story;
            if (url.contains("%E2%80%98")||url.contains("%E2%80%99")||url.contains("%C3%B1")
                    ||url.contains("%E2%80%A6")||url.contains("%C3%A7")||url.contains("%E2%80%94")) {//some special symbols mess up the URL connector
                doc = urlFixer(url);//pass to URL fixer
                Elements elements = doc.getElementsByClass("story_body");
                story = elements.toString();
                story = story.substring(story.indexOf("<p"), story.indexOf("</div>"));//parse the story
                return story;
            } else
                try {
                    doc = Jsoup.connect(url).get();
                    Elements elements = doc.getElementsByClass("story_body");
                    story = elements.toString();
                    story = story.substring(story.indexOf("<p"),story.indexOf("</div>"));//parse the story
                    return story;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            return "Hold this L";//most likely the scenario if unrecognized symbols screws up the URL connecting
        }



        protected void onPostExecute(String story){
            Spanned body;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)//for Android versions 7.0 and  higher
                body = Html.fromHtml(story,Html.FROM_HTML_MODE_LEGACY);//new html method
            else
                body = Html.fromHtml(story);//Deprecated html method, for devices not on Android 7 or greater

            viewtext.setText(body);//set Textview with html text
            viewtext.setMovementMethod(LinkMovementMethod.getInstance());//allows hyperlinks in article to be clicked

        }
    }

    private static Document urlFixer (String url) {//Jsoup(library for parsing html) has some trouble when connecting to URLs with special symbols (UTF-8)
        Document doc = null;
        try {
            Connection connect = Jsoup.connect(url); //use connect obj
            URI u = null;
            try {
                u = new URI(url);//build new url with URI
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }//use below to keep url from getting screwed
            doc = connect.url(new URI(u.getScheme(), u.getUserInfo(), u.getHost(), u.getPort(), URLDecoder.decode(u.getPath(), "UTF-8"), u.getQuery(), u.getFragment()).toURL()).get();
            return doc;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return doc;
    }



}
