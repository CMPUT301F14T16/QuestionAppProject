package cmput301.team16.easya;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    //TODO: remove all the android junk

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        List<QuestionsHolder> qst = new ArrayList<QuestionsHolder>();
        for (int i = 0; i <200; i++)
        {
            qst.add(new QuestionsHolder("Question title " + i, "Author of question" + i, i*10, (i*15)/3));
        }
        ArrayAdapter<QuestionsHolder> listAdapter = new ListAdapter(this, qst);

        ((ListView)findViewById(R.id.mainList)).setAdapter(listAdapter);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //TODO: add code to show up the search box
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    protected static class ListAdapter extends ArrayAdapter<QuestionsHolder>
    {
        private LayoutInflater inflater;

        public ListAdapter( Context context, List<QuestionsHolder> todos)
        {
            super( context, R.layout.main_list, R.id.questionTitle, todos );
            inflater = LayoutInflater.from(context) ;
        }

        @Override
        public View getView(int id, View view, ViewGroup parent)
        {
            // Todo item to display
            QuestionsHolder item = (QuestionsHolder)this.getItem(id);
            // The child views in each row.
            TextView title;
            TextView author;
            TextView stars;
            TextView comments;

            // Create a new row for the list
            if ( view == null )
            {
                view = inflater.inflate(R.layout.main_list, null);

                title = (TextView) view.findViewById( R.id.questionTitle);
                author = (TextView) view.findViewById( R.id.questionAuthor);
                stars = (TextView) view.findViewById( R.id.questionStars);
                comments = (TextView) view.findViewById( R.id.questionComments);

                // ~~ Nice Optimization Hint Used
                // Optimization: Tag the row with it's child views, so we don't have to
                // call findViewById() later when we reuse the row.
                view.setTag(new ViewHolder(title, author, stars, comments));
            }
            else
            {
                // Because we use a ViewHolder, we avoid having to call findViewById().
                ViewHolder viewHolder = (ViewHolder)view.getTag();
                title = viewHolder.title;
                author = viewHolder.author;
                stars = viewHolder.stars;
                comments = viewHolder.comments;
            }
            title.setText(item.title);
            author.setText(item.author);
            stars.setText(item.stars.toString());
            comments.setText(item.comments.toString());

            return view;
        }
    }

    /** Holds child views for one row. */
    protected static class ViewHolder {
        public TextView title;
        public TextView author;
        public TextView stars;
        public TextView comments;
        public ViewHolder( TextView t, TextView a, TextView s, TextView c ) {
            this.title = t;
            this.author = a;
            this.stars = s;
            this.comments = c;
        }
    }
    public class QuestionsHolder
    {
        public String title;
        public String author;
        public Integer stars;
        public Integer comments;

        public QuestionsHolder(String t, String a, Integer s, Integer c)
        {
            this.title = t;
            this.author = a;
            this.stars = s;
            this.comments = c;
        }
    }
}
