package com.kaishengit;

import com.kaishengit.crm.jobs.MyTimeTask;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;

public class TimeTaskTest {

    @Test
    public void timeTest() throws IOException {
        Timer timer = new Timer();
        //当前任务延迟0秒，每隔2000毫秒执行一次
        //timer.schedule(new MyTimeTask(),0,2000);
        //当前任务延迟两秒钟，不重复
        //timer.schedule(new MyTimeTask(),2000);
        //在指定的时间去执行，不重复
        //timer.schedule(new MyTimeTask(),new Date());
        //从指定的时间开始，每隔2000毫秒执行一次
        timer.schedule(new MyTimeTask(),new Date(),2000);
        System.in.read();
    }

}
