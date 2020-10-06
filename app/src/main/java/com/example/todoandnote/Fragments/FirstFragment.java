package com.example.todoandnote.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
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
import androidx.core.view.GestureDetectorCompat;
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
import com.example.todoandnote.GestureDetector.GestureListener;
import com.example.todoandnote.R;
import com.example.todoandnote.Ui.RecyclerView.TodoRecyclerViewAdapter;
import com.example.todoandnote.Utils.MainActivityUtils;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment implements RoomModelSetup {

    //ViewModel NoteTodo architecture View model
    private static NoteTodoViewModel noteTodoViewModel;

    //private GestureDetectorCompat mGesture;
    private GestureDetectorCompat mGesture;

    //first fragment or todo_section fragment
    private ViewGroup fragmentView;

    //Recyclerview for the list of todo_ item
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    //All the todo_ data list for recycler view
    private List<NoteTodo> noteTodoList;

    //allNoteTodo data
    private static List<NoteTodo> allNoteTodo;

    //for log out to debug
    private final static String TAG = "FirstFragment";

    //data Type for todo and nottodo
    private static String dataType;


    public FirstFragment(String dataType)
    {
        this.dataType =dataType;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    )
    {
        // Inflate the layout for this fragment
        fragmentView = (ViewGroup)inflater.inflate(R.layout.fragment_first, container, false);

        mGesture = new GestureDetectorCompat(getActivity(), new GestureListener(getContext()));
        fragmentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                 mGesture.onTouchEvent(event);
                 return true;
            }
        });


//        MainActivityUtils.hideNavButtonView();
//        MainActivityUtils.hideFab();
//        MainActivityUtils.hideAppBarLayout();

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
        observerSetup(dataType);


        //creating recycler view
        recyclerSetup();

        //calling the interaction to view
        listenerSetup();


    }

    @Override
    public void observerSetup(String dataType)
    {
      if(dataType.equals(FeedReaderContract.FeedEntry.DATATYPE_TODO))
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
      }else if(dataType.equals(FeedReaderContract.FeedEntry.DATATYPE_TODO_NOTODO))
      {
          // View Model setting up observer for view Model
          noteTodoViewModel.getNoTodo().observe(getViewLifecycleOwner(), new Observer<List<NoteTodo>>() {

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

    }

    @Override
    public void listenerSetup()
    {
        //implementing the swapping system
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



        //setup for gesture listener
            recyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mGesture.onTouchEvent(event);
                    return false;
                }
            });


            mAdapter = new TodoRecyclerViewAdapter(getActivity().getApplicationContext());
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

    public static void updateData(NoteTodo noteTodo)
    {
        noteTodoViewModel.updateData(noteTodo);
    }


    public static List<NoteTodo> getAllTodo()
    {
        return allNoteTodo;
    }


    private class FirstFragmentGestureListener implements GestureDetector.OnGestureListener
    {
        public FirstFragmentGestureListener(Context context)
        {

        }

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

            Toast.makeText(getContext(), "onLongpress", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            if(e1 != null && e2 != null)
            {
                if (e2.getY() > e1.getY())
                {
                    //down direction
                    MainActivityUtils.showNavButtonView();
                    MainActivityUtils.showFab();
                    MainActivityUtils.showAppBarLayout();
//                    Toast.makeText(getContext(), "down ", Toast.LENGTH_SHORT).show();
                }else{
                    MainActivityUtils.hideNavButtonView();
                    MainActivityUtils.hideFab();
                    MainActivityUtils.hideAppBarLayout();
                }
            }


            return false;
        }
    }
}