package com.example.todoandnote.Data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.todoandnote.TypeConverter.DateConverter;

import java.sql.Timestamp;


@Database(entities = {NoteTodo.class},
        version = FeedReaderContract.FeedEntry.DATABASE_VERSION,
exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class NoteTodoRoomDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static NoteTodoRoomDatabase INSTANCE;
    private static final String LOG_TAG = NoteTodoRoomDatabase.class.getSimpleName();
    public abstract NoteTodoDao noteTodoDao();

    public static NoteTodoRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "getInstance: creating new database instance ");
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        NoteTodoRoomDatabase.class,
                        FeedReaderContract.FeedEntry.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallBack)
                        .build();
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private NoteTodoDao noteTodoDao;

        public PopulateAsyncTask(NoteTodoRoomDatabase db)
        {
            noteTodoDao = db.noteTodoDao();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            int time = (int) (System.currentTimeMillis());
            Timestamp tsTemp = new Timestamp(time);
            noteTodoDao.insertData(new NoteTodo(1,"hello 2",tsTemp, FeedReaderContract.FeedEntry.DATATYPE_TODO_NOTODO_DONE));
            noteTodoDao.insertData(new NoteTodo(2,"hello 2",tsTemp, FeedReaderContract.FeedEntry.DATATYPE_TODO_NOTODO_DONE));
            noteTodoDao.insertData(new NoteTodo(3,"hello 2",tsTemp, FeedReaderContract.FeedEntry.DATATYPE_TODO_NOTODO_DONE));
            noteTodoDao.insertData(new NoteTodo(4,"hello 2",tsTemp, FeedReaderContract.FeedEntry.DATATYPE_TODO_NOTODO_DONE));
            noteTodoDao.insertData(new NoteTodo(5,"hello 2",tsTemp,"todo"));
            noteTodoDao.insertData(new NoteTodo(7,"hello id 7",tsTemp, FeedReaderContract.FeedEntry.DATATYPE_TODO));
            noteTodoDao.insertData(new NoteTodo(8,"hello id 8",tsTemp, FeedReaderContract.FeedEntry.DATATYPE_TODO));
            noteTodoDao.insertData(new NoteTodo(9,"hello id 9",tsTemp, FeedReaderContract.FeedEntry.DATATYPE_TODO));
            return null;
        }
    }

}
