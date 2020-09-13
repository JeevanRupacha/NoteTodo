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
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoandnote.Data.NoteTodo;
import com.example.todoandnote.R;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TodoDoneRecyclerViewAdapter extends RecyclerView.Adapter<TodoDoneRecyclerViewAdapter.TodoViewHolder>
{
    private static final String TAG = "TodoDone RecyclerView Adapter";
    private Context context;

    //list of all the data of noteTodo
    private List<NoteTodo> data;

    //oneDay time for grouping in day date
    private String oneDay;

    //random background list
    private  int[] randomBackgroundList = new int[]{
            R.drawable.random_back_list1
            ,R.drawable.random_back_list2
            ,R.drawable.random_back_list3
            , R.drawable.random_back_list5
      };

    private int randomColorId = randomBackgroundList[randomBackgroundList.length -1];

    public TodoDoneRecyclerViewAdapter(Context context)
    {
        this.context = context;
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
        String strDate = date.toString();

        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(noteTodo
                .getAddedTime().getTime());

        assert oneDay != null;
        if(TextUtils.isEmpty(oneDay))
        {
           oneDay = strDate;
        }

        if(!oneDay.equals(strDate))
        {
           //create new group for one day
           //new date
            Random rnd = new Random();
            int randomNum = rnd.nextInt(randomBackgroundList.length);
            randomColorId = randomBackgroundList[randomNum];
            oneDay = strDate;

        }


        holder.linearLayout.setBackgroundResource(randomColorId);
        holder.content.setText(noteTodo.getContent());
        holder.dateAdded.setText(timeAgo);
        holder.itemView.setTag(noteTodo.getId());

    }

    @Override
    public int getItemCount() {
        if(data != null){
            return data.size();
        }

        return 0;
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
