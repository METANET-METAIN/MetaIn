package com.metain.web.mapper;

import com.metain.web.domain.Notification;
import com.metain.web.domain.Vacation;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.Date;

@Mapper
public interface NotificationMapper {
    /**
     * 부서내 휴가사용자- sendNoti();
     * */
    public void notiBydept(String empDept, Date vacDate);

    /**
     * 휴가 승인됐을 경우 sendNoti();
     * */
    public void approveNoti(String vacStatus);
    /**
     * 휴가 거절됐을 경우 sendNoti();
     * */
    public void rejectNoti(String vacStatus);
    /**
     * 알림 발생--
     * */
    public void sendNoti(LocalDate vacStartDate);

    /**승진 등의 사유로 개인정보가 수정되었을 경우*/
    public void updateMydata(Long udpatedEmpId);

    /**
     * 휴가 요청이 발생 했을 경우- 관리자, sendNoti(); --인수 뭐줘 ?
     * */
    public void requestVactionNoti(Vacation vacation);
}

