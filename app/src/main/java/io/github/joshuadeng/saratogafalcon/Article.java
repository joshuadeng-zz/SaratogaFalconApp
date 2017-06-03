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

public class Article extends AppCompatActivity {

    private String title;
    private String author;
    private String date;
    private String url;

    private TextView viewtitle ;
    private TextView viewauthor;
    private TextView viewdate ;
    private TextView viewtext;

    public Article(){}


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);


        Intent intent = getIntent();
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

    private class Scrape extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... params) {
            Document doc;
            String story;
            if (url.contains("%E2%80%98")||url.contains("%E2%80%99")||url.contains("%C3%B1")
                    ||url.contains("%E2%80%A6")||url.contains("%C3%A7")||url.contains("%E2%80%94")) {
                doc = urlFixer(url);
                Elements elements = doc.getElementsByClass("story_body");
                story = elements.toString();
                story = story.substring(story.indexOf("<p"), story.indexOf("</div>"));
                return story;
            } else
                try {
                    doc = Jsoup.connect(url).get();
                    Elements elements = doc.getElementsByClass("story_body");
                    story = elements.toString();
                    story = story.substring(story.indexOf("<p"),story.indexOf("</div>"));
                    return story;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            return "Hold this L";//most likely the scenario if unrecognized symbols screws up the URL connecting
        }



        protected void onPostExecute(String story){
            Spanned body;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
                body = Html.fromHtml(story,Html.FROM_HTML_MODE_LEGACY);
            else
                body = Html.fromHtml(story);

            viewtext.setText(body);
            viewtext.setMovementMethod(LinkMovementMethod.getInstance());

        }
    }

    private static Document urlFixer (String url) {
        Document doc;
        try {
            Connection connect = Jsoup.connect(url);
            URI u = null;
            try {
                u = new URI(url);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            doc = connect.url(new URI(u.getScheme(), u.getUserInfo(), u.getHost(), u.getPort(), URLDecoder.decode(u.getPath(), "UTF-8"), u.getQuery(), u.getFragment()).toURL()).get();
            return doc;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }



}
