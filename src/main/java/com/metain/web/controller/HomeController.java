package com.metain.web.controller;

import com.metain.web.domain.Emp;
import com.metain.web.dto.VacationListDTO;
import com.metain.web.service.HrService;
import com.metain.web.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private VacationService vacationService;

    @Autowired
    private HrService hrService;

    @RequestMapping("/index")
    public String home( Model model, Authentication auth, Emp emp) {

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
        System.out.println(emp);
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
            LocalDate today = LocalDate.now();
            List<VacationListDTO> events = vacationService.selectListByDept(empDept, today);
            return ResponseEntity.ok(events);
        } else {
            // 로그인되지 않은 경우 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @RequestMapping("/newEmp")
    public ResponseEntity<List<Emp>> newEmp() {
        List<Emp> events = hrService.newEmp();
        System.out.println(events);
        if (events != null) {;
            return ResponseEntity.ok(events);
        } else {
            // 로그인되지 않은 경우 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
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