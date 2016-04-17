package com.parsebeat.lovish.parse;



import android.content.Intent;
import android.content.res.TypedArray;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity {


    DrawerLayout drawer;
    NavigationView nvd;
    String navTitles[];
    TypedArray navIcons;
    Shows_Fragment drawerFragment;
    ActionBarDrawerToggle Dtoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navTitles = getResources().getStringArray(R.array.navDrawerItems);
        navIcons = getResources().obtainTypedArray(R.array.navDrawerIcons);


        nvd = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvd);
        ////////////////

        drawer = (DrawerLayout) findViewById(R.id.drawer);

      Fragment main_fragment = new Main_Fragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, main_fragment, null);
        fragmentTransaction.commit();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }
    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = Main_Fragment.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = Shows_Fragment.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = ProfileFragment.class;
                break;
            case R.id.nav_fourth_fragment:
                fragmentClass = Upload_track_Fragment.class;
                break;
            default:
                fragmentClass = Main_Fragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title

        // Close the navigation drawer
        drawer.closeDrawers();
    }
    public void setUpToolbar(Toolbar toolbar){
        if (toolbar != null){
            setSupportActionBar(toolbar);
            Dtoogle = new ActionBarDrawerToggle(this, drawer,toolbar, R.string.drawer_open, R.string.drawer_close) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                    // open I am not going to put anything here)
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    // Code here will execute once drawer is closed

                }

            }; // Drawer Toggle Object Made
            drawer.setDrawerListener(Dtoogle); // Drawer Listener set to the Drawer toggle
            Dtoogle.syncState();
        }
    }

    public void loadLogin() {
        Intent intent = new Intent(this, Log_in.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
