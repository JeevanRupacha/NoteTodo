package com.example.todoandnote.TodoWorkManager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.todoandnote.Activities.MainActivity;
import com.example.todoandnote.Utils.MainActivityUtils;

public class HideShowNavWorkManager extends Worker {

    public HideShowNavWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        return Result.success();
    }
}
