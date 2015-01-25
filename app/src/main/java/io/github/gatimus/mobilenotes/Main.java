package io.github.gatimus.mobilenotes;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Main extends ActionBarActivity {

    private static final String TAG = "Main";
    private Resources resources;
    private FragmentManager fragmentManager;
    private DialogFragment save;
    private DialogFragment delete;
    private DialogFragment about;
    private DialogFragment help;
    private FAO fao;
    private Note currentNote;
    private List<File> fileList;
    private TextView emptyList;
    private ArrayAdapter<File> fileAdapter;
    private EditText etTitle;
    private EditText etBody;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ListView listView;
    private SearchManager searchManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "Create");
        setContentView(R.layout.activity_main);
        resources = getApplicationContext().getResources();
        fragmentManager = this.getFragmentManager();
        save = new Save();
        delete = new Delete();
        about = new About();
        help = new Help();
        fao = new FAO(getApplicationContext());
        currentNote = new Note();
        fileList = fao.listFiles();
        fileAdapter = new FileAdapter(this, fileList);
        fileAdapter.setNotifyOnChange(true);
        etTitle = (EditText) findViewById(R.id.et_title);
        etBody = (EditText) findViewById(R.id.et_body);
        listView = (ListView) findViewById(android.R.id.list);
        listView.setEmptyView(findViewById(android.R.id.empty));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openNote((File) parent.getItemAtPosition(position));
                searchManager.stopSearch();
                drawerLayout.closeDrawers();
            }
        });
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ){
            @Override
            public void onDrawerClosed(View drawerView){
                super.onDrawerClosed(drawerView);
                updateFileList();
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        listView.setAdapter(fileAdapter);
        searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        searchManager.setOnDismissListener(new SearchManager.OnDismissListener(){
            @Override
            public void onDismiss() {
                Log.v("SearchManager", "Dismiss");
                drawerLayout.closeDrawers();
                updateFileList();
            }
        });
        handleIntent(getIntent());
    } //onCreate

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            List<File> result = new ArrayList<File>();
            for(File file : fileList){
                if(file.getName().toLowerCase().contains(query.toLowerCase())){
                    result.add(file);
                }
            }
            fileList.removeAll(fileList);
            fileList.addAll(result);
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(TAG, "CreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        if(searchView != null){
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }
        return true;
    } //onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        Log.i(TAG, item.getTitle().toString() + " Selected");
        int id = item.getItemId();
        switch(id){
            case R.id.action_search :
                if(item.getActionView() == null){
                    onSearchRequested();
                }
                break;
            case R.id.action_save :
                save.show(fragmentManager, resources.getString(R.string.action_save));
                break;
            case R.id.action_delete :
                delete.show(fragmentManager, resources.getString(R.string.action_delete));
                break;
            /*
            case R.id.action_settings :
                Intent intent = new Intent(Main.this, Settings.class);
                startActivity(intent);
                break;
                */
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

    public void saveNote(){
        currentNote.title = etTitle.getText().toString();
        currentNote.body = etBody.getText().toString();
        fao.saveFile(currentNote);
        updateFileList();
    } //save

    public void deleteNote(){
        etTitle.setText("");
        etBody.setText("");
        fao.deleteFile(currentNote);
        updateFileList();
    } //delete

    public void openNote(File file){
        currentNote = new Note(file);
        etTitle.setText(currentNote.title);
        etBody.setText(currentNote.body);
    } //openNote

    public void updateFileList(){
        Log.v(TAG, "updateFileList");
        fileList.removeAll(fileList);
        fileList.addAll(fao.listFiles());
    } //updateFileList

} //class
