package com.metain.web.controller;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;
import com.metain.web.dto.NewEmpDTO;
import com.metain.web.service.HrService;
import com.metain.web.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/hr")

public class HrController {
    @Autowired
    private HrService hrService;


    @GetMapping("/emp-lists")
    @ResponseBody
    public List<Emp> selectAll() {

        return hrService.selectAll();
    }

//    @RequestMapping("/new-emp-list")
//    public String newEmpSelectAll(Model model){
//        List<NewEmpDTO> list = hrService.newEmpSelectAll();
//        System.out.println(list);
//        model.addAttribute("newEmpList", list);
//        return "hr/new-emp-list";
//    }


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

//    @PostMapping("/confirm-new-emp")
//    @ResponseBody
//    public int confirmNewEmp(@RequestBody List<NewEmp> newEmp, Emp emp) {
//        System.out.println(newEmp);
//        return hrService.confirmNewEmp(newEmp, emp);
//    }


//    사원 승인하기 (회원가입)
    @PostMapping("/confirm-new-emp")
    @ResponseBody
    public int confirmNewEmp(@RequestBody List<NewEmp> newEmpList, Emp emp) {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//        if (newEmpList != null && !newEmpList.isEmpty()) {
//            for (NewEmp newEmp : newEmpList) {
//                if (newEmp.getNewBirth() != null && !newEmp.getNewBirth().isEmpty()) {
//                    try {
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                        Date empBirth = dateFormat.parse(newEmp.getNewBirth());
//                        emp.setEmpBirth(empBirth);
//
//                        // 생년월일 암호화
//                        String encodedBirth = encoder.encode(newEmp.getNewBirth());
//                        emp.setEmpPwd(encodedBirth);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        // emp의 비밀번호 암호화
//        if (emp.getEmpPwd() != null && !emp.getEmpPwd().isEmpty()) {
//            String encodedPwd = encoder.encode(emp.getEmpPwd());
//            emp.setEmpPwd(encodedPwd);
//        }
        return hrService.confirmNewEmp(newEmpList, emp);

    }
}
