package layout;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.joshuadeng.saratogafalcon.CardItem;
import io.github.joshuadeng.saratogafalcon.MyAdapter;
import io.github.joshuadeng.saratogafalcon.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link News.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link News#newInstance} factory method to
 * create an instance of this fragment.
 */
public class News extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1="key1";
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView newsView;
    private RecyclerView.Adapter adapter;

    private List<CardItem> cardItems;

    private SwipeRefreshLayout refreshLayout;


    public News() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment News.
     */
    // TODO: Rename and change types and number of parameters
    public static News newInstance(String param1, String param2) {
        News fragment = new News();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        newsView = (RecyclerView) view.findViewById(R.id.RecyclerNews);
        refreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        newsView.setLayoutManager(linearLayoutManager);

        cardItems = new ArrayList<>();
        final int[] i = {0};
        scrapeData(i[0]);

        adapter = new MyAdapter(cardItems,getActivity());
        newsView.setAdapter(adapter);//put cardItems ArrayList on UI

        newsView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (linearLayoutManager.findLastVisibleItemPosition() == cardItems.size()-1){
                    i[0]++;
                    scrapeData(i[0]);

                }
            }
        });

        return view;
    }


    private void scrapeData(final int id) {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... params) {
                Document doc;

                try {
                    doc = Jsoup.connect("http://saratogafalcon.org/news?page="+id).get();
                    Elements titles = doc.getElementsByClass("teaser_header");
                    Elements dates = doc.getElementsByClass("pub_info");
                    Elements authors = doc.getElementsByClass("author_info");
                    Elements urls = doc.getElementsByClass("teaser_header");

                    String alltitles = titles.toString();
                    String allauthors = authors.toString();
                    String alldates = dates.toString();
                    String allurls = urls.toString();

                    for (int j = 0; j < 5; j++) {
                        alltitles = alltitles.substring(alltitles.indexOf("<a"));
                        String title = alltitles.substring(alltitles.indexOf(">") + 1, alltitles.indexOf("</"));
                        alltitles = alltitles.substring(alltitles.indexOf("</div>"));

                        String author = allauthors.substring(allauthors.indexOf("by"), allauthors.indexOf("</"));
                        allauthors = allauthors.substring(allauthors.indexOf("</span>") + 7);

                        String date = alldates.substring(alldates.indexOf(">") + 3, alldates.indexOf(" —"));
                        alldates = alldates.substring(alldates.indexOf("</div>") + 6);

                        allurls = allurls.substring(allurls.indexOf("<a"));
                        String url = "http://saratogafalcon.org" +
                                allurls.substring(allurls.indexOf("href=") + 6, allurls.indexOf("\">"));
                        allurls = allurls.substring(allurls.indexOf("</div>"));


                        cardItems.add(new CardItem(title, author, date, url));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            protected void onPostExecute(Void avoid){
                adapter.notifyDataSetChanged();
            }
        };

        task.execute(id);
    }

    @Override
    public void onRefresh() {
        clear();
        scrapeData(0);
        adapter = new MyAdapter(cardItems,getActivity());
        newsView.setAdapter(adapter);
        refreshLayout.setRefreshing(false);
    }

    private void clear() {
        cardItems.clear();
        adapter.notifyDataSetChanged();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
