package com.metain.web.scheduler;

import com.metain.web.service.VacationService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventScheduler implements Job {

    private VacationService vacService;

    private static Logger logger = LoggerFactory.getLogger(EventScheduler.class);

    @Autowired
    public EventScheduler(VacationService vacService) {
        this.vacService = vacService;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //logger.info("::::::aaaaaaaaaaaa:::::");
       // LocalDate today = LocalDate.now();
        //String empDept="IT";
        //List<VacationListDTO> l = vacService.selectListByDept(empDept, today);
    }
}
