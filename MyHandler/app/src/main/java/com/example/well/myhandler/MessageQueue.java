package com.example.well.myhandler;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Well on 2016/12/6.
 */

public class MessageQueue {

    private final Message[] mItem;
    private int putIndex = 0;
    private int tackIndex = 0;
    private int count = 0;

    private Lock mLock;
    private Condition notEmpty;
    private Condition notFull;

    public MessageQueue() {
        mItem = new Message[50];    //创造一个又上线的消息队列
        mLock = new ReentrantLock();
        notEmpty = mLock.newCondition();
        notFull = mLock.newCondition();
    }

    /**
     * 压入队列
     *
     * @param message
     */
    public void enqueueMessage(Message message) {
        try {
            mLock.lock();
            while (count == mItem.length) {
                try {
                    notEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mItem[putIndex] = message;
            putIndex = ++putIndex == mItem.length ? 0 : putIndex;
            count++;

            notFull.signal();

        } finally {
            mLock.unlock();
        }


    }

    /**
     * 退出队列
     */
    public Message next() {
        Message message = null;
        try {
            mLock.lock();
            while (count == 0) {
                try {
                    notFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//            Log.e("tag", "tackIndex=" + tackIndex + " putIndex=" + putIndex);

            message = mItem[tackIndex];
            mItem[tackIndex] = null;
            tackIndex = ++tackIndex == mItem.length ? 0 : tackIndex;
            count--;

            notEmpty.signal();

        } finally {
            mLock.unlock();
        }
        return message;
    }


}
