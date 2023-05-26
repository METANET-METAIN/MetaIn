package com.metain.web.service;

import com.metain.web.domain.Emp;

import java.util.List;

public interface HrService {
    public List<Emp> empList();

    public int updateEmp(Emp emp);
}
