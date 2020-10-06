package com.example.todoandnote.GestureDetector;

import android.animation.LayoutTransition;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;


import com.example.todoandnote.Fragments.FirstFragment;
import com.example.todoandnote.Utils.MainActivityUtils;

public class GestureListener implements GestureDetector.OnGestureListener {

    private static final String TAG = "GestureListener Debug";
    private Context context;


    public GestureListener(Context context)
    {
        this.context = context;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }


    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        if(e1 != null && e2 != null)
        {

            if (e2.getY() > e1.getY())
            {
                //down direction
                MainActivityUtils.showNavButtonView();
                MainActivityUtils.showFab();
//                MainActivityUtils.showAppBarLayout();
//                    Toast.makeText(getContext(), "down ", Toast.LENGTH_SHORT).show();
            }else{
                MainActivityUtils.hideNavButtonView();
                MainActivityUtils.hideFab();
//                MainActivityUtils.hideAppBarLayout();
            }
        }
//        WorkRequest request = new OneTimeWorkRequest.Builder(HideShowNavWorkManager.class).build();
//        WorkManager.getInstance(context).enqueue(request);
//        FloatingActionButton fab = (FloatingActionButton) find
//        new MainActivity().hideShowFab();
//        mainActivity.hideShowFab();
//        mainActivity.hideShowNavButtonView();

//        if(e1 == null || e2 == null)
//        {
//            return false;
//        }
//
//        Log.d(TAG,"e1Y = " +e1.getY() + " e2Y = " + e2.getY() +" e1X = " +e1.getX() + " e2X = " + e2.getX()+" velx = " + velocityX + " vely = "+ velocityY);
//
//        if (e2.getY() > e1.getY()) {
//            // direction up
//            MainActivityUtils.showNavButtonView();
//            MainActivityUtils.hideFab();
//
//
//        }else if(e2.getY() < e1.getY()) {
//            // direction down
//            MainActivityUtils.hideNavButtonView();
//            MainActivityUtils.showFab();
//
//        }
//
//        if (e2.getX() > e1.getX()) {
//            // direction right
//        }else {
//            // direction left
//        }


        return true;
    }

}