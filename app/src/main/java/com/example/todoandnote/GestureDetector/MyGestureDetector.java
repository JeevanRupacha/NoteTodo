package com.example.todoandnote.GestureDetector;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class MyGestureDetector extends GestureDetector.SimpleOnGestureListener  {
    @Override
    public void onLongPress(MotionEvent e) {
        super.onLongPress(e);
//        Toast.makeText(, "", Toast.LENGTH_SHORT).show();
    }
}
