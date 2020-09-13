package com.example.todoandnote.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoandnote.Data.NoteTodo;
import com.example.todoandnote.Data.NoteTodoViewModel;
import com.example.todoandnote.R;
import com.example.todoandnote.Ui.RecyclerView.TodoDoneRecyclerViewAdapter;
import com.example.todoandnote.Ui.RecyclerView.TodoRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class RightFragment extends Fragment implements RoomModelSetup{

    //ViewModel NoteTodo architecture View model
    private static NoteTodoViewModel noteTodoViewModel;


    //first fragment or todo_section fragment
    private ViewGroup fragmentView;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    //All the todo_ data list for recycler view
    private List<NoteTodo> noteTodoList;

    //for log out to debug
    private final static String TAG = "RightFragment";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        // Inflate the layout for this fragment
        fragmentView = (ViewGroup)inflater.inflate(R.layout.fragment_right, container, false);
        return fragmentView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //creating viewModel object
        noteTodoViewModel = new ViewModelProvider(this).get(NoteTodoViewModel.class);

        //initializing the todoList arrayList object
        noteTodoList = new ArrayList<>();

        //creating for observer in the ViewModel
        observerSetup();

        //creating recycler view
        recyclerSetup();

        //listener for data change through the live data ViewModel
        listenerSetup();


    }

    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity != null;
        ActionBar actionBar = activity.getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.right_frament_title);

    }

    @Override
    public void observerSetup() {
        // View Model setting up observer for view Model
        noteTodoViewModel.getTodoDone().observe(getViewLifecycleOwner(), new Observer<List<NoteTodo>>() {

            @Override
            public void onChanged(List<NoteTodo> noteTodo) {
                ((TodoDoneRecyclerViewAdapter) mAdapter).setNoteTodo(noteTodo);
            }
        });
    }

    @Override
    public void listenerSetup() {

    }

    @Override
    public void recyclerSetup() {
        recyclerView =  fragmentView.findViewById(R.id.done_recyclerView);
        mAdapter = new TodoDoneRecyclerViewAdapter(getActivity().getApplicationContext());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        //set for data refresh after change data
        mAdapter.notifyDataSetChanged();
    }
}