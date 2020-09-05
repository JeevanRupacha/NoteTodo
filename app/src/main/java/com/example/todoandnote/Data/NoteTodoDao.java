package com.example.todoandnote.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteTodoDao {

    @Insert
    void insertData(NoteTodo data);

    @Query("SELECT * FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME)
    LiveData<List<NoteTodo>> getAllData();

    @Query("SELECT * FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE NoteTodoId =:id")
    NoteTodo getOneData(int id);

    @Query("DELETE FROM " + FeedReaderContract.FeedEntry.TABLE_NAME)
    void deleteAllData();

    @Query("DELETE FROM " + FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE NoteTodoId =:id" )
    void deleteOne(int id);

    @Query("UPDATE "+ FeedReaderContract.FeedEntry.TABLE_NAME + " SET DataType =:dataType WHERE NoteTodoId =:id")
    void changeDataType(String dataType , int id);

    @Query("SELECT * FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE DataType =" + FeedReaderContract.FeedEntry.TODO)
    LiveData<List<NoteTodo>> getTodo();

    @Query("SELECT * FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE DataType =" + FeedReaderContract.FeedEntry.TODO_DONE)
    LiveData<List<NoteTodo>> getTodoDone();

    @Query("SELECT * FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE DataType =" + FeedReaderContract.FeedEntry.TODO_UNDONE)
    LiveData<List<NoteTodo>> getTodoUndone();

    @Query("SELECT * FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE DataType =" + FeedReaderContract.FeedEntry.TODO_HISTORY)
    LiveData<List<NoteTodo>> getYesterdayHistory();

    @Query("SELECT * FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE DataType =" + FeedReaderContract.FeedEntry.NOTODO)
    LiveData<List<NoteTodo>> getNoTodo();

    @Query("SELECT * FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE DataType =" + FeedReaderContract.FeedEntry.NOTODO_DONE)
    LiveData<List<NoteTodo>> getNoTodoDone();

    @Query("SELECT * FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE DataType =" + FeedReaderContract.FeedEntry.NOTODO_UNDONE)
    LiveData<List<NoteTodo>> getNoTodoUndone();

}
