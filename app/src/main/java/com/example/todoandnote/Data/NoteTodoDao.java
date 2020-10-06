package com.example.todoandnote.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteTodoDao {

    @Insert
    void insertData(NoteTodo data);

    @Query("DELETE FROM " + FeedReaderContract.FeedEntry.TABLE_NAME)
    void deleteAllData();

    @Query("DELETE FROM " + FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE NoteTodoId =:id" )
    void deleteOne(int id);

    @Query("DELETE FROM " + FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE DataType =:dataType" )
    void delete(String dataType);

//    @Query("UPDATE "+ FeedReaderContract.FeedEntry.TABLE_NAME + " SET Content =:content, priority =:priority,schedulerTimeBool = :isScheduler WHERE NoteTodoId =:id")
    @Update
    void updateData(NoteTodo noteTodo);
//    void updateData(String content,boolean isScheduler,int priority, int id);

    @Query("UPDATE "+ FeedReaderContract.FeedEntry.TABLE_NAME + " SET DataType =:dataType WHERE NoteTodoId =:id")
    void changeDataType(String dataType , int id);

    @Query("SELECT * FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE DataType= 'todo' ORDER BY priority")
    LiveData<List<NoteTodo>> getTodo();

    @Query("SELECT * FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE DataType ='todo_done' ORDER BY Time_added DESC")
    LiveData<List<NoteTodo>> getTodoDone();

    @Query("SELECT * FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE DataType ='todo_undone' ORDER BY Time_added DESC")
    LiveData<List<NoteTodo>> getTodoUndone();

    @Query("SELECT * FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE DataType = 'todo_history' ORDER BY Time_added DESC")
    LiveData<List<NoteTodo>> getYesterdayHistory();

    @Query("SELECT * FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE DataType ='notodo' ORDER BY Time_added DESC")
    LiveData<List<NoteTodo>> getNoTodo();

    @Query("SELECT * FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE DataType ='notodo_done' ORDER BY Time_added DESC")
    LiveData<List<NoteTodo>> getNoTodoDone();

    @Query("SELECT * FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE DataType ='notodo_indone' ORDER BY Time_added DESC")
    LiveData<List<NoteTodo>> getNoTodoUndone();

    @Query("SELECT * FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME + " ORDER BY Time_added DESC")
    LiveData<List<NoteTodo>> getAllData();

}
