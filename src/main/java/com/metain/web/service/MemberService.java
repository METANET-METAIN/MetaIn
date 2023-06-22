package com.metain.web.service;


import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;

import java.util.List;

public interface MemberService {

    //관리자 정보
    public Emp selectAdminInfo(String empDept, String empGrade);
}
