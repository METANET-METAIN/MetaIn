package com.metain.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacationApply {
    private Long vhisId;
    private Long vacId;
    private Long fileId;
    private Long empId;
}
