package io.github.gatimus.mobilenotes;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Note {

    private static final String TAG = "Note";

    public String title;
    public String body;
    private File file;

    public Note(){
        title = "";
        body = "";
        file = null;
    } //constructor

    public Note(File file){
        Log.v(TAG, "construct");
        this.file = file;
        title = file.getName();
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try{
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line).append("\n");
                Log.v(TAG, line);
            }
        }catch (IOException e){
            Log.e(TAG, e.toString());
        }finally {
            try {
                bufferedReader.close();
                fileReader.close();
            } catch (IOException | NullPointerException e) {
                Log.e(TAG,e.toString());
            }
        }
        body = stringBuilder.toString();
        Log.v(TAG, body);
    } //constructor

} //class
