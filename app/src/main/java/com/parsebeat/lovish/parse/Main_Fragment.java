package com.parsebeat.lovish.parse;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Main_Fragment extends Fragment  {

    ProgressBar progressBar;
    HrzntlProflAdptr popadapter;
    List_adapter firstadapter;
    List_adapter secondadaper;
    List_adapter thirdadapter;
    RecyclerView vertial_Recycle;
    ListView dailyrotationlist;
    ListView newtracks;
    ListView staffpickslist;
    String navTitles[];
    TypedArray navIcons;
    Toolbar toolbar;
    TextView poplrtckbrn;
    Button allnewtrckbtn,dailyrtnbtn;

    public Stack<String> mFragmentStack;
    private List<tracks> ModelList = new ArrayList<tracks>();
    final String TOP_SCORES_LABEL = "topScores";
    boolean connected = false;
    public Main_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container,false);
        mFragmentStack = new Stack<String>();
        vertial_Recycle = (RecyclerView) view.findViewById(R.id.main_popular_horizntl);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        vertial_Recycle.setHasFixedSize(true);
        vertial_Recycle.setLayoutManager(llm);
        dailyrotationlist = (ListView) view.findViewById(R.id.daily_rotation_list);
        newtracks = (ListView) view.findViewById(R.id.new_tracks_list);
        staffpickslist = (ListView) view.findViewById(R.id.staff_picks_list);
        toolbar= (Toolbar) view.findViewById(R.id.toolbar);
        poplrtckbrn = (TextView) view.findViewById(R.id.seeall);
        progressBar = (ProgressBar) view.findViewById(R.id.prog);
        poplrtckbrn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment seeallfragment = new Track_chart_Fragment();
                String tag = poplrtckbrn.toString();
                mFragmentStack.add(tag);
                fragmentTransaction.replace(R.id.container, seeallfragment,tag);
                fragmentTransaction.addToBackStack(tag);
                fragmentTransaction.commit();
            }
        });
        allnewtrckbtn=(Button) view.findViewById(R.id.see_all_newtrcks_button);
        allnewtrckbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment allnewtracks = new New_tracks_Fragment();
                String tag = poplrtckbrn.toString();
                mFragmentStack.add(tag);
                fragmentTransaction.replace(R.id.container, allnewtracks,tag);
                fragmentTransaction.addToBackStack(tag);
                fragmentTransaction.commit();
            }
        });
        dailyrtnbtn =(Button) view.findViewById(R.id.dailyrotationbutton);
        dailyrtnbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment daily_tracks_fragment = new Daily_tracks_Fragment();
                String tag = poplrtckbrn.toString();
                mFragmentStack.add(tag);
                fragmentTransaction.replace(R.id.container, daily_tracks_fragment,tag);
                fragmentTransaction.addToBackStack(tag);
                fragmentTransaction.commit();
            }
        });
        int color = 0xFF00FF00;
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);

        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //query();
    }
    @Override
    public void onPause() {

        super.onPause();

        //getSupportFragmentManager().findFragmentByTag("MyFragment").setRetainInstance(true);
    }

    @Override
    public void onResume() {

        super.onResume();

        //getActivity().getSupportFragmentManager().findFragmentByTag("MyFragment").getRetainInstance();

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //query();
        popadapter = new HrzntlProflAdptr(getContext(), ModelList);
        vertial_Recycle.setAdapter(popadapter);
        firstadapter = new List_adapter(getContext(), ModelList);
        dailyrotationlist.setAdapter(firstadapter);
        secondadaper = new List_adapter(getContext(),ModelList);
        newtracks.setAdapter(secondadaper);
        thirdadapter = new List_adapter(getContext(),ModelList);
        staffpickslist.setAdapter(thirdadapter);



    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //new FirstQuery().execute();
        query();
        ((MainActivity)getActivity()).setUpToolbar(toolbar);

    }
    @Override
    public void onStart(){
        super.onStart();
        //query();

    }
    private class FirstQuery extends AsyncTask<Void , Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected Void doInBackground(Void... args) {
            //final ArrayList<tracks> blist = new ArrayList<tracks>();
            ParseQuery<tracks> query = ParseQuery.getQuery(tracks.class);
            query.fromPin(TOP_SCORES_LABEL);
            query.setLimit(5);
            query.orderByDescending("createdAt");
            query.findInBackground(new FindCallback<tracks>() {
                @Override
                public void done(final List<tracks> modelList, ParseException e) {
                    if (modelList.size()>0) {

                            ModelList.clear();
                           ModelList.addAll(modelList);
                    }else {
                        new SecondQuery().execute();
                    }
                }
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
           // ModelList.addAll(ablist);
            popadapter.notifyDataSetChanged();
            firstadapter.notifyDataSetChanged();
            secondadaper.notifyDataSetChanged();
            thirdadapter.notifyDataSetChanged();
            Utility.setDynamicHeight(staffpickslist);
            Utility.setDynamicHeight(newtracks);
            Utility.setDynamicHeight(dailyrotationlist);
            progressBar.setVisibility(View.GONE);
            //isConnected();
            //new ListProcess().execute();
        }


    }
    private void query(){
        ParseQuery<tracks> query = ParseQuery.getQuery(tracks.class);
        query.fromPin(TOP_SCORES_LABEL);
        query.setLimit(5);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<tracks>() {
            @Override
            public void done(final List<tracks> modelList, ParseException e) {
                //for (tracks i:modelList){ModelList.add(i);}
                if(modelList.size()>0){
                    ModelList.clear();
                    ModelList.addAll(modelList);
                    popadapter.notifyDataSetChanged();
                    firstadapter.notifyDataSetChanged();
                    secondadaper.notifyDataSetChanged();
                    thirdadapter.notifyDataSetChanged();
                    Utility.setDynamicHeight(staffpickslist);
                    Utility.setDynamicHeight(newtracks);
                    Utility.setDynamicHeight(dailyrotationlist);
                }else {
                    isConnected();
                    new SecondQuery().execute();
                }
            }
        });
    }
    public boolean isConnected (){

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            Toast.makeText(getActivity(), " Connected ", Toast.LENGTH_LONG).show();
            new SecondQuery().execute();
            return true;
        }catch (Exception e){
            Log.d("connectivity", e.toString());
            Toast.makeText(getActivity(), " not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
    }
   private class SecondQuery extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... args) {
            //final ArrayList<tracks> alist = new ArrayList<tracks>();

            final ParseQuery<tracks> query = ParseQuery.getQuery(tracks.class);
            //query.fromPin(TOP_SCORES_LABEL);
            query.orderByDescending("createdAt");
            query.setLimit(5);
            query.findInBackground(new FindCallback<tracks>() {
                @Override
                public void done(final List<tracks> modelList, ParseException e) {
                    Log.d("data", modelList.toString());


                        ModelList.clear();
                        ModelList.addAll(modelList);
                        //popadapter.notifyDataSetChanged();
                        //firstadapter.notifyDataSetChanged();
                        //secondadaper.notifyDataSetChanged();
                        //thirdadapter.notifyDataSetChanged();
                    /*ParseObject.unpinAllInBackground(TOP_SCORES_LABEL, modelList, new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {

                            ParseObject.pinAllInBackground(TOP_SCORES_LABEL, modelList);
                        }

                    });*/
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            popadapter.notifyDataSetChanged();
            firstadapter.notifyDataSetChanged();
            secondadaper.notifyDataSetChanged();
            thirdadapter.notifyDataSetChanged();
            Utility.setDynamicHeight(staffpickslist);
            Utility.setDynamicHeight(newtracks);
            Utility.setDynamicHeight(dailyrotationlist);

            progressBar.setVisibility(View.GONE);
            ParseObject.unpinAllInBackground(TOP_SCORES_LABEL, ModelList, new DeleteCallback() {
                @Override
                public void done(ParseException e) {

                    ParseObject.pinAllInBackground(TOP_SCORES_LABEL, ModelList);
                }

            });
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}

