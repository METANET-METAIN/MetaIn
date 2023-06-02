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

    @Autowired
    private HrMapper hrMapper;

    //로그인한 사원 인사정보 조회
    @Override
    public List<Emp> getEmpInfoList(Long empId) {
        List<Emp> list = hrMapper.selectEmpInfo(empId);
        if(list == null){
            return null;
        }
        return list;
    }

    //증명서신청시 발급내역 추가
    @Override
    public int addCertIssue(Issue issue) {
        certificationMapper.insertIssue(issue);
        return 1;
    }

    //재직증명서 신청시 발급정보 추가
    @Override
    public int applyEmpCert(EmpCert empCert) {
        certificationMapper.insertEmpCert(empCert);
        return 1;
    }
    
    //증명서 생성시 넣어줄 증명서발급정보
    @Override
    public List<EmpCert> getEmpCertList(Long empId) {
        List<EmpCert> list = certificationMapper.selectAllEmpCert(empId);
        if(list == null){
            return null;
        }
        return list;
    }
}
