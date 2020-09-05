package com.example.todoandnote.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {NoteTodo.class},
        version = FeedReaderContract.FeedEntry.DATABASE_VERSION)
public abstract class NoteTodoRoomDatabase extends RoomDatabase {
    public Object LOCK = new Object();

    public abstract NoteTodoDao noteTodoDao();

    private static NoteTodoRoomDatabase INSTANCE;

    public NoteTodoRoomDatabase getInstance(final Context context) {
        if (INSTANCE != null) {
            synchronized (LOCK) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        NoteTodoRoomDatabase.class,
                        FeedReaderContract.FeedEntry.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return INSTANCE;
    }

}
