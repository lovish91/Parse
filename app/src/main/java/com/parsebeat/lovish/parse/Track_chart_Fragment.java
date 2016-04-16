package com.parsebeat.lovish.parse;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * Activities that contain this fragment must implement the
 * {@link Track_chart_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Track_chart_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Track_chart_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    Toolbar toolbar;
    RecyclerView popl;
    Linear_list_adapter adapter;
    private List<tracks> ModelList= new ArrayList<tracks>();
    final String TOP_SCORES_LABEL = "topScores";
    CollapsingToolbarLayout collapsingToolbarLayout;

    // TODO: Rename and change types of parameters


    public Track_chart_Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_track_chart, container, false);
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
        activity.getSupportActionBar().setTitle("Popular Tracks");
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public void query(){
        ParseQuery<tracks> query = ParseQuery.getQuery(tracks.class);
        query.fromPin(TOP_SCORES_LABEL);
        query.include("user");
        //query.include("username");
        query.orderByDescending("createdAt");
        //query.fromLocalDatastore();
        query.findInBackground(new FindCallback<tracks>() {
            @Override
            public void done(final List<tracks> modelList, ParseException e) {
                ModelList.clear();
                ModelList.addAll(modelList);
                adapter.notifyDataSetChanged();

                /*ParseObject.unpinAllInBackground(TOP_SCORES_LABEL, modelList, new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {

                        ParseObject.pinAllInBackground(TOP_SCORES_LABEL, modelList);
                    }

                });*/
            }
        });
        second_query();
    }
    private void second_query(){
        ParseQuery<tracks> query = ParseQuery.getQuery(tracks.class);
        //query.fromPin(TOP_SCORES_LABEL);
        query.include("user");
        //query.include("username");
        query.orderByDescending("createdAt");
        //query.fromLocalDatastore();
        query.findInBackground(new FindCallback<tracks>() {
            @Override
            public void done(final List<tracks> modelList, ParseException e) {
                ModelList.clear();
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
    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
