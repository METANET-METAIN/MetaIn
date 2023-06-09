package com.metain.web.service;

import com.metain.web.domain.Emp;
import com.metain.web.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    public MemberMapper memberMapper;


    @Override
    public Emp login(Emp emp) {
        return memberMapper.login(emp.getEmpSabun(), emp.getEmpPwd());
    }

    @Override
    public Emp selectAdminInfo(String empDept, String empGrade) {
        Emp adminInfo=memberMapper.selectAdminInfo(empDept, empGrade) ;
            if(adminInfo==null && empGrade!="팀관리자"){
                return null;
            }
        return adminInfo;
    }
}
