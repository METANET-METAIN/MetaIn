package com.metain.web.controller;

import com.metain.web.domain.Emp;
import com.metain.web.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class EmpController {
    @Autowired
    private EmpService empService;

    @RequestMapping("/test")
    public String selectAll(Model model){
        List<Emp> empList =empService.empList();
        model.addAttribute("list",empList);
        return "index";
    }

}
