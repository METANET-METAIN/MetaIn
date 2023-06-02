package com.metain.web.service;

import com.metain.web.dto.MyCertListDTO;
import com.metain.web.dto.MyVacDTO;

import java.util.List;

public interface MyPageService {

    List<MyCertListDTO> selectIssueAll();

    List<MyVacDTO> selectMyVacList(MyVacDTO myVacDTO);
}
