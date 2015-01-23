package io.github.gatimus.mobilenotes;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;


public class Main extends ActionBarActivity {

    private static final String TAG = "Main";
    private Resources resources;
    private FragmentManager fragmentManager;
    private DialogFragment about;
    private DialogFragment help;
    private ListView listView;
    private ArrayAdapter<String> fruitAdapter;
    private ArrayList<String> fruit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "Create");
        setContentView(R.layout.activity_main);
        resources = getApplicationContext().getResources();
        fragmentManager = this.getFragmentManager();
        about = new About();
        help = new Help();
        fruit = new ArrayList<String>();
        fruit.add("banana");
        fruit.add("apple");
        fruit.add("orange");
        fruit.add("tomato");
        fruitAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fruit);
        fruitAdapter.setNotifyOnChange(true);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(fruitAdapter);
        handleIntent(getIntent());
    } //onCreate

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            fruitAdapter.getFilter().filter(query);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(TAG, "CreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        if(searchView != null){
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }
        return true;
    } //onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, item.getTitle().toString() + " Selected");
        int id = item.getItemId();
        switch(id){
            case R.id.action_search :
                if(item.getActionView() == null){
                    onSearchRequested();
                }
                break;
            case R.id.action_settings :
                Intent intent = new Intent(Main.this, Settings.class);
                startActivity(intent);
                break;
            case R.id.action_about :
                about.show(fragmentManager, resources.getString(R.string.action_about));
                break;
            case R.id.action_help :
                help.show(fragmentManager, resources.getString(R.string.action_help));
                break;
            case R.id.action_quit :
                System.exit(0);
                break;
            default :
                break;
        }
        return super.onOptionsItemSelected(item);
    } //onOptionsItemSelected

} //class
