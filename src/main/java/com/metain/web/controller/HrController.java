package com.metain.web.controller;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;
import com.metain.web.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller


public class HrController {
    @Autowired
    private HrService hrService;



    @RequestMapping("/emp-list")

    public String selectAll(Model model){
//        List<Emp> empList = hrService.empList();
//        model.addAttribute("list",empList);
        return "hr/emp-list";
    }

    @RequestMapping("/new-emp-lists")
    @ResponseBody
    public List<NewEmp> newEmpSelectAll(){
        return hrService.newEmpSelectAll();
    }





}
