package com.metain.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CertificationController {

    @RequestMapping("/certification/emp-cert-show")
    public String empCertShow(){

        return "/certification/emp-cert-show";
    }
    @RequestMapping("/certification/emp-cert-apply")
    public  String empCertApply(){

        return "/certification/emp-cert-apply";
    }
    @RequestMapping("/certification/emp-cert-complete")
    public String empCertComplete(){

        return "certification/emp-cert-complete";
    }

    @RequestMapping("/certification/my-cert-list")
    public String myCertList(){
        return "certification/my-cert-list";
    }


    @RequestMapping("/certification/exper-cert-show")
    public String experCertApply(){

        return "/certification/exper-cert-show";
    }

    @RequestMapping("/certification/exper-cert-apply")
    public String experCertShow(){

        return "/certification/exper-cert-apply";
    }




}
