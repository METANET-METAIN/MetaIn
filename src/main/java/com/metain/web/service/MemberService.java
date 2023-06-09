package com.metain.web.service;


import com.metain.web.domain.Emp;

public interface MemberService {

    public Emp login(Emp emp);

    //관리자 정보
    public Emp selectAdminInfo(String empDept, String empGrade);
}
