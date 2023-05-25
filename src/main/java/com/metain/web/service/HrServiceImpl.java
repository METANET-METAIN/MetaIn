package com.metain.web.service;

import com.metain.web.domain.Emp;
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
        List<Emp> list=hrMapper.empSelectAll();
        return list;
    }
}
