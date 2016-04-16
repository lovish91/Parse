package com.parsebeat.lovish.parse;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class Upload_track_Fragment extends Fragment {

    Toolbar toolbar;
    EditText nme_of_song,gnre_of_song,desc_of_song;
    Button save_it;
    public Upload_track_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_track, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        nme_of_song = (EditText) view.findViewById(R.id.song_name);
        gnre_of_song =(EditText) view.findViewById(R.id.song_genre);
        desc_of_song = (EditText) view.findViewById(R.id.song_desc);
        save_it = (Button) view.findViewById(R.id.save_it);
        save_it.setOnClickListener(save_query());
        return view;
    }
    private View.OnClickListener save_query(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tracks Tracks = new tracks();
                Tracks.setfoo(nme_of_song.getText().toString());
                Tracks.setgenre(gnre_of_song.getText().toString());
                Tracks.settname(desc_of_song.getText().toString());
                Tracks.setduration("0");
                Tracks.setplays("0");
                Tracks.sethearts("0");
                Tracks.put("user",ParseUser.getCurrentUser());
                Tracks.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            Toast.makeText(getActivity(),"save is sucessfull",Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(getActivity(),"Save is unsecessfull",Toast.LENGTH_SHORT).show();
                    }
                });
                //Fragment main_fragment = new Main_Fragment();
                //FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                //fragmentManager.beginTransaction().replace(R.id.container, main_fragment).commit();
            }
        };
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity)getActivity()).setUpToolbar(toolbar);
    }

}
