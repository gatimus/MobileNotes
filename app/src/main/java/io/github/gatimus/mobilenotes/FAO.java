package io.github.gatimus.mobilenotes;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FAO {

    private static final String TAG = "FAO";

    private Context context;
    private String dir;

    public FAO(Context context){
        Log.v(TAG, "construct");
        this.context = context;
        dir = context.getFilesDir().getAbsolutePath();
    } //constructor

    public void newFile(String name, String content){

    }

    public void saveFile(String name, String content){

    }

    public File openFile(String name){
        File file = null;
        try{
            file = new File(dir, name);
        } catch (NullPointerException e){
            Log.e(TAG, e.toString());
        } //try catch
        return file;
    } //openFile

    public List<File> listFiles(){
        List<File> files = new ArrayList<File>();
        String[] fileNames = context.fileList();
        for(String fileName : fileNames){
            try{
                files.add(new File(dir,fileName));
            } catch (NullPointerException e){
                Log.e(TAG, e.toString());
            } //try catch
        } //for
        return files;
    } //listFiles


    //below, from lecture

    public void createFile(String fn) {
        try {
            OutputStreamWriter outFile = new OutputStreamWriter(openFileOutput(fn, MODE_PRIVATE));
            outFile.write("Line 1: " + fn + '\n');
            outFile.write("Line 2: " + fn);
            outFile.close();
            Toast.makeText(this, "file created: "+ fn, Toast.LENGTH_LONG).show();
        }
        catch(Throwable t) {
            Toast.makeText(this, "Create Exception: "+ t.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public void viewFile(View v) {
        String fileName = etFileName.getText().toString();
        readFile(fileName);
    }

    public void readFile(String fn) {
        try {
            InputStream in = openFileInput(fn);
            if(in != null)
            {
                InputStreamReader tmp = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(tmp);
                String str=null;
                StringBuilder buf = new StringBuilder();
                while((str = reader.readLine()) != null) {
                    buf.append(str + "\n");
                }
                in.close();
                tvFileContents.setText(buf.toString());
            }
            else
                Toast.makeText(this, "Find Not Found", Toast.LENGTH_SHORT).show();
        }


        catch(java.io.FileNotFoundException e) {

        }
        catch(Throwable t) {
            Toast.makeText(this, "Read Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public void removeFile(View v) {
        String fileName = etFileName.getText().toString();
        deleteMyFile(fileName);
    }

    public void deleteMyFile(String fn) {
        try {

            String dir = getFilesDir().getAbsolutePath();
            File myFile = new File(dir, fn);

            boolean ret = myFile.delete();
            if(!ret)
                Toast.makeText(getApplicationContext(), "Delete Error",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "File Deleted: " + fn,Toast.LENGTH_LONG).show();


        }
        catch(Throwable t) {
            Toast.makeText(getApplicationContext(), "Delete Exception: " + t.toString(),Toast.LENGTH_LONG).show();
        }


    }

    public void listFiles(View v) {

        String[] filenames = getApplicationContext().fileList();

        tvFileContents.setText("");
        String fn="";
        int i;
        for(i = 0; i<filenames.length; i++){
            fn = fn  + filenames[i] + '\n';
        }
        tvFileContents.setText(fn);
    }

} //class
