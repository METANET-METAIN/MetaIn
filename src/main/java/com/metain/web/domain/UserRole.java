package com.metain.web.domain;

import lombok.Data;

@Data
public class UserRole {
    public UserRole(Long urId, Long empId, Long roleId) {
        this.urId = urId;
        this.empId = empId;
        this.roleId = roleId;
    }

    private Long urId;
    private Long empId;
    private Long roleId;



}
