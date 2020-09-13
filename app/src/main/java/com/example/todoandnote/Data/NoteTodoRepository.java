package com.example.todoandnote.Data;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import java.sql.Struct;
import java.util.List;

public class NoteTodoRepository
{
    private static final String TAG = "NoteTodoRepository";
    private NoteTodoDao noteTodoDao;
    private LiveData<List<NoteTodo>> allNoteTodoData;

   public NoteTodoRepository(Application application)
   {
       NoteTodoRoomDatabase db = NoteTodoRoomDatabase.getInstance(application);
       noteTodoDao = db.noteTodoDao();
       allNoteTodoData = noteTodoDao.getAllData();
   }


   public void insetData(NoteTodo noteTodo)
   {
        new NoteTodoAsyncTask<NoteTodo>(noteTodoDao, "insert").execute(noteTodo);
   }

    public void deleteAllData()
    {
        new NoteTodoAsyncTask<Void>(noteTodoDao, "deleteAllData").execute();
    }

    public void deleteOne(int id)
    {
        new NoteTodoAsyncTask<Integer>(noteTodoDao, "deleteOne").execute(id);
    }

    public void deleteByDatatype(String dataType)
    {
        new NoteTodoAsyncTask<String>(noteTodoDao, "deleteByDatatype").execute(dataType);
    }

    public void updateData(String content, int id)
    {
        String strId = String.valueOf(id);
        new NoteTodoAsyncTask<String>(noteTodoDao, "updateData").execute(content,strId);
    }

    public void changeDataType(String dataType , int id)
    {
        Log.d(TAG, "changeDataType: id" + id);
        String strId = String.valueOf(id);
        Log.d(TAG, "changeDataType: strId" + strId);

        new NoteTodoAsyncTask<String>(noteTodoDao, "changeDataType").execute(dataType,strId);
    }

    public LiveData<List<NoteTodo>> getTodo()
    {return noteTodoDao.getTodo();}

    public LiveData<List<NoteTodo>> getTodoDone()
    {return noteTodoDao.getTodoDone();}

    public LiveData<List<NoteTodo>> getTodoUndone()
    {return noteTodoDao.getTodoUndone();}

    public LiveData<List<NoteTodo>> getYesterdayHistory()
    {return noteTodoDao.getYesterdayHistory();}

    public LiveData<List<NoteTodo>> getNoTodo()
    {return noteTodoDao.getNoTodo();}

    public LiveData<List<NoteTodo>> getNoTodoDone()
    {return noteTodoDao.getNoTodoDone();}

    public LiveData<List<NoteTodo>> getNoTodoUndone()
    {return noteTodoDao.getNoTodoUndone();}

    public LiveData<List<NoteTodo>> getAllData()
    {return noteTodoDao.getAllData();}

   //TODO implements all the CRUD functions
    // TODO Create async task for all the function and do in background


    //creating assynchronous class because room doesnt allow to load data
    //it may crash
    public static class NoteTodoAsyncTask<T> extends AsyncTask<T,Void,Void>
    {
        private NoteTodoDao noteTodoDao;
        private String action;

        public NoteTodoAsyncTask(NoteTodoDao noteTodoDao, String action) {
            this.noteTodoDao = noteTodoDao;
            this.action = action;
        }

        @Override
        protected Void doInBackground(T... ts) {
            switch (action)
            {
                case "insert" : noteTodoDao.insertData((NoteTodo)ts[0]);
                        break;
                case "deleteAllData": noteTodoDao.deleteAllData();
                    break;
                case "deleteOne": noteTodoDao.deleteOne(Integer.parseInt((String) ts[0]));
                    break;
                case "deleteByDatatype": noteTodoDao.delete((String)ts[0]);
                    break;
                case "updateData": noteTodoDao.updateData((String) ts[0], Integer.valueOf((Integer) ts[1]));
                    break;
                case "changeDataType":
                    Log.d("Repo", "doInBackground: " + ts[0] +" "+ ts[1]);
                    noteTodoDao.changeDataType((String) ts[0], Integer.parseInt((String) ts[1]));
                    break;
                default:
                    try
                    {
                        throw new Exception("action selection fail");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
            return null;
        }
    }
}
