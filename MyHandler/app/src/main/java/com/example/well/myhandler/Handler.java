package com.example.well.myhandler;

/**
 * Created by Well on 2016/12/6.
 */

public class Handler {

    private MessageQueue mQueue;
    private final Looper mLooper;

    public Handler() {
        mLooper = Looper.myLooper();
        mQueue = mLooper.messageQueue;
    }

    public void sendMasage(Message message) {
        message.target = this;
        mQueue.enqueueMessage(message);
    }

    public void dispathMessage(Message message) {
        handleMessage(message);
    }

    public void handleMessage(Message message) {

    }
}
