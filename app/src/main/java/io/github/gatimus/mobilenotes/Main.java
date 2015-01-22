package io.github.gatimus.mobilenotes;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class Main extends ActionBarActivity {

    private static final String TAG = "Main";
    private Resources resources;
    private FragmentManager fragmentManager;
    private DialogFragment about;
    private DialogFragment help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "Create");
        setContentView(R.layout.activity_main);
        resources = getApplicationContext().getResources();
        fragmentManager = this.getFragmentManager();
        about = new About();
        help = new Help();
        setContentView(R.layout.activity_main);
    } //onCreate


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(TAG, "CreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    } //onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, item.getTitle().toString() + " Selected");
        int id = item.getItemId();
        switch(id){
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
