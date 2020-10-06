package com.example.todoandnote.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.Toolbar;
import com.example.todoandnote.R;
import com.example.todoandnote.Ui.Animations.HomeAnimation;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.internal.operators.flowable.FlowableConcatMapEager;

/**
 * Note and documentation of MainActivityUtils
 * All the utils which is communicated with MainActivity and other outside class
 */

public class MainActivityUtils {
    private Context context;
    private static Activity activity;

    //from home fab Button
    private static FloatingActionButton fab;

    //NavButtonView in home
    private static BottomNavigationView navigationView;

    //toolbar in home
    private static Toolbar toolbar;

    //appBarLayout in home
    private static AppBarLayout appBarLayout;

    //ButtonNav Framelayout
    private static FrameLayout bottomNavFramelayout;

    // Main list container Framelayout
    private  static RelativeLayout mainContainerFrame;


    public MainActivityUtils(Context context , Activity activity)
    {
        this.context = context;
        this.activity = activity;
        fab = activity.findViewById(R.id.add_fab_first);
        navigationView = activity.findViewById(R.id.nav_button_home);
        toolbar = (Toolbar)activity.findViewById(R.id.toolbar);
        appBarLayout = activity.findViewById(R.id.appBarLayout);
        bottomNavFramelayout = activity.findViewById(R.id.nav_bottom_framelayout);
        mainContainerFrame = activity.findViewById(R.id.home_list_fragment);
    }


    public static void hideFab()
    {

        if(fab.getVisibility() != View.GONE) {
            fab.setVisibility(View.GONE);
        }

    }


    public static void showFab()
    {
        if(fab.getVisibility() != View.VISIBLE) {
            fab.setVisibility(View.VISIBLE);
        }
    }


    public static void hideNavButtonView()
    {
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,
                0,navigationView.getHeight());
        animate.setDuration(500);
        if(navigationView.getVisibility() != View.GONE) {
            navigationView.startAnimation(animate);
            navigationView.setVisibility(View.GONE);
        }
    }


    public static void showNavButtonView()
    {
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,
                navigationView.getHeight(),
                0);
        animate.setDuration(500);
        if( navigationView.getVisibility() != View.VISIBLE) {
            navigationView.startAnimation(animate);
            navigationView.setVisibility(View.VISIBLE);
        }
    }


    public static void showAppBarLayout()
    {
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,
                bottomNavFramelayout.getHeight(),
                bottomNavFramelayout.getHeight());
        animate.setDuration(200);
        if( bottomNavFramelayout.getVisibility() != View.VISIBLE) {
            bottomNavFramelayout.startAnimation(animate);
            bottomNavFramelayout.setVisibility(View.VISIBLE);
        }
    }


    public static void hideAppBarLayout()
    {
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,
                bottomNavFramelayout.getHeight(),
                0);
        animate.setDuration(300);
        if( bottomNavFramelayout.getVisibility() != View.GONE) {
            bottomNavFramelayout.startAnimation(animate);
            bottomNavFramelayout.setVisibility(View.GONE);
        }
    }


}
