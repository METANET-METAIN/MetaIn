package com.metain.web.service;

import com.metain.web.domain.*;
import com.metain.web.dto.CertInfoDTO;
import com.metain.web.dto.ImageRequestData;

import java.io.IOException;

public interface CertificationService {

    //필요한 emp 정보 list로 가져오기
    public Emp getEmpInfoList(Long empId);




    //재직증명서 신청 기능
    public EmpCert applyAndSelectEmpCert(CertInfoDTO certInfoDTO);

    //경력증명서 신청
    public ExperienceCert applyAndSelectExperCert(CertInfoDTO certInfoDTO);

    //퇴직증명서 신청
    public RetireCert applyAndSelectRetireCert(CertInfoDTO certInfoDTO);


    //증명서 생성 기능
    //증명서 생성할때 필요한 증명서발급정보 list로 가져오기
    public EmpCert getEmpCert(Long empCertId);

    public ExperienceCert getExperCert(Long experCertId);

    public RetireCert getRetireCert(Long RetireCertId);

    public void makeCertPdf(ImageRequestData request)throws IOException;

    public void signPdf(String filename) throws Exception;

    //마이페이지서비스로
//    //다운로드할 증명서 파일이름가져오기
//    public String getCertFilename(Long certId, String certSort);



}
