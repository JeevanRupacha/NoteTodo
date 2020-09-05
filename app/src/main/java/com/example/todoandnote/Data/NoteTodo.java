package com.example.todoandnote.Data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(tableName = FeedReaderContract.FeedEntry.TABLE_NAME)
public class NoteTodo {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name ="NoteTodoId")
    private int id;
    @ColumnInfo(name="Content")
    private String content;
    @ColumnInfo(name="Time Added")
    private Timestamp addedTime;
    @ColumnInfo(name="DataType")
    private String dataType;
}
