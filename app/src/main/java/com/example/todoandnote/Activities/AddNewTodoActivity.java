package com.example.todoandnote.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todoandnote.Data.NoteTodo;
import com.example.todoandnote.DateTime.DateTime;
import com.example.todoandnote.Fragments.FirstFragment;
import com.example.todoandnote.PrefereceUtil.PreferenceData;
import com.example.todoandnote.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddNewTodoActivity extends AppCompatActivity
{

    //log out for debug
    private static final String TAG = "AddNewActivity";

    //the button which saves the all todo_ or update
    private Button saveButton;

    //todo_ user input text
    private TextView inputText;

    //switcher for scheduler notification
    private SwitchMaterial schedulerSwitch;

    //date and time picker for scheduler notification
    private static TextView dateTextView, timeTextView;

    //todo_ priority
    private int mPriority;

    private static Calendar cal;

    //default date for showing into textView
    public static final String DEFAULT_DATE = DateTime.formatDate(System.currentTimeMillis());

    //formatting the time into and default time for showing into textView
    static SimpleDateFormat df = new SimpleDateFormat("HH:mm aa");
    static String formattedDate = df.format(System.currentTimeMillis());
    public static final String DEFAULT_TIME = DateTime.getTwelveHourFormatted(Calendar.getInstance()
            .get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE));


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_add_activity);

        Toolbar toolbar = findViewById(R.id.add_edit_noteTodo_toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.xxlightGray));
        setSupportActionBar(toolbar);



        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Add Section");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        //initializing the all views from edit_add_activity.xml
//        saveButton = (Button) findViewById(R.id.save_todo_button);
        inputText = (EditText) findViewById(R.id.input_text);
        schedulerSwitch = (SwitchMaterial) findViewById(R.id.scheduler_switch);
        dateTextView = (TextView) findViewById(R.id.date_show_textview);
        timeTextView = (TextView) findViewById(R.id.time_show_textview);

        //calender set for scheduler ...
        cal = Calendar.getInstance();

        //default priority and low check for radio button
        mPriority = 3;
        ((RadioButton) findViewById(R.id.radio1)).setChecked(true);

        //setting the default time and date for scheduler notification
        dateTextView.setText(DEFAULT_DATE);
        timeTextView.setText(DEFAULT_TIME);

//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(TextUtils.isEmpty(inputText.getText().toString()))
//                {
//                    Toast.makeText(AddNewTodoActivity.this, "Input text is Empty", Toast.LENGTH_SHORT).show();
//                }else {
//                    addSaveButton();
//                }
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_edit_todo_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.save_check_button:
                addSaveButton();
                break;
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpMenu() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        setPreviousValues();
    }

    private void setPreviousValues() {
        Bundle intent = getIntent().getExtras();
        int id;
        if(intent != null)
        {
            id = intent.getInt("id");
            List<NoteTodo> allNoteTodo = new ArrayList<>();
            allNoteTodo = FirstFragment.getAllTodo();
            for(NoteTodo noteTodo: allNoteTodo)
            {
                if(noteTodo.getId() == id){
                    inputText.setText(noteTodo.getContent());
                    setRadioButton(noteTodo.getPriority());

                    Log.d(TAG, "setPreviousValues: " + noteTodo.getSchedulerTimeDate());

                    if(noteTodo.getSchedulerTimeBool() && noteTodo.getSchedulerTimeDate() != null)
                    {
                    //TODO set the scheduler time getting from the shared preference
                        schedulerSwitch.setChecked(true);
                        DateTime dateTime = new DateTime(noteTodo.getSchedulerTimeDate());
                        dateTextView.setText(DateTime.formatDate(noteTodo.getSchedulerTimeDate()));
                        timeTextView.setText(DateTime.getTwelveHourFormatted(dateTime.getHour(), dateTime.getMinute()));
                    }

                    return;
                }
            }

        }
    }

    private void setRadioButton(int priority) {
        switch (priority)
        {
            case 1: ((RadioButton)findViewById(R.id.radio3)).setChecked(true);
            break;

            case 2: ((RadioButton)findViewById(R.id.radio2)).setChecked(true);
            break;

            case 3:((RadioButton)findViewById(R.id.radio1)).setChecked(true);
            break;

            default: break;
        }
    }

    public void showTimePickerDialog(View v) {
        if(schedulerSwitch.isChecked())
        {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
        }
    }

    public void showDatePickerDialog(View v) {
        if(schedulerSwitch.isChecked()) {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getSupportFragmentManager(), "datePicker");
        }
    }

    public void onPrioritySelected(View view) {
        if (((RadioButton) findViewById(R.id.radio1)).isChecked()) {
            mPriority = 3;
        } else if (((RadioButton) findViewById(R.id.radio2)).isChecked()) {
            mPriority = 2;
        } else if (((RadioButton) findViewById(R.id.radio3)).isChecked()) {
            mPriority = 1;
        }
    }

    public void addSaveButton()
    {
        String content = inputText.getText().toString();
        Timestamp time = new Timestamp(System.currentTimeMillis());
        NoteTodo noteTodo = new NoteTodo();

        noteTodo.setContent(content.trim());
        noteTodo.setAddedTime(time);
        noteTodo.setDataType("todo");
        noteTodo.setPriority(mPriority);


        if(schedulerSwitch.isChecked())
        {
            //TODO insert meaning ful key for scheduler time
            noteTodo.setSchedulerTimeBool(true);
            PreferenceData preferenceData = new PreferenceData(this);
            preferenceData.setSchedulerPreference("timerID", cal.getTimeInMillis());
//            long time = cal.getTimeInMillis();
//
//            Calendar cale = Calendar.getInstance();
//            cale.setTimeInMillis(time);
//
//            Log.d(TAG, "addSaveButton: " +cale.getTime());
        }

       if(!TextUtils.isEmpty(content))
       {
           FirstFragment.insertData(noteTodo);
           startActivity(new Intent(this, MainActivity.class));
       }else{
           Toast.makeText(this, "Empty content ", Toast.LENGTH_SHORT).show();
       }
    }


    public static class DatePickerFragment
            extends DialogFragment
            implements DatePickerDialog.OnDateSetListener
    {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
        {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            dateTextView.setText(DateTime.formatDate(cal.getTimeInMillis()));
        }
    }


    public static class TimePickerFragment
            extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener
    {

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(),this,hour, minute,
                    DateFormat.is24HourFormat(getActivity()));

        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal.set(Calendar.MINUTE, minute);
            timeTextView.setText(DateTime.getTwelveHourFormatted(hourOfDay, minute));
        }
    }




}