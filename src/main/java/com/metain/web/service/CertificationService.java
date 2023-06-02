package com.metain.web.service;

import com.metain.web.domain.*;
import com.metain.web.dto.VacationListDTO;

import java.util.List;

public interface CertificationService {

    //필요한 emp 정보 list로 가져오기
    public List<Emp> getEmpInfoList(Long empId);


    //증명서 발급신청
    public int addCertIssue(Issue issue);

    //재직증명서 신청
    public int applyEmpCert(EmpCert empCert);

//    //경력증명서 신청
//    public int applyExperCert(ExperienceCert experienceCert);
//
//    //퇴직증명서 신청
//    public int applyRetireCert(RetireCert retireCert);


    //증명서 생성할때 필요한 증명서발급정보 list로 가져오기
    public List<EmpCert> getEmpCertList(Long empId);

//    public List<ExperienceCert> getExperCertList(Long empId);
//
//    public List<RetireCert> getRetireCertList(Long empId);





}
