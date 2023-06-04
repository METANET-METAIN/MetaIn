package com.metain.web.mapper;

import com.metain.web.domain.EmpCert;
import com.metain.web.domain.ExperienceCert;
import com.metain.web.domain.Issue;
import com.metain.web.domain.RetireCert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CertificationMapper {
    /**증명서 신청
     */
    public  void insertIssue(EmpCert empCert);
     public void insertEmpCert(EmpCert empcert);
     public void insertExperienceCert(ExperienceCert experiencecert);
     public void insertRetireCert(RetireCert retirecert);

    /**내 증명서 발급 내역 조회
     */
    public List<Issue> selectAllIssue(Long empId);


    /** 증명서 발급(다운로드)*/

    public List<EmpCert> selectAllEmpCert(Long empId);
    public List<ExperienceCert> selectAllExperienceCert(Long empId);
    public List<RetireCert> selectAllRetireCert(Long empId);
}
