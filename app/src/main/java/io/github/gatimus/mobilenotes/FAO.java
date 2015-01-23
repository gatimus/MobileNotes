package io.github.gatimus.mobilenotes;

import android.content.Context;
import android.util.Log;

import java.io.File;
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



} //class
