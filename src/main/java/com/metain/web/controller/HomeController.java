package com.metain.web.controller;

import com.metain.web.domain.Emp;
import com.metain.web.domain.PrincipalDetails;
import com.metain.web.dto.VacationListDTO;
import com.metain.web.service.HrService;
import com.metain.web.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private VacationService vacationService;

    @Autowired
    private HrService hrService;
//   @RequestMapping("/index")

//    public String home(Model model, Authentication auth) {
//        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
//        Long empId = principalDetails.getEmpId();
//        List<String> roleNames = new ArrayList<>();
//        System.out.println(principalDetails);
//        System.out.println(empId);
//
//        for (GrantedAuthority authority : principalDetails.getAuthorities()) {
//            String roleName = authority.getAuthority();
//            roleNames.add(roleName);
//        }
//
//
//        if (roleNames != null && !roleNames.isEmpty()) {
//            System.out.println(principalDetails);
//        model.addAttribute("emp", principalDetails);
//        model.addAttribute("roleNames : " , roleNames);
//            return "index";
//        }
//        return null;

//     public String home( Model model, Authentication auth) {
//         Emp emp = (Emp)auth.getPrincipal();
//         model.addAttribute("emp",emp);
//         return "index";



    //}
    @RequestMapping("/index")
    public String home( Model model, Authentication auth) {
        PrincipalDetails principalDetails = (PrincipalDetails)auth.getPrincipal();
        Long EmpId = principalDetails.getEmpId();
        Emp empList =  hrService.selectEmpInfo(EmpId);

        if (empList != null) {
            model.addAttribute("emp", empList);
            System.out.println("home : " + empList);
            return "index";
        }
    return null;
    }
// 유효하지 않은 경우에 대한 처리


        @RequestMapping("/fetchEvents")
    public ResponseEntity<List<VacationListDTO>> fetchEventsForLoggedInUser(Authentication auth) {
            PrincipalDetails principalDetails = (PrincipalDetails)auth.getPrincipal();
            Long EmpId = principalDetails.getEmpId();
            Emp emp =  hrService.selectEmpInfo(EmpId);

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
    public ResponseEntity<List<Emp>> newEmp(Authentication auth) {
        PrincipalDetails principalDetails = (PrincipalDetails)auth.getPrincipal();
        Long EmpId = principalDetails.getEmpId();

        List<Emp> events = hrService.newEmp();
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