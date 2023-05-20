package com.metain.web.service;

import com.metain.web.domain.Emp;
import com.metain.web.mapper.EmpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Override
    public List<Emp> empList() {
        List<Emp> list=empMapper.selectAll();
        return list;
    }
}
