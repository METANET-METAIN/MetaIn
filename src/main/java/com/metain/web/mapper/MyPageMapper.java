package com.metain.web.mapper;

import com.metain.web.domain.*;
import com.metain.web.dto.AlarmDTO;
import com.metain.web.dto.MyCertDTO;
import com.metain.web.dto.MyVacDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageMapper {

    /**개인정보 수정*/
    public int updateMyPage(Emp dbemp);

    /**
     * 비밀번호 변경
     */
    public int updatePwd(Emp dbemp);

    /**휴가 신청 현황 조회*/
    public List<MyVacDTO> selectMyVacList(MyVacDTO myVacDTO);


    /**나의 재직 증명서 발급 내역 조회*/
    public List<EmpCert> selectMyEmpCert(Long empId);

    /**나의 경력 증명서 발급 내역 조회*/
    public List<ExperienceCert> selectMyExperCert(Long empId);

    /**나의 퇴직 증명서 발급 내역 조회*/
    public List<RetireCert> selectMyRetCert(Long empId);

    /** 증명서 파일 다운로드 */
    public  String selectEmpCertFilename(Long certId);
    public String selectExperCertFilename(Long certId);
    public String selectRetireCertFilename(Long certId);

    /** 증명서 다운로드 시 다운로드상태 업데이트 */
    public int updateEmpIssueStatus(Long certId);
    public int updateExperIssueStatus(Long certId);
    public int updateRetireIssueStatus(Long certId);

    List<MyVacDTO> myVacList(Long empId);

    List<AlarmDTO> alarmList(Long empId);
}
