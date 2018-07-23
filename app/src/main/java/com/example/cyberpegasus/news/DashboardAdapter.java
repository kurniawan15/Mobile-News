package com.example.cyberpegasus.news;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by USER on 7/19/2018.
 */

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder>{
    private List<Name> listItems;
    private Context context;

    public DashboardAdapter(List<Name> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Name listItem = listItems.get(position);
        holder.textViewHead.setText(listItem.getName());
        holder.textViewBody.setText(listItem.getBody());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "You clicked " + listItem.getName(), Toast.LENGTH_LONG).show();
                Intent detailIntent = new Intent(view.getContext(), NewsDetailActivity.class);
                view.getContext().startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewHead;
        public TextView textViewBody;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textHead);
            textViewBody = (TextView) itemView.findViewById(R.id.textBody);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutId);
        }
    }
}
