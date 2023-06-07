package com.metain.web.config;

import com.metain.web.dto.VacationListDTO;
import com.metain.web.service.VacationService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

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
