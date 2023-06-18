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

//    List<MyCertDTO> selectIssueAll();

    List<MyVacDTO> selectMyVacList(MyVacDTO myVacDTO);
    List<MyVacDTO> myVacList(Long empId);

    //재직증명서 발급 현황
    List<EmpCert> selectMyEmpCert(EmpCert empCert);

    //경력증명서 발급 현황
    List<ExperienceCert> selectMyExperCert(ExperienceCert experienceCert);

    //경력증명서 발급 현황
    List<RetireCert> selectMyRetCert(RetireCert retireCert);
    //알림함
    List<AlarmDTO> alarmList(Long empId);


    void updateMy(Emp dbemp, MultipartFile file) throws IOException;
}
