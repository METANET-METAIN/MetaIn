package com.metain.web.mapper;

import com.metain.web.domain.Vacation;
import com.metain.web.dto.VacationListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface VacationMapper {
    /**
     * 휴가 신청--0
     * */
    public int requestVacation(Vacation vacation);
    /**
     * 휴가 신청(첨부파일
     * */
    public int insertAfterVacation(Vacation vacation);
    /**
     * 부서별 휴가 조회 - 메인 캘린더 표출, 필터
     * */
    public List<Vacation> selectListByDept(String empDept);
    /**
     * 기간별 휴가 조회
     * */
    public List<Vacation> selectListByPeriod();
    /**
     * 휴가 전체 조회--0
     */
    public List<VacationListDTO> selectAllList();

    /**
     * 휴가 승인-관리자--0 //시큐리티 이후 관리자 번호도 넣어야됨
     * */
    public int approveVacationRequest(Long vacId);
    /**
     * 휴가 거절-관리자 --0 //시큐리티 이후 관리자 번호도 넣어야됨
     * */
    public int rejectVacationRequest(Long vacId);
    /**
     * 휴가 취소 - user --0
     * */
    public int cancelVacationRequest(Long vacId, Long empId);
    /**
     * 휴가 알림
     * */
    public void alarmVacation(String vacStatus);
    /**
     * 휴가 디테일--0
     * */
    public Vacation vacDetail(Long vacId);
    /**
     * 연차별로 휴가(1일) 부여하는 기능 +연차 차감 이런것도 해야댐
     * */
    public int vacationCount(Date empFistDt);
    /**
    * 요청된 휴가 목록--0
    * */
    public List<VacationListDTO> requestList();

}
