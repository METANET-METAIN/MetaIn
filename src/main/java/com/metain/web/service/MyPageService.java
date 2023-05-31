package com.metain.web.service;

import com.metain.web.dto.MyCertListDTO;

import java.util.List;

public interface MyPageService {

    List<MyCertListDTO> selectIssueAll();
}
