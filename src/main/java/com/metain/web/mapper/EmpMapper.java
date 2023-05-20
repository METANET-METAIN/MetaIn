package com.metain.web.mapper;

import com.metain.web.domain.Emp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpMapper {
    List<Emp> selectAll();
}
