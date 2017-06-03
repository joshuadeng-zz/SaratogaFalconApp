package io.github.joshuadeng.saratogafalcon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import layout.News;

/**
 * Created by Joshua on 5/23/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<CardItem> cardItems;
    private Context context;

    public MyAdapter(List<CardItem> cardItems, Context context) {
        this.cardItems = cardItems;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CardItem cardItem= cardItems.get(position);

        holder.title.setText(cardItem.getTitle());
        holder.author.setText(cardItem.getAuthor());
        holder.date.setText(cardItem.getDate());

        holder.linearLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Article.class);//send intent to new class(Article)
                intent.putExtra("title",cardItem.getTitle());//pass title to article class
                intent.putExtra("author",cardItem.getAuthor());//pass author to article class
                intent.putExtra("date",cardItem.getDate());//pass date to article class
                intent.putExtra("url",cardItem.getUrl());//pass url to article class
                context.startActivity(intent);//start Article class when a cardItem story is pressed

            }
        });
    }

    @Override
    public int getItemCount() {
        return cardItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView author;
        public TextView date;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {//set Strings to the corresponding TextView
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            author = (TextView) itemView.findViewById(R.id.author);
            date = (TextView) itemView.findViewById(R.id.date);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }

}
