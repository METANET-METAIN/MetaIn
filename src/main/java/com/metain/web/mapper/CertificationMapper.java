package com.metain.web.mapper;

import com.metain.web.domain.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CertificationMapper {
    /**증명서 신청
     */
    public  void insertIssue(EmpCert empCert);
     public void insertEmpCert(CommonCert commonCert);
     public void insertExperCert(CommonCert commonCert);
     public void insertRetireCert(CommonCert commonCert);

    /**내 증명서 발급 내역 조회
     */
    public List<Issue>  selectAllIssue(Long empId);


    /** 증명서 발급(다운로드)*/

    public EmpCert selectEmpCert(Long empId);
    public ExperienceCert selectExperCert(Long empId);
    public EmpCert selectRetireCert(Long empId);
}
