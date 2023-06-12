package com.metain.web.service;


import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;

import java.util.List;

public interface MemberService {

//    //신입사원 승인
//    public int confirmNewEmp(List<NewEmp> newEmp, Emp emp);

//    public Emp login(Emp emp);

    //관리자 정보
    public Emp selectAdminInfo(String empDept, String empGrade);
}
