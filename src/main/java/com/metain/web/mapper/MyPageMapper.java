package com.metain.web.mapper;

import com.metain.web.domain.Emp;
import com.metain.web.domain.Notification;
import com.metain.web.domain.Vacation;
import com.metain.web.dto.MyCertListDTO;
import com.metain.web.dto.MyVacDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageMapper {

    /**개인정보 수정*/
    public int updateMyPage(Emp emp);

    /**휴가 신청 현황 조회*/
    public List<MyVacDTO> selectMyVacList(MyVacDTO myVacDTO);

    /**휴가 신청 현황 상세 조회*/
    public Vacation myVacDetail(Long vacId);

    /**휴가 신청 수정*/
    public List<MyVacDTO> updateMyVac(MyVacDTO myVacDTO);


    /**증명서 발급 내역 조회*/

    public List<MyCertListDTO> selectIssueAll();

//    /**증명서 다운로드*/ 보류
//    public int certDownload(Long issueId);

    /**알람 리스트 확인*/
    public List<Notification> selectListNoti(Long notiId);




}
