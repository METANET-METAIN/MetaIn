package com.metain.web.service;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;
import com.metain.web.dto.NewEmpDTO;

import java.util.List;

public interface HrService {

    //신입사원 등록
    public int insertNewEmp(NewEmp newEmp);

    //신입사원 목록
    public List<NewEmpDTO> newEmpSelectAll();


    public List<Emp> selectAll();


    public Emp selectEmpInfo(Long empId);
   //신입사원 승인
    public int confirmNewEmp(List<NewEmp> newEmpList, Emp emp);

    List<Emp> newEmp();

    void updateEmp(String empStatus, String empGrade, String empDept, Long updateEmpId);

}
