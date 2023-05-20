package com.metain.web.service;

import com.metain.web.domain.Emp;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EmpService {
    List<Emp> empList();
}
