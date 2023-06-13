package com.metain.web.controller;

import com.metain.web.domain.Emp;
import com.metain.web.dto.PrincipalDetails;
import com.metain.web.dto.VacationListDTO;
import com.metain.web.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private VacationService vacationService;


    @GetMapping("/index")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getPrincipal());

        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
        System.out.println(auth.getName());
        System.out.println("empId!!!!!!" + principalDetails.getEmpId());
        model.addAttribute("name", principalDetails.getUsername());
        model.addAttribute("empId", principalDetails.getEmpId());
        model.addAttribute("auth", principalDetails.getAuthorities());





//
//        model.addAttribute("loginType", "login-form");
//        model.addAttribute("pageName", "loginPage");

//        if(auth != null){
//            Emp loginEmp =
//        }

//        Emp loginEmp = (Emp) httpSession.getAttribute("loginEmp");
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
//        model.addAttribute("empList", principalDetails.empList(emp));
        System.out.println("index 접근했다!!! " );
//        if(principalDetails != null) {
//            model.addAttribute("getUsername", principalDetails.getUsername());
//
//        }
//        System.out.println("emp!!! " + emp);
//        System.out.println("model!!!! " + model);
//        System.out.println("principalDetails!!!!!! " + principalDetails);
        return "index";


    }

    @RequestMapping("/fetchEvents")
    public ResponseEntity<List<VacationListDTO>> fetchEventsForLoggedInUser(HttpSession session) {
        // session에서 객체 가져오기
        Emp emp = (Emp) session.getAttribute("loginEmp");

        if (emp != null) {
            String empDept = emp.getEmpDept();
            List<VacationListDTO> events = fetchEvents(empDept);
            return ResponseEntity.ok(events);
        } else {
            // 로그인되지 않은 경우 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private List<VacationListDTO> fetchEvents(String empDept) {
        LocalDate today = LocalDate.now();
        return vacationService.selectListByDept(empDept, today);
    }



    @GetMapping("/hr/{newEmp}")
    public String goPageHr(@PathVariable String newEmp) {
        return "/hr/" + newEmp;
    }

    @GetMapping("/mypage/{mypage}")
    public String goPageMy(@PathVariable String mypage) {
        return "/mypage/" + mypage;
    }

    @GetMapping("/member/{member}")
    public String goPageMem(@PathVariable String member) {
        return "/member/" + member;
    }
}