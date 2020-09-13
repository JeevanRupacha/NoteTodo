package com.example.todoandnote.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoandnote.Activities.AddNewTodoActivity;
import com.example.todoandnote.Activities.MainActivity;
import com.example.todoandnote.Data.FeedReaderContract;
import com.example.todoandnote.Data.NoteTodo;
import com.example.todoandnote.Data.NoteTodoViewModel;
import com.example.todoandnote.R;
import com.example.todoandnote.Ui.RecyclerView.TodoRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment implements RoomModelSetup {

    //ViewModel NoteTodo architecture View model
    private static NoteTodoViewModel noteTodoViewModel;

    private GestureDetector mGesture;

    //first fragment or todo_section fragment
    private ViewGroup fragmentView;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    //All the todo_ data list for recycler view
    private List<NoteTodo> noteTodoList;

    //allNoteTodo data
    private static List<NoteTodo> allNoteTodo;

    //for log out to debug
    private final static String TAG = "FirstFragment";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    )
    {
        // Inflate the layout for this fragment
        fragmentView = (ViewGroup)inflater.inflate(R.layout.fragment_first, container, false);
        return fragmentView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        //creating viewModel object
        noteTodoViewModel = new ViewModelProvider(this).get(NoteTodoViewModel.class);

        //initializing the todoList arrayList object
        noteTodoList = new ArrayList<>();
        allNoteTodo = new ArrayList<>();

        //creating for observer in the ViewModel
        observerSetup();

        //creating recycler view
        recyclerSetup();

//        //calling the interaction to view
//        listenerSetup();

    }


    @Override
    public void observerSetup()
    {
            // View Model setting up observer for view Model
            noteTodoViewModel.getTodo().observe(getViewLifecycleOwner(), new Observer<List<NoteTodo>>() {

            @Override
            public void onChanged(List<NoteTodo> noteTodo) {
                ((TodoRecyclerViewAdapter) mAdapter).setNoteTodo(noteTodo);
            }
        });

        noteTodoViewModel.getAllData().observe(getViewLifecycleOwner(), new Observer<List<NoteTodo>>() {

            @Override
            public void onChanged(List<NoteTodo> noteTodo) {
                allNoteTodo.addAll(noteTodo);
            }
        });

    }

    @Override
    public void listenerSetup()
    {
        //implementing the swaping system
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir)
            {
                // Here is where you'll implement swipe to delete
                int id = (int)viewHolder.itemView.getTag();

                if( swipeDir == ItemTouchHelper.LEFT)
                {
                    noteTodoViewModel.changeDataType(FeedReaderContract.FeedEntry.DATATYPE_TODO_UNDONE, id);
                    Toast.makeText(getContext(), "left " + id, Toast.LENGTH_SHORT).show();
                    //left swap
                }
                if(swipeDir == ItemTouchHelper.RIGHT)
                {
                    //right swap
                    noteTodoViewModel.changeDataType(FeedReaderContract.FeedEntry.DATATYPE_TODO_DONE, id);
                    Toast.makeText(getContext(), "right", Toast.LENGTH_SHORT).show();
                }
            }


        }).attachToRecyclerView(recyclerView);

    }

    @Override
    public void recyclerSetup() {
            recyclerView =  fragmentView.findViewById(R.id.todo_recyclerView);
            mAdapter = new TodoRecyclerViewAdapter(getActivity().getApplicationContext());
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mAdapter);

            //set for data refresh after change data
            mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity != null;
        ActionBar actionBar = activity.getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.first_frament_title);
    }

    public static void insertData(NoteTodo noteTodo)
    {
        noteTodoViewModel.insetData(noteTodo);
    }

    public void updateData(int id )
    {

    }

    public static List<NoteTodo> getAllTodo()
    {
        return allNoteTodo;
    }

//    public class SimpleGestureListener extends GestureDetector.SimpleOnGestureListener {
//
//        @Override
//        public boolean onDown(MotionEvent event) {
//            // triggers first for both single tap and long press
//            return true;
//        }
//
//        @Override
//        public boolean onSingleTapUp(MotionEvent event) {
//            // triggers after onDown only for single tap
//            return true;
//        }
//
//        @Override
//        public void onLongPress(MotionEvent event) {
//            // triggers after onDown only for long press
//            super.onLongPress(event);
//            Log.d(TAG, "onLongPress: called");
//            listenerSetup();
//        }
//    }

}