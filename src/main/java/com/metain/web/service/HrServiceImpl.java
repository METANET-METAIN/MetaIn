package com.metain.web.service;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;
import com.metain.web.mapper.HrMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HrServiceImpl implements HrService {
    @Autowired
    private HrMapper hrMapper;
    @Override
    public List<Emp> empList() {
        return hrMapper.empSelectAll();
    }

    @Override
    public int updateEmp(Emp emp) {
        return 0;
    }

    @Override
    public List<NewEmp> newEmpSelectAll() {
        return hrMapper.newEmpSelectAll();
    }

    @Override
    public Emp selectEmpInfo(Long empId) {
        Emp dbEmp=hrMapper.selectEmpInfo(empId);
        if (dbEmp==null){ //db에서 꺼내온  emp가 null이면 에러페이지 이동
            return null;
        }
        return dbEmp;
    }
}
