package com.example.todoandnote.Data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.todoandnote.TypeConverter.DateConverter;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity(tableName = FeedReaderContract.FeedEntry.TABLE_NAME,
        indices = {@Index(value = {"NoteTodoId"},unique = true)})

public class NoteTodo
{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name ="NoteTodoId")
    protected int id;
    @ColumnInfo(name="Content")
    protected String content;
    @ColumnInfo(name="Time_added")
    protected Timestamp addedTime;
    @ColumnInfo(name="DataType")
    protected String dataType;
    @ColumnInfo(name="priority")
    protected int priority;
    @ColumnInfo(name="schedulerTime")
    protected Long schedulerTimeDate;
    @ColumnInfo(name= "schedulerTimeBool")
    protected boolean schedulerTimeBool;


    public NoteTodo(){}

    @Ignore
    public NoteTodo(int id, String content, Timestamp addedTime, String dataType)
    {
        this.id = id;
        this.content = content;
        this.addedTime = addedTime;
        this.dataType = dataType;
    }

//    @Ignore
//    public NoteTodo( String content, Timestamp addedTime, String dataType)
//    {
//        this.content = content;
//        this.addedTime = addedTime;
//        this.dataType = dataType;
//    }


    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getAddedTime() {
        return addedTime;
    }

    public String getDataType() {
        return dataType;
    }

    public int getPriority() {
        return priority;
    }

    public Long getSchedulerTimeDate() {
        return schedulerTimeDate;
    }

    public boolean getSchedulerTimeBool()
    {
        return schedulerTimeBool;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setSchedulerTimeDate(Long schedulerTimeDate) {
        this.schedulerTimeDate = schedulerTimeDate;
    }

    public void setSchedulerTimeBool(boolean schedulerTimeBool) {
        this.schedulerTimeBool = schedulerTimeBool;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAddedTime(Timestamp addedTime) {
        this.addedTime = addedTime;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
