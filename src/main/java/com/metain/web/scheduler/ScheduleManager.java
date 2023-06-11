package com.metain.web.scheduler;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.EventListener;

@Component
public class ScheduleManager implements EventListener {
    // quartz
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @PostConstruct
    public void start() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.start();

        // job 지정
        JobDetail job = JobBuilder.newJob(EventScheduler.class).withIdentity("testJob").build();

        // trigger 지정
        Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0 0 * ? * *")).build();

        scheduler.scheduleJob(job, trigger);
    }
}