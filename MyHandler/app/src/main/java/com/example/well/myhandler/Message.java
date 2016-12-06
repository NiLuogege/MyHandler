package com.example.well.myhandler;

/**
 * Created by Well on 2016/12/6.
 */

public class Message {
    public Handler target;
    public int what;
    public Object obj;

    @Override
    public String toString() {
        return " obj="+obj.toString();
    }
}
