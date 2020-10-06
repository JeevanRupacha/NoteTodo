package com.example.todoandnote.Data;

import android.os.Build;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;

public final class FeedReaderContract {
    private FeedReaderContract(){}

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME ="NoteTodo_datatable";
        public static final String DATABASE_NAME ="NoteTodo_database";

        //DataType
        public static final String DATATYPE_TODO = "todo";
        public static final String DATATYPE_TODO_UNDONE = "todo_undone";
        public static final String DATATYPE_TODO_DONE = "todo_done";
        public static final String DATATYPE_TODO_TODO_HISTORY = "todo_history";
        public static final String DATATYPE_TODO_NOTODO = "notodo";
        public static final String DATATYPE_TODO_NOTODO_DONE = "notodo_done";
        public static final String DATATYPE_TODO_NOTODO_UNDONE = "notodo_undone";

        //database version
        public static final int DATABASE_VERSION = 6;



    }
}
