package com.example.todoandnote.Ui.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoandnote.Data.NoteTodo;
import com.example.todoandnote.R;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TodoDoneRecyclerViewAdapter extends
        RecyclerView.Adapter<TodoDoneRecyclerViewAdapter.TodoViewHolder>
{
    private static final String TAG = "TodoDone RecyclerView Adapter";
    private Context context;

    //list of all the data of noteTodo
    private List<NoteTodo> data;

    //oneDay time for grouping in day date
    private String oneDay;

    //arrayList of color for seperation of the list by one day differ
    private HashMap<String, Integer> colorHashMap;

    //random number to track previous random value
    private int preRandomVal = -1;

    //index value for the color array
    private int indexCol = 0;


    //random background list
    private  int[] randomBackgroundList = new int[]{
            R.drawable.random_back_list1
            ,R.drawable.random_back_list2
            ,R.drawable.random_back_list3,
            R.drawable.random_back_list6,
            R.drawable.random_back_list7,
            R.drawable.random_back_list8,
            R.drawable.random_back_list9,
            R.drawable.random_back_list10,
            R.drawable.random_back_list11,
            R.drawable.random_back_list12,
            R.drawable.random_back_list13,
            R.drawable.random_back_list14,
            R.drawable.random_back_list15,

    };


    public TodoDoneRecyclerViewAdapter(Context context)
    {
        this.context = context;
        colorHashMap = new HashMap<>();
    }

    public void setNoteTodo(List<NoteTodo> noteTodoData)
    {
        if( noteTodoData != null) {
            this.data = noteTodoData;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_single_list_done_undone, parent, false);

        return new TodoViewHolder(listView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {

        //noteTodo object initialize
        NoteTodo noteTodo = data.get(position);

        Date date = new Date(noteTodo.getAddedTime().getTime());
        String strDate = date.toString().trim();


        Log.d("DATE", "onBindViewHolder: " + colorHashMap.get(strDate));


        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(noteTodo
                .getAddedTime().getTime());

        Log.d(TAG, "Date test " + strDate);
            if(colorHashMap.get(strDate) == null)
            {
                /***
                 * generate color different if random color came in same twice again
                 */
                if(indexCol < randomBackgroundList.length)
                {
                    colorHashMap.put(strDate, randomBackgroundList[indexCol]);
                    indexCol++;
                }else{
                    indexCol = 0;
                    colorHashMap.put(strDate, randomBackgroundList[indexCol]);
                    indexCol++;
                }
            }


        holder.linearLayout.setBackgroundResource(colorHashMap.get(strDate));
        holder.content.setText(noteTodo.getContent());
        holder.dateAdded.setText(timeAgo);
        holder.itemView.setTag(noteTodo.getId());

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    public static class TodoViewHolder extends RecyclerView.ViewHolder
    {

        // description of todo_ content
        private TextView content;

        //Time added to the todo_
        private TextView dateAdded;

        //linearLayout for the change dynamically the background
        private LinearLayout linearLayout;


        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            content =  itemView.findViewById(R.id.item_list_title);
            dateAdded = itemView.findViewById(R.id.date_added);
            linearLayout = itemView.findViewById(R.id.item_single_list_linearLayout);

        }
    }


}
