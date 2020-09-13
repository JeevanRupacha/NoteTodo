package com.example.todoandnote.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.todoandnote.Fragments.FirstFragment;
import com.example.todoandnote.Fragments.LeftFragment;
import com.example.todoandnote.Fragments.RightFragment;
import com.example.todoandnote.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //number of pages in fragment
    private static final int NUM_PAGES = 3;

    //View pager for swapping fragment
    private ViewPager2 viewPager;

    //fragmentAdapter to control all the fragments
    private FragmentStateAdapter pagerAdapter;

    //list of fragment which is swapable
    private List<Fragment> fragment;

    //adding the todo_ and note button
    private Button addTodo, addNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        floatingButtonClick();


        //fragment
        fragment = new ArrayList<>();
        fragment.add(new LeftFragment());
        fragment.add(new FirstFragment());
        fragment.add(new RightFragment());

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = (ViewPager2)findViewById(R.id.fragment_pager);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

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
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }


    private void floatingButtonClick()
    {
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.add_fab_first);
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