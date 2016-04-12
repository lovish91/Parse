package com.parsebeat.lovish.parse;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class New_tracks_Fragment extends Fragment {
    Toolbar toolbar;
    RecyclerView popl;
    Linear_list_adapter adapter;
    List<tracks> ModelList;
    final String TOP_SCORES_LABEL = "topScores";
    CollapsingToolbarLayout collapsingToolbarLayout;

    public New_tracks_Fragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        query();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_new_tracks, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        popl = (RecyclerView) view.findViewById(R.id.popular_full);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        popl.setHasFixedSize(true);
        popl.setLayoutManager(layoutManager);
        popl.setNestedScrollingEnabled(false);
        //Utility.setDynamicHeight(popl);
        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.populartracks);
        Palette.generateAsync(bitmap,
                new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch vibrant =
                                palette.getVibrantSwatch();
                        int mutedColor = palette.getVibrantSwatch().getRgb();
                        if (vibrant != null) {
                            // If we have a vibrant color
                            // update the title TextView
                            collapsingToolbarLayout.setBackgroundColor(mutedColor);
                            //  mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                            collapsingToolbarLayout.setStatusBarScrimColor(palette.getLightMutedColor(mutedColor));
                            collapsingToolbarLayout.setContentScrimColor(palette.getLightVibrantColor(mutedColor));
                        }
                    }
                });

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("All New Tracks");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });
        adapter = new Linear_list_adapter(getContext(), ModelList);
        popl.setAdapter(adapter);
        return view;
    }
    public void query(){
        ModelList = new ArrayList<tracks>();
        ParseQuery<tracks> query = ParseQuery.getQuery(tracks.class);
        query.include("user");
        query.include("username");
        query.orderByDescending("createdAt");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<tracks>() {
            @Override
            public void done(final List<tracks> modelList, ParseException e) {

                ModelList.addAll(modelList);
                adapter.notifyDataSetChanged();

                ParseObject.unpinAllInBackground(TOP_SCORES_LABEL, modelList, new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {

                        ParseObject.pinAllInBackground(TOP_SCORES_LABEL, modelList);
                    }

                });
            }
        });
    }

}
