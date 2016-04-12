package com.parsebeat.lovish.parse;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HrzntlProflAdptr extends RecyclerView.Adapter<HrzntlProflAdptr.CustomViewHolder> {
    Context context;
    LayoutInflater inflater;
    private List<tracks> ModelList;
    private ArrayList<tracks> arrayList;

    public HrzntlProflAdptr(Context context, List<tracks> modelList) {
        this.context = context;
        this.ModelList = modelList;
        this.arrayList = new ArrayList<tracks>();
        this.arrayList.addAll(modelList);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_tracksitem, null,false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        tracks model = ModelList.get(position);
        holder.title.setText(model.getfoo());
        holder.username.setText(model.gettname());
        holder.genre.setText(model.getgenre());
        holder.plays.setText(model.getplays());
        holder.hearts.setText(model.getplays());
        holder.duration.setText(model.getduration());
    }

    @Override
    public int getItemCount() {
        return ModelList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView username;
        TextView genre;
        TextView plays;
        TextView hearts;
        TextView duration;

        public CustomViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.hrnztl_itemtitle);
            this.username = (TextView) view.findViewById(R.id.hrnztl_itemusrnme);
            this.genre = (TextView) view.findViewById(R.id.hrnztl_itemgenre);
            this.plays = (TextView) view.findViewById(R.id.hrnztl_itemplayno);
            this.hearts = (TextView) view.findViewById(R.id.hrnztl_itemhrtno);
            this.duration = (TextView) view.findViewById(R.id.hrnztl_itemdurtnno);
        }
    }
}
