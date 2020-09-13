package com.example.todoandnote.Ui.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoandnote.Activities.AddNewTodoActivity;
import com.example.todoandnote.Data.NoteTodo;
import com.example.todoandnote.Fragments.FirstFragment;
import com.example.todoandnote.R;

import java.sql.Date;
import java.util.List;

public class TodoRecyclerViewAdapter extends
        RecyclerView.Adapter<TodoRecyclerViewAdapter.TodoViewHolder>
    implements AdapterView.OnItemClickListener
{
    private static final String TAG = "RecyclerView Adapter";
    private static Context context;
    private List<NoteTodo> data;

    public TodoRecyclerViewAdapter(Context context)
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
                .inflate(R.layout.item_single_list, parent, false);

        return new TodoViewHolder(listView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {

        //noteTodo object initialize
        NoteTodo noteTodo = data.get(position);


        Log.d(TAG, "onBindViewHolder: test time  " + new Date(noteTodo.getAddedTime().getTime()));


        //priority for task
        int priority = noteTodo.getPriority();

        //scheduler timer
        if(noteTodo.getSchedulerTimeBool())
        {
            holder.timerNotiImage.setVisibility(View.VISIBLE);
        }


        holder.priority.setText(String.valueOf(priority));
        GradientDrawable priorityCircle  = (GradientDrawable) holder.priority.getBackground();
        priorityCircle.setColor(getPriorityColor(priority));

        holder.content.setText(noteTodo.getContent());

        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(noteTodo
                .getAddedTime().getTime());

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context, "item click", Toast.LENGTH_SHORT).show();
    }


    public static class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        // description of todo_ content
        private TextView content;

        //Time added to the todo_
        private TextView dateAdded;

        //task priority
        private TextView priority;

        //time notification scheduler imageView
        private ImageView timerNotiImage;

        //list container layout
        private LinearLayout linearLayout;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            content =  itemView.findViewById(R.id.item_list_title);
            dateAdded = itemView.findViewById(R.id.date_added);
            priority = itemView.findViewById(R.id.priorityTextView);
            timerNotiImage = itemView.findViewById(R.id.timer_scheduler_icon);
            linearLayout = itemView.findViewById(R.id.item_single_list_linearLayout);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            //update the data
//            new FirstFragment().updateData((int)v.getTag());
            Intent intent = new Intent(context, AddNewTodoActivity.class);
            intent.putExtra("id", (int)v.getTag());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
//            Log.d(TAG, "updateData: id is " + id);
        }
    }

    public int getPriorityColor(int priority)
    {

        int colorId = 0;

        switch (priority)
        {
            case 1:
                //get red color
                colorId = ContextCompat.getColor(context, R.color.materialRed);
                break;
            case 2:
                //get Orange color
                colorId = ContextCompat.getColor(context, R.color.materialOrange);
                break;
            case 3:
                //get grass color
                colorId = ContextCompat.getColor(context, R.color.materialGrass);
                break;
            default:break;
        }

        return colorId;
    }

}
