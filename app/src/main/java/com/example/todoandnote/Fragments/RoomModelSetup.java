package com.example.todoandnote.Fragments;

/**
This interface is for code reduce as all the fragment has these three function common
 So all the fragment should implemetns these three function
 */

public interface RoomModelSetup {
     void observerSetup(String dataType);
    void listenerSetup();
    void recyclerSetup();
}
