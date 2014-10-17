package cmput301.team16.easya;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cauani on 14/10/17.
 */
public class SubmitContent extends Activity {
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

}
