package com.metain.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private Long notiId;
    private Long empId;
    private String notiContent;
    private String notiUrl;
    private Date notiDt;
}
