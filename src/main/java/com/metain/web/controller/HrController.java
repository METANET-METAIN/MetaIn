package com.metain.web.controller;

import com.metain.web.domain.NewEmp;
import com.metain.web.dto.NewEmpDTO;
import com.metain.web.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hr")

public class HrController {
    @Autowired
    private HrService hrService;



    @RequestMapping("/emp-list")

    public String selectAll(Model model){
//        List<Emp> empList = hrService.empList();
//        model.addAttribute("list",empList);
        return "hr/emp-list";
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
    public String insertNewEmp(NewEmp newEmp){
        System.out.println("신입사원 등록 :");
        System.out.println(newEmp);
        hrService.insertNewEmp(newEmp);
        return "redirect:/hr/new-emp-list";
    }

    @GetMapping("/new-list")
    @ResponseBody
    public List<NewEmpDTO> newEmpSelectAll(){

        return hrService.newEmpSelectAll();
    }





}
