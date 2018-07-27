package com.example.cyberpegasus.news.Adapter;

/**
 * Created by Cyber Pegasus on 7/17/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cyberpegasus.news.Name;
import com.example.cyberpegasus.news.R;

import java.util.List;


public class NameAdapter extends ArrayAdapter<Name> {

    //menyimpan semua data dalam daftar
    private List<Name> names;

    //context object
    private Context context;

    //constructor
    public NameAdapter(Context context, int resource, List<Name> names) {
        super(context, resource, names);
        this.context = context;
        this.names = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //mendapatkan layoutinflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Mendapatkan listview itmes
        View listViewItem = inflater.inflate(R.layout.name, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        ImageView imageViewStatus = (ImageView) listViewItem.findViewById(R.id.imageViewStatus);

        //Mendapatkan the current name
        Name name = names.get(position);

        //mensetting the data ke textview
        textViewName.setText(name.getName());

        // jika status yang disinkronkan adalah 0 yang ditampilkan
        // antrian ikon
        // else menampilkan ikon yang disinkronkan
        if (name.getStatus() == 0)
            imageViewStatus.setBackgroundResource(R.drawable.stopwatch);
        else
            imageViewStatus.setBackgroundResource(R.drawable.success);

        return listViewItem;
    }
}

