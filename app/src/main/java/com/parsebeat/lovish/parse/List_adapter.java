package com.parsebeat.lovish.parse;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lovish-THINKPAD on 06-02-2016.
 */
public class List_adapter extends BaseAdapter{
    Context context;
    LayoutInflater inflater;
    private List<tracks> modelList;
    private ArrayList<tracks> arrayList;

    public List_adapter(Context context, List<tracks> modelList) {
        this.context = context;
        this.modelList = modelList;
        this.arrayList = new ArrayList<tracks>();
//
//       this.arrayList.addAll(modelList);
    }


    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public static class ViewHolder{
        TextView title;
        TextView username;
        TextView genre;
        TextView plays;
        TextView hearts;
        TextView duration;
        TextView user;
    }
    public View getView (final int position, View view, ViewGroup parent){
        final ViewHolder holder;
        if(view == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.vertical_list_item, null);
            holder = new ViewHolder();
            // Locate the TextViews in listview_item.xml
            holder.title = (TextView) view.findViewById(R.id.Track_name);
            holder.username = (TextView) view.findViewById(R.id.user_name);
            holder.hearts = (TextView) view.findViewById(R.id.vertcl_itemhrtno);
            holder.plays = (TextView) view.findViewById(R.id.vertcl_itemplayno);
            holder.duration = (TextView) view.findViewById(R.id.vertcl_itemdurtnno);
            holder.user= (TextView) view.findViewById(R.id.user);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.title.setText(modelList.get(position).getfoo());
        holder.username.setText(modelList.get(position).gettname());
        holder.plays.setText(modelList.get(position).getplays());
        holder.hearts.setText(modelList.get(position).gethearts());
        holder.duration.setText(modelList.get(position).getduration());
        holder.user.setText(modelList.get(position).getusername());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}
