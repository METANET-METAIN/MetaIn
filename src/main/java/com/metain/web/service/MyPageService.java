package com.metain.web.service;

import com.metain.web.domain.Emp;
import com.metain.web.domain.EmpCert;
import com.metain.web.domain.ExperienceCert;
import com.metain.web.domain.RetireCert;
import com.metain.web.dto.AlarmDTO;
import com.metain.web.dto.MyCertDTO;
import com.metain.web.dto.MyVacDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MyPageService {

    List<MyVacDTO> myVacList(Long empId);

    //재직증명서 발급 현황
    List<EmpCert> selectMyEmpCert(Long empId);

    //경력증명서 발급 현황
    List<ExperienceCert> selectMyExperCert(Long empId);

    //경력증명서 발급 현황
    List<RetireCert> selectMyRetCert(Long empId);

    //다운로드할 증명서 파일이름가져오기
    public String getCertFilename(Long certId, String certSort);

    //알림함
    List<AlarmDTO> alarmList(Long empId);


    void updateMy(Emp dbemp, MultipartFile empProfile) throws IOException;

    void updatePwd(Emp dbemp);

    void updateIssueStatus(Long certId, String certSort);
}
