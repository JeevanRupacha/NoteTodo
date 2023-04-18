package com.example.todoandnote.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.todoandnote.Data.FeedReaderContract;
import com.example.todoandnote.Fragments.FirstFragment;
import com.example.todoandnote.Fragments.LeftFragment;
import com.example.todoandnote.Fragments.RightFragment;
import com.example.todoandnote.GestureDetector.GestureListener;
import com.example.todoandnote.R;
import com.example.todoandnote.Utils.MainActivityUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * This is MainActivity all the entry point starts from here
 * activity_main.xml is main xml to load at first when it created the app
 */

public class MainActivity extends AppCompatActivity{

    //number of pages in fragment
    private static final int NUM_PAGES = 1;
    private static final String TAG = "MainActivity_Debug";

    //View pager for swapping fragment
    private ViewPager2 viewPager;

    //fragmentAdapter to control all the fragments
    private FragmentStateAdapter pagerAdapter;

    //list of fragment which is swapable
    private List<Fragment> fragment;

    //adding the todo_ and note button
    private Button addTodo, addNote;

    //gesture dector for detect touch system
    private GestureDetectorCompat mGestureDetector;

    //fab button
    private FloatingActionButton fab;

    //navigation button home
    private BottomNavigationView bottomNavigationView;

    //Toolbar for home activity
    private Toolbar toolbar;

    //list main container in
    private RelativeLayout mainContainer ;

    //getting fragmnet manager
    //private FragmentManager fragmentManager = new getFragmentActivity();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);


        new MainActivityUtils(this, this);


        //main container initialize here
//        mainContainer = (RelativeLayout) findViewById(R.id.home_list_fragment);
//        (RelativeLayout) findViewById(R.id.home_list_fragment).getLayoutTransition()
//                .enableTransitionType(LayoutTransition.CHANGING);

        //fab button initialize
        fab = findViewById(R.id.add_fab_first);

        floatingButtonClick();


        //gesture object  initialize
        mGestureDetector = new GestureDetectorCompat(this,new GestureListener(this));

        //Shrink and Expand Animation for view while hide dispaly the toolbar and
        //nav bar
//        findViewById(R.id.home_list_fragment).startAnimation(HomeAnimation.shrinkExpandAnimationView());



        fragmentCreate(FeedReaderContract.FeedEntry.DATATYPE_TODO);

        bottomNavigationView =  findViewById(R.id.nav_button_home);

        bottomNavigationView.getMenu().getItem(2).setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                int id = item.getItemId();
                int currentItemIndex = viewPager.getCurrentItem();

                switch (id) {
                    case R.id.left_nav_button:
                        //go to left fragment
                        if (currentItemIndex >= 0)
                        {
                            viewPager.setCurrentItem(currentItemIndex - 1);

                        }
                        break;

                    case R.id.right_nav_button:
                        //goto right fragment
                        if (currentItemIndex < 3) {
                            viewPager.setCurrentItem(currentItemIndex + 1);
                        }

                    case R.id.todo_section:
                        //todo_section

//                        FirstFragment firstFragment = new FirstFragment();
//                        firstFragment.observerSetup(FeedReaderContract.FeedEntry.DATATYPE_TODO);
                        break;

                    case R.id.not_todo_section:
                        //not todo_ section

//                        FirstFragment firstFragment2 = new FirstFragment();
//                        firstFragment2.observerSetup(FeedReaderContract.FeedEntry.DATATYPE_TODO_NOTODO);
                        break;

                    case R.id.more_section:
                        //More Navigation section
                        break;

                    default:break;
                }
                return true;
            }
        });



    }

    public void fragmentCreate(String dataType)
    {

        //fragment
        fragment = new ArrayList<>();
        fragment.add(new FirstFragment(dataType));
        fragment.add(new LeftFragment());
        fragment.add(new RightFragment());

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.fragment_pager);
        pagerAdapter = new MainActivity.ScreenSlidePagerAdapter(this, fragment);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);


    }



    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.navigation_items, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }



    private static class ScreenSlidePagerAdapter extends FragmentStateAdapter {

        private List<Fragment> fragmentList;

        public ScreenSlidePagerAdapter(FragmentActivity fa, List<Fragment> fragmentList) {
            super(fa);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment createFragment(int position) {
//            if(position >= 0){
//                return fragmentList.get(position);
//            }
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

    /**
     * Floating Button in main Activity which creates a Popup alert dialog
     * Inside the popup alert the three list button is seen
     * 1. TODO_
     * 2. NOTE
     * 3. NOT TODO_
     */

    private void floatingButtonClick()
    {
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //popup dialog for adding task
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(getBaseContext());
                View popupView = inflater.inflate(R.layout.add_option_popup, null);
                builder.setView(popupView);
                builder.setTitle("Select The Options");
                final AlertDialog alert = builder.create();
                alert.show();

                //initializing the button of popup dialog
                addTodo = popupView.findViewById(R.id.todo_add_button);
                addNote = popupView.findViewById(R.id.note_add_button);

                addTodo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, AddNewTodoActivity.class));
                        Toast.makeText(MainActivity.this, "add todo", Toast.LENGTH_SHORT).show();
                        alert.dismiss();
                    }
                });

                addNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "note added ", Toast.LENGTH_SHORT).show();
                        alert.dismiss();
                    }
                });

            }
        });
    }


}