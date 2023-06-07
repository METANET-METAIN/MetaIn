package com.metain.web.controller;

import com.metain.web.domain.Emp;
import com.metain.web.dto.VacationListDTO;
import com.metain.web.service.HrService;
import com.metain.web.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.time.LocalDate;
import java.util.List;

@Controller
//@EnableScheduling
public class HomeController {

    @Autowired
    private VacationService vacationService;

    @RequestMapping("/index")
    public String home(Model model) {
        return "index";
    }

    /*@Scheduled(cron = "5 * * * * ?") // 오초마다
    public void fetchEventsScheduler() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        // session에서 객체 가져오기
        HttpSession session = requestAttributes.getRequest().getSession();
        Emp emp = (Emp) session.getAttribute("loginEmp");
        // Emp 객체를 이용한 로직 처리
        if (emp != null) {
            String empDept = emp.getEmpDept();
            List<VacationListDTO> events = fetchEvents(empDept);
            System.out.println(events);
        }
    }*/

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