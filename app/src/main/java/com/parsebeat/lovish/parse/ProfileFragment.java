package com.parsebeat.lovish.parse;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;



public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    Dialog pdialog;
    RecyclerView hrntlrecyl;
    HrzntlProflAdptr adapter;
    List<ParseObject> list;
    private List<tracks> modelList;
    RecyclerView latest_tracks;
    Linear_list_adapter second_adapter;
    ImageButton editbutton;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artist_profile, container, false);
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("itemTitle");
        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.img_three);
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
                            collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(mutedColor));
                            collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(mutedColor));

                        }
                    }
                });
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        hrntlrecyl = (RecyclerView) view.findViewById(R.id.popular_tracks);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hrntlrecyl.setHasFixedSize(true);
        hrntlrecyl.setLayoutManager(layoutManager);
        hrntlrecyl.setNestedScrollingEnabled(false);
        latest_tracks = (RecyclerView) view.findViewById(R.id.latest_tracks);
        LinearLayoutManager layoutManage = new LinearLayoutManager(getActivity());
        layoutManage.setOrientation(LinearLayoutManager.VERTICAL);
        latest_tracks.setHasFixedSize(true);
        latest_tracks.setLayoutManager(layoutManage);
        latest_tracks.setNestedScrollingEnabled(false);
        //collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        editbutton = (ImageButton) view.findViewById(R.id.editbutton);
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getActivity(), Activity_user_profile_edit.class);
                startActivityForResult(intent, 2);// Activity is started with requestCode 2
            }
        });

    return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            //String message=data.getStringExtra("MESSAGE");
            //textView1.setText(message);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new ListProcess().execute();
        ((MainActivity)getActivity()).setUpToolbar(toolbar);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


   @Override
    public void onAttach(Context context) {
        super.onAttach(context);

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

    private class ListProcess extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            pdialog = new ProgressDialog(getActivity());
            // Set progressdialog title
            pdialog.setTitle("Parse.com Custom ListView Tutorial");
            // Show progressdialog
            pdialog.show();
        }

        @Override
        protected Void doInBackground(Void... args) {
            modelList = new ArrayList<tracks>();
            ParseQuery<tracks> query = ParseQuery.getQuery(tracks.class);
            query.orderByDescending("createdAt");
            try {
                modelList = query.find();
                for (tracks i : modelList) {

                }
            } catch (com.parse.ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            adapter = new HrzntlProflAdptr(getContext(), modelList);
            hrntlrecyl.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            second_adapter = new Linear_list_adapter(getContext(), modelList);
            latest_tracks.setAdapter(second_adapter);
            second_adapter.notifyDataSetChanged();
            pdialog.dismiss();
        }
    }
}
