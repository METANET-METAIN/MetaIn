package com.metain.web.service;

import com.metain.web.domain.Emp;
import com.metain.web.domain.EmpCert;
import com.metain.web.domain.Issue;
import com.metain.web.mapper.CertificationMapper;
import com.metain.web.mapper.HrMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationServiceImpl implements CertificationService{

    //Mapper인터페이스 의존성주입
    @Autowired
    private CertificationMapper certificationMapper;

    private final HrService hrService;

    @Autowired
    public CertificationServiceImpl(HrService hrService) {
        this.hrService = hrService;
    }


    //로그인한 사원 인사정보 조회
    @Override
    public Emp getEmpInfoList(Long empId) {
        Emp empInfolist = hrService.selectEmpInfo(empId);
        if(empInfolist == null){
            return null;
        }
        return empInfolist;
    }



//재직증명서 발급신청 - 정보입력 
    //재직증명서 신청시 입력정보 추가
    //재직증명서신청시 발급내역 추가
    @Override
    public int applyEmpCert(EmpCert empCert) {

        certificationMapper.insertEmpCert(empCert);
//        //트랜젝션처리하기
//        certificationMapper.insertEmpCert(empCert);
//        certificationMapper.insertIssue(empCert);
        return 1;
    }
    
    //증명서 생성 기능
    @Override
    public EmpCert getEmpCertList(Long empId) {
       EmpCert list = certificationMapper.selectAllEmpCert(empId);
        if(list == null){
            return null;
        }
        return list;
    }
}
