package com.metain.web.mapper;

import com.metain.web.domain.*;
import com.metain.web.dto.CertInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface CertificationMapper {
    /**증명서 신청
     */
    public  void insertIssue(EmpCert empCert);



    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void insertEmpCert(CertInfoDTO certInfoDTO);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void insertExperCert(CertInfoDTO certInfoDTO);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void insertRetireCert(CertInfoDTO certInfoDTO);



    // 마지막으로 자동으로 생성된 ID 값을 가져오는 메소드
    int getLastEmpCertId();
    int getLastExperCertId();
    int getLastRetireCertId();

    /**내 증명서 발급 내역 조회
     */
    public List<Issue>  selectAllIssue(Long empId);

    public int selectEmpCertId();


    /** 증명서 발급*/

    public EmpCert selectEmpCert(Long generatedId);
    public ExperienceCert selectExperCert(Long generatedId);
    public RetireCert selectRetireCert(Long generatedId);





}
