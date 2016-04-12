package com.parsebeat.lovish.parse;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

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
 * {@link Shows_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Shows_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Shows_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    Dialog pdialog;
    ProgressBar progressBar;
    Context context;
    RecyclerView hrntlrecyl;
    HrzntlProflAdptr adapter;
    List<ParseObject> list;
    List_adapter adap;
    ListView testlist;
    public List<tracks> modelList;
    final String TOP_SCORES_LABEL = "topScores";
    private FragmentDrawerListener drawerListener;
    private static final String LIST_STATE = "listState";
    private Parcelable mListState = null;
    // TODO: Rename and change types of parameters


    private OnFragmentInteractionListener mListener;

    public Shows_Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Shows_Fragment newInstance(String param1, String param2) {
        Shows_Fragment fragment = new Shows_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //new ListProcess().execute();
        query();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shows, container, false);
        hrntlrecyl = (RecyclerView) view.findViewById(R.id.drawer_menu);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hrntlrecyl.setHasFixedSize(true);
        hrntlrecyl.setLayoutManager(layoutManager);
        testlist = (ListView) view.findViewById(R.id.testlist);
        progressBar = (ProgressBar) view.findViewById(R.id.prog);
        //adap= new List_adapter(getContext(),modelList);


        Utility.setDynamicHeight(testlist);
        int color = 0xFF0033CC;
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void query(){
        //modelList = new ArrayList<tracks>();
        ParseQuery<tracks> query = ParseQuery.getQuery(tracks.class);
        query.orderByDescending("createdAt");
        query.include("user");
        query.include("username");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<tracks>() {
            @Override
            public void done(final List<tracks> modelList, ParseException e) {
                adap = new List_adapter(getContext(), modelList);
                testlist.setAdapter(adap);
                adap.notifyDataSetChanged();

                ParseObject.unpinAllInBackground(TOP_SCORES_LABEL, modelList, new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {

                        ParseObject.pinAllInBackground(TOP_SCORES_LABEL, modelList);
                    }

                });
            }
        });
        /*try {
            modelList = query.find();
            for (tracks i : modelList) {

                adap.notifyDataSetChanged();
            }
        } catch (com.parse.ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }*/
        //return null;
    }
/*
    private class ListProcess extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... args) {
            //modelList = new ArrayList<Track_sub>();
            ParseQuery<> query = ParseQuery.getQuery(.class);
            query.orderByDescending("createdAt");
            try {
                modelList = query.find();
                for ( i : modelList) {

                }
            } catch (com.parse.ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
           // adapter = new HrzntlProflAdptr(getContext(), modelList);
            //hrntlrecyl.setAdapter(adapter)
            progressBar.setVisibility(View.GONE);

//            pdialog.dismiss();
        }
    }*/
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface FragmentDrawerListener {
        void onDrawerItemSelected(View view, int position);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
