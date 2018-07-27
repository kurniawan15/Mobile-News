package com.example.cyberpegasus.news.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cyberpegasus.news.R;

import java.util.List;

/**
 * Created by USER on 7/26/2018.
 */

public class MediaListAdapter extends ArrayAdapter<String>{
    private List<String> listItems;
    private Context context;


    public MediaListAdapter(@NonNull Context context, int resource, List<String> listItems) {
        super(context, resource, listItems);
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //mendapatkan layoutinflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Mendapatkan listview items
        View listViewItem = inflater.inflate(R.layout.media_list_item, null, true);
        TextView textViewFileName = (TextView) listViewItem.findViewById(R.id.textViewFileName);
        ImageView imageViewFileStatus = (ImageView) listViewItem.findViewById(R.id.imageViewFileStatus);

        //mensetting the data ke textview
        textViewFileName.setText(listItems.get(position));

        imageViewFileStatus.setBackgroundResource(R.drawable.success);
        return listViewItem;
    }
}
