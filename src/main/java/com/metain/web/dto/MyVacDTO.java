package com.metain.web.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class MyVacDTO {
    private Long empId;
    private Long vacId;
    private String vacType; //일반(반차), 일반(연차), 병가 , 조사(배우자5) ,조사(부모님5),조사(형제1), 예비군
    private Date vacRegDate;
    private Date vacStartDate;
    private Date vacEndDate;
    private String vacStatus; //승인상태

}
