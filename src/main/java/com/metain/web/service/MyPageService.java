package com.metain.web.service;

import com.metain.web.domain.EmpCert;
import com.metain.web.dto.MyCertDTO;
import com.metain.web.dto.MyVacDTO;

import java.util.List;

public interface MyPageService {

//    List<MyCertDTO> selectIssueAll();

    List<MyVacDTO> selectMyVacList(MyVacDTO myVacDTO);
    List<MyVacDTO> myVacList(Long empId);

    //재직증명서 발급 현황
    List<EmpCert> selectMyEmpCert(EmpCert empCert);
}
