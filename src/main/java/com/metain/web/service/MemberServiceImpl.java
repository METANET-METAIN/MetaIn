package com.metain.web.service;

import com.metain.web.domain.Emp;
import com.metain.web.mapper.HrMapper;
import com.metain.web.mapper.MemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    public MemberMapper memberMapper;

    @Autowired
    public HrMapper hrMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public Emp selectAdminInfo(String empDept, String empGrade) {
        Emp adminInfo=memberMapper.selectAdminInfo(empDept, empGrade) ;
            if(adminInfo==null && empGrade!="팀관리자"){
                return null;
            }
        return adminInfo;
    }


}
