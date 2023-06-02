package com.metain.web.service;

import com.metain.web.domain.Emp;
import com.metain.web.dto.NewEmpDTO;

import java.util.List;

public interface HrService {
    public List<Emp> empList();

    public int updateEmp(Emp emp);

//    전체 신입사원 목록
    public List<NewEmpDTO> newEmpSelectAll();
}
