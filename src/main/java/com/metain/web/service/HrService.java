package com.metain.web.service;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;
import com.metain.web.dto.NewEmpDTO;

import java.util.List;

public interface HrService {
    public List<Emp> empList();

    public int updateEmp(Emp emp);

    public List<NewEmpDTO> newEmpSelectAll();
    public Emp selectEmpInfo(Long empId);

}
