package io.github.gatimus.mobilenotes;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    public void saveFile(Note note){
        File file = null;
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try{
            file = new File(dir, note.title);
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(note.body, 0, note.body.length());
            String msg = note.title + " saved.";
            Log.i(TAG, msg);
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } catch(IOException e){
            Log.e(TAG, e.toString());
            Toast.makeText(context, "Failed to save " + note.title, Toast.LENGTH_SHORT).show();
        } finally {
            try{
                bufferedWriter.close();
                fileWriter.close();
            }catch (IOException e){
                Log.e(TAG, e.toString());
            }
        }
    } //saveFile

    public void deleteFile(Note note){
        boolean deleted = context.deleteFile(note.title);
        if(!deleted){
            File file = null;
            try{
                file = new File(dir, note.title);
                deleted = file.delete();
            } catch (NullPointerException e){
                Log.e(TAG, e.toString());
            } //try catch
        } //if
        if(deleted){
            String msg = note.title + " deleted.";
            Log.i(TAG, msg);
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } else {
            String msg = "Failed to delete " + note.title;
            Log.e(TAG, msg);
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } //if else
    } //deleteFile

    public Note openFile(String name){
        Note note = null;
        try{
            note = new Note(new File(dir, name));
        } catch (NullPointerException e){
            Log.e(TAG, e.toString());
        } //try catch
        return note;
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
