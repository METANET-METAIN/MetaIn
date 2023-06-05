package com.metain.web.service;

import com.metain.web.domain.Vacation;
import com.metain.web.dto.VacationListDTO;

import java.util.List;

public interface VacationService {
    /*휴가 전체조회
    * */
    List<VacationListDTO> selectAllList();

    Vacation vacationDetail(Long vacationId);

    List<VacationListDTO> requestList();

    public void approveVacationRequest(Long vacId);
    public void rejectVacationRequest(Long vacId);
    public void cancelVacationRequest(Long vacId, Long empId,String vacStatus);

    public void insertVacation(Vacation vacation);
    public void insertAfterVacation(Vacation vacation);
}
