package com.metain.web.service;

import com.metain.web.domain.File;
import com.metain.web.domain.Vacation;
import com.metain.web.dto.FileDTO;
import com.metain.web.dto.VacationListDTO;
import com.metain.web.mapper.FileMapper;
import com.metain.web.mapper.VacationMapper;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
@Service
@Transactional
public class VacationServiceImpl implements VacationService{
    @Autowired
    private VacationMapper vacMapper;
    @Autowired
    private FileMapper fileMapper;

    @Override
    public List<VacationListDTO> selectAllList() {
        List<VacationListDTO> list = vacMapper.selectAllList();
        if (list==null) {
            return null;
        }
            return list;
    }

    @Override
    public Vacation vacationDetail(Long vacationId) {
        Vacation dbVacation=vacMapper.vacDetail(vacationId);
        if (dbVacation==null){
            return null;
        }
        return dbVacation;}

    @Override
    public List<VacationListDTO> requestList() {
        List<VacationListDTO> list = vacMapper.requestList();
        if (list==null) {
            return null;
        }
        return list;
    }

    @Override
    public void approveVacationRequest(Long vacId,String vacStatus) {
        if(vacStatus.equals("승인대기")) {
            int result = vacMapper.approveVacationRequest(vacId, vacStatus);
            if (result == 0) {
                new Exception("에러");
            }
        }
    }
    @Override
    public void rejectVacationRequest(Long vacId,String vacStatus) {
        if(vacStatus.equals("승인대기")) {
            int result = vacMapper.rejectVacationRequest(vacId, vacStatus);
            if (result == 0) {
                new Exception("에러");
            }
        }
    }

    @Override
    public void cancelVacationRequest(Long vacId, Long empId,String vacStatus) {
        if(vacStatus.equals("승인대기")) {
            int result = vacMapper.cancelVacationRequest(vacId, empId);
        }
    }

    @Override
    public void insertVacation(Vacation vacation) {
        vacMapper.requestVacation(vacation);
    }

    @Override
    public void insertAfterVacation(Vacation vacation) {
        String fn = vacation.getFileName();
        // 파일 정보 삽입
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileName(fn);
        fileDTO.setEmpId(5L);
        fileMapper.insertFile(fileDTO);
        //인서트 되면 AUTOINCREAMENT 값 가져 와서 VAC에 넣기 
        int fileId = fileMapper.getFileId();
        vacation.setFileId((long) fileId);
        //그로 VAC INSERT 시킨당
        vacMapper.insertAfterVacation(vacation);
    }

    @Override
    public List<VacationListDTO> selectListByDept(String empDept, LocalDate today) {
        today = LocalDate.now();
        List<VacationListDTO> currMonthVac=vacMapper.selectListByDept(empDept,today);
        System.out.println("서비스에서 이번달 휴가목록"+currMonthVac);
        return currMonthVac;
    }

    @Override
    public int decreaseVacation(int selectedDays) {

        return 0;
    }

}
