package com.kaishengit.crm.jobs;

import java.util.TimerTask;

public class MyTimeTask extends TimerTask {
    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run() {
        System.out.println("hello,TimeTask");
    }
}
