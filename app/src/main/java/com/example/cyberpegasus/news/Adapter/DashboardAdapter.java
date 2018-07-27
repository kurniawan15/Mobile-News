package com.example.cyberpegasus.news.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cyberpegasus.news.Name;
import com.example.cyberpegasus.news.NewsDetailActivity;
import com.example.cyberpegasus.news.R;
import com.example.cyberpegasus.news.model.Data;

import java.util.ArrayList;
import java.util.List;

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
        holder.textViewHead.setText(dataList.get(position).getCategory());
        holder.textViewDate.setText(dataList.get(position).getDari());
        holder.textViewBody.setText(dataList.get(position).getLaporan());

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

        public TextView textViewHead;
        public TextView textViewBody,textViewDate;
        public LinearLayout linearLayout;

        public DataViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textHead);
            textViewBody = (TextView) itemView.findViewById(R.id.textBody);
            textViewDate = (TextView) itemView.findViewById(R.id.textDate);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutId);
        }
    }
}
