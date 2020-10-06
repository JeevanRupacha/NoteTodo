package com.example.todoandnote.Data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;

public class NoteTodoViewModel extends AndroidViewModel {
    private NoteTodoRepository noteTodoRepository;

    public NoteTodoViewModel(@NonNull Application application) {
        super(application);
        noteTodoRepository = new NoteTodoRepository(application);
    }


    public void insetData(NoteTodo noteTodo)
    {
        noteTodoRepository.insetData(noteTodo);
    }

    public void deleteAllData()
    {
        noteTodoRepository.deleteAllData();
    }

    public void deleteOne(int id)
    {
        noteTodoRepository.deleteOne(id);
    }

    public void deleteByDatatype(String dataType)
    {
        noteTodoRepository.deleteByDatatype(dataType);
    }

//    public void updateData(String content,boolean isScheduler,int priority, int id)
//    {
//        noteTodoRepository.updateData(content,isScheduler,priority, id);
//    }

    public void updateData(NoteTodo noteTodo)
    {
        noteTodoRepository.updateData(noteTodo);
    }

    public void changeDataType(String dataType , int id)
    {
        noteTodoRepository.changeDataType(dataType,id);
    }

    public LiveData<List<NoteTodo>> getTodo()
    {
        return noteTodoRepository.getTodo();
    }

    public LiveData<List<NoteTodo>> getTodoDone()
    {
        return noteTodoRepository.getTodoDone();
    }

    public LiveData<List<NoteTodo>> getTodoUndone()
    {
        return noteTodoRepository.getTodoUndone();
    }
    public LiveData<List<NoteTodo>> getYesterdayHistory()
    {
        return noteTodoRepository.getYesterdayHistory();
    }
    public LiveData<List<NoteTodo>> getNoTodo()
    {
        return noteTodoRepository.getNoTodo();
    }
    public LiveData<List<NoteTodo>> getNoTodoDone()
    {
        return noteTodoRepository.getNoTodoDone();
    }
    public LiveData<List<NoteTodo>> getNoTodoUndone()
    {
        return noteTodoRepository.getNoTodoUndone();
    }
    public LiveData<List<NoteTodo>> getAllData()
    {
        return noteTodoRepository.getAllData();
    }
}
