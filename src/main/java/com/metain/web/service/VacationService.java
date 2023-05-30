package com.metain.web.service;

import com.metain.web.domain.Vacation;
import com.metain.web.dto.VacationListDTO;

import java.util.List;

public interface VacationService {
    /*휴가 등록
    * */
    List<VacationListDTO> selectAllList();
}
