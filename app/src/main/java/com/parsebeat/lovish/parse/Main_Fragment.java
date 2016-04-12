package com.parsebeat.lovish.parse;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Main_Fragment extends Fragment implements ViewPager.OnPageChangeListener {

    Parcelable state;
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
    private List<tracks> ModelList;
    Dialog pdialog;
    public static int PAGE ;
    private ViewPager mCardsViewPager;
    private float MIN_SCALE = 0.7f;
    private float MAX_SCALE = 1f;
    private float DIFF = MAX_SCALE - MIN_SCALE;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    final String TOP_SCORES_LABEL = "topScores";
    private static final Integer[] IMAGES= {R.drawable.images,R.drawable.images,R.drawable.images,R.drawable.images};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
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
        /*mCardsViewPager = (ViewPager) view.findViewById(R.id.viewpager_card);
        mCardsViewPager.setAdapter(new CardsPagerAdapter());
        mCardsViewPager.setCurrentItem(PAGE);
        mCardsViewPager.setPageMargin(-260);
        mCardsViewPager.setOffscreenPageLimit(3);
        mCardsViewPager.setOnPageChangeListener(this);
        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);
        mPager = (ViewPager) view.findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(getContext(), ImagesArray));
        CirclePageIndicator indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);



        indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(4* density);

        NUM_PAGES =IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });*/


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
        query();
        popadapter = new HrzntlProflAdptr(getContext(), ModelList);
        vertial_Recycle.setAdapter(popadapter);

    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

                //new ListProcess().execute();
        //query();

        navTitles = getResources().getStringArray(R.array.navDrawerItems);
        navIcons = getResources().obtainTypedArray(R.array.navDrawer);
        ((MainActivity)getActivity()).setUpToolbar(toolbar);





        //toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        //AppCompatActivity activity = (AppCompatActivity) getActivity();
        //activity.setSupportActionBar(toolbar);
        //activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.profile);
        //activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //activity.getSupportActionBar().setTitle("Your Title");

        //adapter = new newAdapter(navTitles, navIcons, getActivity());
        //recyclerView.setAdapter(adapter);
    }
    public void query(){
        ModelList = new ArrayList<tracks>();
        ParseQuery<tracks> query = ParseQuery.getQuery(tracks.class);
        query.include("user");
        query.include("username");
        query.setLimit(5);
        query.orderByDescending("createdAt");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<tracks>() {
            @Override
            public void done(final List<tracks> modelList, ParseException e) {

                ModelList.addAll(modelList);
              //popadapter = new HrzntlProflAdptr(getContext(), modelList);
              //vertial_Recycle.setAdapter(popadapter);
                popadapter.notifyDataSetChanged();
                firstadapter = new List_adapter(getContext(), modelList);
                dailyrotationlist.setAdapter(firstadapter);
                firstadapter.notifyDataSetChanged();
                secondadaper = new List_adapter(getContext(),modelList);
                newtracks.setAdapter(secondadaper);
                secondadaper.notifyDataSetChanged();
                thirdadapter = new List_adapter(getContext(),modelList);
                staffpickslist.setAdapter(thirdadapter);
                thirdadapter.notifyDataSetChanged();
                Utility.setDynamicHeight(staffpickslist);
                Utility.setDynamicHeight(newtracks);
                Utility.setDynamicHeight(dailyrotationlist);

                ParseObject.unpinAllInBackground(TOP_SCORES_LABEL, modelList, new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {

                        ParseObject.pinAllInBackground(TOP_SCORES_LABEL, modelList);
                    }

                });
            }
        });
    }

  /*  private class ListProcess extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            pdialog = new ProgressDialog(getActivity());
            // Set progressdialog title
            pdialog.setTitle("Parse.com Custom ListView Tutorial");
            // Show progressdialog
            pdialog.show();
            pdialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... args) {
            modelList = new ArrayList<tracks>();

            ParseQuery<tracks> query = ParseQuery.getQuery(tracks.class);
            query.include("user");
            query.include("username");
            query.setLimit(5);
            query.orderByDescending("createdAt");
            try {

            modelList= query.find();
                    for (tracks i :modelList) {
                        Log.d("hello",modelList.toString());
                    }
            }catch (com.parse.ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            popadapter = new HrzntlProflAdptr(getContext(), modelList);
            vertial_Recycle.setAdapter(popadapter);
            popadapter.notifyDataSetChanged();
            firstadapter = new List_adapter(getContext(), modelList);
            dailyrotationlist.setAdapter(firstadapter);
            firstadapter.notifyDataSetChanged();
            secondadaper = new List_adapter(getContext(),modelList);
            newtracks.setAdapter(secondadaper);
            secondadaper.notifyDataSetChanged();
            thirdadapter = new List_adapter(getContext(),modelList);
            staffpickslist.setAdapter(thirdadapter);
            thirdadapter.notifyDataSetChanged();
            Utility.setDynamicHeight(staffpickslist);
            Utility.setDynamicHeight(newtracks);
            Utility.setDynamicHeight(dailyrotationlist);
            pdialog.dismiss();
        }

    }*/

    private class CardsPagerAdapter extends PagerAdapter {

        private boolean mIsDefaultItemSelected = false;

        public int[] mCards = {
                R.drawable.images,
                R.drawable.images,
                R.drawable.images,
                R.drawable.images,
                R.drawable.images,
                R.drawable.images,
                R.drawable.img_one,
                R.drawable.img_three };

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //View page = getLayoutInflater().inflate(R.layout.vertical_items, container, false);
            ImageView cardImageView = (ImageView) View.inflate(container.getContext(), R.layout.vertical_items, null);
            cardImageView.setImageDrawable(getResources().getDrawable(mCards[position]));
            cardImageView.setTag(position);
            PAGE= getCount()*1000/2;

            if (!mIsDefaultItemSelected) {
                cardImageView.setScaleX(MAX_SCALE);
                cardImageView.setScaleY(MAX_SCALE);
                mIsDefaultItemSelected = true;
            } else {
                cardImageView.setScaleX(MIN_SCALE);
                cardImageView.setScaleY(MIN_SCALE);
            }

            container.addView(cardImageView);
            return cardImageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mCards.length;
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        for (int i = 0; i < mCardsViewPager.getChildCount(); i++) {
            View cardView = mCardsViewPager.getChildAt(i);
            int itemPosition = (Integer) cardView.getTag();

            if (itemPosition == position) {
                cardView.setScaleX(MAX_SCALE- DIFF*positionOffset);
                cardView.setScaleY(MAX_SCALE- DIFF*positionOffset);
            }

            if (itemPosition == (position + 1)) {
                cardView.setScaleX(MIN_SCALE +DIFF*positionOffset);
                cardView.setScaleY(MIN_SCALE +DIFF*positionOffset);
            }

        }
    }


    @Override
    public void onPageSelected(int position) {

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}

