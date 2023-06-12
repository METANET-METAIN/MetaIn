package com.metain.web.mapper;

import com.metain.web.domain.Notification;
import com.metain.web.domain.Vacation;
import com.metain.web.dto.AlarmDTO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Mapper
public interface AlarmMapper {
    List<Notification> alarmListAll(Long empId);

    int insertAlarm(AlarmDTO alarmDTO);
}

