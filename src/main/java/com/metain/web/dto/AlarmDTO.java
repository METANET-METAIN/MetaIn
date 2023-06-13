package com.metain.web.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class AlarmDTO {
    private Long empId;
    private String notiContent;
    private Date notiDt;
}
