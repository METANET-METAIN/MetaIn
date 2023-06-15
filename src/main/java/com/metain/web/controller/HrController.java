package com.metain.web.controller;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;
import com.metain.web.dto.NewEmpDTO;
import com.metain.web.dto.VacationListDTO;
import com.metain.web.service.HrService;
import com.metain.web.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hr")

public class HrController {
    @Autowired
    private HrService hrService;


    @GetMapping("/emp-list")
    public String selectAll(Model model, Authentication auth) {
        Emp empInfo = (Emp) auth.getPrincipal();
        List<Emp> list=hrService.selectAll();
        model.addAttribute("emp",empInfo);
        model.addAttribute("list",list);
        return "/hr/emp-list";
    }



    //신입사원 등록
    @PostMapping("/insert-new-emp")
    public String insertNewEmp(NewEmp newEmp) {
        System.out.println("신입사원 등록 :");
        System.out.println(newEmp);
        hrService.insertNewEmp(newEmp);
        return "redirect:/hr/new-emp-list";
    }

    @GetMapping("/new-list")
    @ResponseBody
    public List<NewEmpDTO> newEmpSelectAll() {

        return hrService.newEmpSelectAll();
    }


//    사원 승인하기 (회원가입)
    @PostMapping("/confirm-new-emp")
    @ResponseBody
    public int confirmNewEmp(@RequestBody List<NewEmp> newEmpList, Emp emp) {
        return hrService.confirmNewEmp(newEmpList, emp);

    }

    @PostMapping("/updateEmp")
    @ResponseBody
    public ResponseEntity<String> updateEmp(@RequestBody Map<String,Object> requestData) {
        String empStatus = (requestData.get("empStatus").toString());
        String empGrade=requestData.get("empGrade").toString();
        Long updateEmpId = Long.parseLong(requestData.get("updateEmpId").toString());


        hrService.updateEmp(empStatus,empGrade,updateEmpId);
        return ResponseEntity.ok("성공");
    }
}
