package com.metain.web.service;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;

import java.util.List;

public interface HrService {
    public List<Emp> empList();

    public int updateEmp(Emp emp);

    public List<NewEmp> newEmpSelectAll();

    public Emp selectEmpInfo(Long empId);
}
