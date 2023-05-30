package com.metain.web.mapper;

import com.metain.web.domain.Vacation;
import com.metain.web.dto.VacationListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface VacationMapper {
    /**
     * 휴가 신청
     * */
    public void requestVacation();
    /**
     * 부서별 휴가 조회 - 메인 캘린더 표출, 필터
     * */
    public List<Vacation> selectListByDept(String empDept);
    /**
     * 기간별 휴가 조회
     * */
    public List<Vacation> selectListByPeriod();
    /**
     * 휴가 전체 조회
     */
    public List<VacationListDTO> selectAllList();
    /**
     * 나의 휴가 조회 ----->마이페이지로 가져가><
     * */
    public List<Vacation> selectMyList(Long empId);
    /**
     * 휴가 승인-관리자
     * */
    public void approveVacationRequest(String vacStatus);
    /**
     * 휴가 거절-관리자
     * */
    public void rejectVacationRequest(String vacStatus);
    /**
     * 휴가 취소 - user
     * */
    public void cancelVacationRequest(String vacStatus);
    /**
     * 휴가 알림
     * */
    public void alarmVacation(String vacStatus);
    /**
     * 휴가 디테일
     * */
    public Vacation vacDetail(Long vacId);
    /**
     * 연차별로 휴가(1일) 부여하는 기능
     * */
    public int vacationCount(Date empFistDt);
}
