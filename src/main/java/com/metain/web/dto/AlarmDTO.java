package com.metain.web.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class AlarmDTO {
    private Long empId;
    private String notiContent;
    private String notiUrl;
    private Date notiDt;
    private String notiType;
}
