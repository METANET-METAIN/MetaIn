package com.metain.web.controller;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;
import com.metain.web.domain.PrincipalDetails;
import com.metain.web.dto.NewEmpDTO;
import com.metain.web.service.HrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hr")

public class HrController {
    @Autowired
    private HrService hrService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping("/emp-list")
    public String selectAll(Model model, Authentication auth) {
        PrincipalDetails principalDetails = (PrincipalDetails)auth.getPrincipal();
        Long empId = principalDetails.getEmpId();
        Emp emp =  hrService.selectEmpInfo(empId);
        List<Emp> list=hrService.selectAll();

        model.addAttribute("emp",emp);
        model.addAttribute("list",list);
        return "/hr/emp-list";
    }





    //신입사원 등록
    @PostMapping("/insert-new-emp")
    public String insertNewEmp(NewEmp newEmp) {
        logger.info("HRCon/insertNewEmp 성공 확인 로그",newEmp);
        hrService.insertNewEmp(newEmp);
        return "redirect:/hr/new-emp-list";
    }


    @GetMapping("/new-emp-list")
    public String newEmpSelect(Model model, Authentication auth) {
        PrincipalDetails principalDetails= (PrincipalDetails) auth.getPrincipal();
        Long empId= principalDetails.getEmpId();
        Emp empInfo = hrService.selectEmpInfo(empId);
        model.addAttribute("emp", empInfo);

        return "/hr/new-emp-list";
    }

    @GetMapping("/new-list")
    @ResponseBody
    public List<NewEmpDTO> newEmpSelectAll() {
        logger.info("HRCon/newEmpSelectAll 접근 확인 로그");
        return hrService.newEmpSelectAll();
    }


//    사원 승인하기 (회원가입)
    @PostMapping("/confirm-new-emp")
    @ResponseBody
    public int confirmNewEmp(@RequestBody List<NewEmp> newEmpList, Emp emp, Model model, Authentication auth) {
        PrincipalDetails principalDetails= (PrincipalDetails) auth.getPrincipal();
        Long empId= principalDetails.getEmpId();
        Emp empInfo = hrService.selectEmpInfo(empId);
        model.addAttribute("emp", empInfo);
        model.addAttribute("emp", emp);
        return hrService.confirmNewEmp(newEmpList, empInfo);

    }

    @PostMapping("/updateEmp")
    @ResponseBody
    public ResponseEntity<String> updateEmp(@RequestBody Map<String,Object> requestData) {

        String empStatus = (requestData.get("empStatus").toString());
        String empGrade=requestData.get("empGrade").toString();

        String empDept=requestData.get("empDept").toString();
        Long empId = Long.parseLong(requestData.get("updateEmpId").toString());


        hrService.updateEmp(empStatus,empGrade,empDept,empId);
        return ResponseEntity.ok("성공");
    }
}
