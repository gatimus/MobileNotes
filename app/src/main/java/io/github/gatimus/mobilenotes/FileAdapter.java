package io.github.gatimus.mobilenotes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class FileAdapter extends ArrayAdapter<File> {

    private final static String TAG = "FileAdapter";

    private Context context;
    private List<File> fileList;

    public FileAdapter(Context context, List<File> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        Log.v(TAG, "construct");
        this.context = context;
        fileList = objects;
    } //constructor

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v(TAG, "getView " + fileList.get(position).getName());
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        TextView textView = (TextView) rowView.findViewById(android.R.id.text1);
        textView.setText(fileList.get(position).getName());
        return rowView;
    } //getView

} //class
