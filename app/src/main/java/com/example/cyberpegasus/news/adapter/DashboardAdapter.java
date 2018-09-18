package com.example.cyberpegasus.news.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyberpegasus.news.R;
import com.example.cyberpegasus.news.activity.NewsDetailActivity;
import com.example.cyberpegasus.news.model.Data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by USER on 7/19/2018.
 */

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DataViewHolder> {
    private ArrayList<Data> dataList;
    private boolean isConect;

    public DashboardAdapter(ArrayList<Data> dataList, Boolean isConect) {
        this.dataList = dataList;
        this.isConect = isConect;
    }


    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, final int position) {
        holder.textViewHead.setText(dataList.get(position).getJudul());
        holder.textViewDate.setText(dataList.get(position).getPengirim());
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataList.get(position).getDateBerita());
        String formatedDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
        holder.textViewTime.setText(formatedDate);
        String isi = dataList.get(position).getIsi();
        holder.textViewBody.setText(isi.substring(0, Math.min(isi.length(), 100)) + "...");

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "You clicked " + dataList.get(position).getId(), Toast.LENGTH_LONG).show();
                Intent detailIntent = new Intent(view.getContext(), NewsDetailActivity.class);

                //Passing nilai pada object Data satu2
                Bundle b = new Bundle();
                b.putString("JUDUL", dataList.get(position).getJudul());
                b.putString("KATEGORI", dataList.get(position).getCategory());
                b.putString("ISI", dataList.get(position).getIsi());
                b.putSerializable("TANGGAL", dataList.get(position).getDateBerita());
                b.putDouble("LAT_BERITA", dataList.get(position).getLokBeritaList().getLan());
                b.putDouble("LONG_BERITA", dataList.get(position).getLokBeritaList().get_long());
                if(isConect == true){
                    b.putStringArrayList("FILE", (ArrayList<String>) dataList.get(position).getFile());
                }
                detailIntent.putExtras(b);

                /*//Menggunakan Serializeable
                Bundle b = new Bundle();
                b.putSerializable("BERITA", dataList.get(position));
                detailIntent.putExtras(b);*/

                view.getContext().startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {

        TextView textViewHead,textViewBody,textViewDate, textViewTime ;
         LinearLayout linearLayout;

         DataViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textHead);
            textViewBody = (TextView) itemView.findViewById(R.id.textBody);
            textViewDate = (TextView) itemView.findViewById(R.id.textDate);
            textViewTime = (TextView) itemView.findViewById(R.id.textTime);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutId);
        }
    }



}
