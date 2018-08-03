package com.example.cyberpegasus.news.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cyberpegasus.news.R;
import com.example.cyberpegasus.news.model.Data;

import java.util.ArrayList;

/**
 * Created by USER on 7/19/2018.
 */

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DataViewHolder> {
    private ArrayList<Data> dataList;
    private Context context;

    public DashboardAdapter(ArrayList<Data> dataList) {
        this.dataList = dataList;
    }


    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        holder.textViewHead.setText(dataList.get(position).getJudul());
        holder.textViewDate.setText(dataList.get(position).getPengirim());
        holder.textViewTime.setText(dataList.get(position).getDateBerita().toString());
        holder.textViewBody.setText(dataList.get(position).getIsi());

                //Toast.makeText(context, "You clicked " + listItem.getName(), Toast.LENGTH_LONG).show();
             //   Intent detailIntent = new Intent(view.getContext(), NewsDetailActivity.class);
            //    view.getContext().startActivity(detailIntent);
            //}
       // });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewHead,textViewBody,textViewDate, textViewTime ;
        public LinearLayout linearLayout;

        public DataViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textHead);
            textViewBody = (TextView) itemView.findViewById(R.id.textBody);
            textViewDate = (TextView) itemView.findViewById(R.id.textDate);
            textViewTime = (TextView) itemView.findViewById(R.id.textTime);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutId);
        }
    }
}
