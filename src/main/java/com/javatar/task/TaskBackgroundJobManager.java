package com.javatar.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(eager=true)
@ApplicationScoped
public class TaskBackgroundJobManager {

    private ScheduledExecutorService scheduler; 

    @PostConstruct
    public void init() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new TaskResponseQueueConsumer(), 0, 1000, TimeUnit.MILLISECONDS);
    }

    @PreDestroy
    public void destroy() {
        scheduler.shutdownNow();
    }

}