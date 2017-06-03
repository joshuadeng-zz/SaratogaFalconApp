package io.github.joshuadeng.saratogafalcon;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import layout.Columns;
import layout.Features;
import layout.News;
import layout.Opinion;
import layout.Sports;

public class MainActivity extends AppCompatActivity//Almost all auto-generated
        implements News.OnFragmentInteractionListener, Sports.OnFragmentInteractionListener,
        Opinion.OnFragmentInteractionListener, Columns.OnFragmentInteractionListener,Features.OnFragmentInteractionListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the sections
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {//switch between tabs

            switch (position) {
                case 0:
                    News newstab = new News();
                    return newstab;
                case 1:
                    Sports sportstab = new Sports();
                    return sportstab;
                case 2:
                    Opinion opiniontab = new Opinion();
                    return opiniontab;
                case 3:
                    Columns columnstab = new Columns();
                    return columnstab;
                case 4:
                    Features featurestab = new Features();
                    return featurestab;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 5 tabs
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {//returns tab titles when switching between
            switch (position) {
                case 0:
                    return "News";
                case 1:
                    return "Sports";
                case 2:
                    return "Opinion";
                case 3:
                    return "Columns";
                case 4:
                    return "Features";

            }
            return null;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }
}
