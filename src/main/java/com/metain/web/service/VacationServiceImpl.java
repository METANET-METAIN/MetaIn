package com.metain.web.service;

import com.metain.web.domain.Vacation;
import com.metain.web.dto.VacationListDTO;
import com.metain.web.mapper.VacationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class VacationServiceImpl implements VacationService{
    @Autowired
    private VacationMapper vacMapper;
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
    public void approveVacationRequest(Long vacId) {
        int result =vacMapper.approveVacationRequest(vacId);

        if(result==0) {
            new Exception("에러");
        }
    }
    @Override
    public void rejectVacationRequest(Long vacId) {
        int result =vacMapper.rejectVacationRequest(vacId);
        if(result==0) {
            new Exception("에러");
        }
    }

    @Override
    public void cancelVacationRequest(Long vacId, Long empId,String vacStatus) {
        if(vacStatus.equals("승인대기")) {
            int result = vacMapper.cancelVacationRequest(vacId, empId);
        }
    }
}
