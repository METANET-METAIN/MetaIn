package com.metain.web.service;

import com.metain.web.domain.Emp;
import com.metain.web.domain.Notification;
import com.metain.web.domain.Vacation;
import com.metain.web.dto.AlarmDTO;
import com.metain.web.dto.VacationFileDTO;
import com.metain.web.dto.VacationListDTO;
import com.metain.web.dto.VacationWithoutFileDTO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

public interface VacationService {
    /*휴가 전체조회
    * */
    List<VacationListDTO> selectAllList();

    VacationFileDTO vacationDetail(Long vacationId);

    List<VacationListDTO> requestList();

    public void approveVacationRequest(Long vacId,String vacStatus,Long receiver);
    public void rejectVacationRequest(Long vacId,String vacStatus,Long receiver);
    public void cancelVacationRequest(Long vacId, Long empId,String vacStatus);

    public void insertVacation(Vacation vacation,int diffDays,Long empId);
    public void insertAfterVacation(Vacation vacation);
    public List<VacationListDTO> selectListByDept(String empDept, LocalDate today);
    public List<VacationListDTO> calendar(String empDept,LocalDate today);

    public int decreaseVacation(int selectedDays,Long empId);

    int annualUpdate(Emp empInfo);
    List<AlarmDTO> alarmListAll(Long empId);

    List<VacationListDTO> todayVacation(String empDept);

    public int increaseVacation(int selectedDays,Long empId);

    Vacation vacationDetailWithoutFile(Long vacationId);
}
