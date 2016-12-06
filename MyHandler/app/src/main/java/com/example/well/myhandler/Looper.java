package com.example.well.myhandler;

/**
 * Created by Well on 2016/12/6.
 */

public final class Looper {

    private static ThreadLocal<Looper> mThreadLocal = new ThreadLocal<>();//保证线程数据隔离
    public static MessageQueue messageQueue;

    public Looper() {
        messageQueue = new MessageQueue();
    }

    public static void prepare() {
        Looper looper = mThreadLocal.get();
        if (null != looper) {
            throw new RuntimeException("one Thread only have one Looper!");
        } else {
            Looper loopre = new Looper();
            mThreadLocal.set(loopre);
        }

    }

    /**
     * 获取当前线程looper对象
     *
     * @return
     */
    public static Looper myLooper() {
        Looper looper = mThreadLocal.get();
        return looper;
    }

    public static MessageQueue getMessageQueue() {
        return messageQueue;
    }

    /**
     * 消息队列轮询
     */
    public static void loop() {
        Looper looper = myLooper();
        if (null == looper) {
            throw new RuntimeException("no Looper ,use prepare()");
        }
        MessageQueue messageQueue = looper.messageQueue;
        for (; ; ) {
            Message next = messageQueue.next();
            next.target.dispathMessage(next);
        }

    }
}
