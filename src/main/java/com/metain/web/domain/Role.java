package com.metain.web.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;



public enum Role implements GrantedAuthority {

    //    SW("EMPLOYEE"), //사원
//    DR("ASSISTANT"), //대리
//    GJ("MANAGER"), //과장
//    CJ("DEPUTY"), //차장
//    HR("HR"), //인사관리
//    ADMIN("ADMIN"), //관리자
//    ACT("ACTIVE"), //재직자
//    RET("RETIREE"); //퇴직자
    EMPLOYEE("EMPLOYEE", "사원"),
    ASSISTANT("ASSISTANT", "대리"),
    MANAGER("MANAGER", "과장"),
    DEPUTY("DEPUTY", "차장"),
    HR("HR", "인사관리"),
    ADMIN("ADMIN", "관리자"),
    ACTIVE("ACTIVE", "재직자"),
    RETIREE("RETIREE", "퇴직자");

    private String role;

    private String roleName;



    Role(String role, String roleName) {
        this.role = role;
        this.roleName = roleName;

    }




    public String value() {
        return role;
    }

    @Override
    public String getAuthority(){
        return role;
    }

    // 등급과 역할 매핑
//    public static Role fromGrade(String grade) {
//        switch (grade) {
//            case "ADMIN":
//                return ADMIN;
//            case "EMPLOYEE":
//                return SW;
//            case "ASSISTANT":
//                return DR;
//            case "MANAGER":
//                return GJ;
//            case "DEPUTY":
//                return CJ;
//            case "HR":
//                return HR;
//            default:
//                throw new IllegalArgumentException("Invalid grade: " + grade);
//        }
//    }
    public static Role fromGrade(String grade) {
        switch (grade) {
            case "관리자":
                return ADMIN;
            case "사원":
                return EMPLOYEE;
            case "대리":
                return ASSISTANT;
            case "과장":
                return MANAGER;
            case "차장":
                return DEPUTY;
            case "인사관리":
                return HR;


            default:
                throw new IllegalArgumentException("Invalid grade: " + grade);
        }
    }

    public static String toKorean(Role role) {
        if (role == Role.ADMIN) {
            return "관리자";
        } else if (role == Role.EMPLOYEE) {
            return "사원";
        } else if (role == Role.ASSISTANT) {
            return "대리";
        } else if (role == Role.MANAGER) {
            return "과장";
        } else if (role == Role.DEPUTY) {
            return "차장";
        } else if (role == Role.HR) {
            return "인사관리";
        } else {
            throw new IllegalArgumentException("Invalid grade: " + role);
        }
    }





    // 상태와 역할 매핑
    public static Role fromStatus(String status) {
        switch (status) {
            case "ACTIVE":
                return ACTIVE;
            case "RETIREE":
                return RETIREE;
            default:
                throw new IllegalArgumentException("Invalid status: " + status);
        }
    }

}