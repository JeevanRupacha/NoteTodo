package com.example.todoandnote.Data;

import android.os.AsyncTask;

public class changeDataTypeAsyncTask extends AsyncTask<NoteTodo,Void,Void> {
    private NoteTodoDao noteTodoDao;
    public changeDataTypeAsyncTask(NoteTodoDao noteTodoDao) {
        this.noteTodoDao = noteTodoDao;
    }

    @Override
    protected Void doInBackground(NoteTodo... noteTodos) {
        noteTodoDao.changeDataType(noteTodos[0].dataType, noteTodos[0].getId());
        return null;
    }
}
