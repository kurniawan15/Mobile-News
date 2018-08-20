package com.example.cyberpegasus.news.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyberpegasus.news.R;

import java.io.File;
import java.util.List;

/**
 * Created by USER on 7/26/2018.
 */

public class MediaListAdapter extends ArrayAdapter<String>{
    private List<String> listItems;
    private Context context;
    private List<File> listFiles;


    public MediaListAdapter(@NonNull Context context, int resource, List<String> listItems, List<File> listFiles) {
        super(context, resource, listItems);
        this.context = context;
        this.listItems = listItems;
        this.listFiles = listFiles;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //mendapatkan layoutinflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Mendapatkan listview items
        View listViewItem = inflater.inflate(R.layout.media_list_item, null, true);

        TextView textViewFileName = (TextView) listViewItem.findViewById(R.id.textViewFileName);

        //mensetting the data ke textview
        textViewFileName.setText(listItems.get(position));

        ImageButton btnDelete = (ImageButton) listViewItem.findViewById(R.id.buttonDeleteFile);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), listItems.get(position).toString() + " Dihapus !", Toast.LENGTH_LONG).show();
                Toast.makeText(view.getContext(), "File : " + listFiles.get(position).getName().toString() + " Dihapus !", Toast.LENGTH_LONG).show();
                listItems.remove(position);
                listFiles.remove(position);
                notifyDataSetChanged();
            }
        });

        return listViewItem;
    }
}
