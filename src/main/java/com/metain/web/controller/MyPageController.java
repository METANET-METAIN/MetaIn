package com.metain.web.controller;

import com.metain.web.domain.Issue;
import com.metain.web.dto.MyCertListDTO;
import com.metain.web.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private MyPageService myPageService;

    @GetMapping("/update-mypage")
    public String updateMyPage() {
        return "/mypage/update-mypage";
    }

    @RequestMapping("/my-cert-list")
    public String selectIssueAll(Model model) {
        List<MyCertListDTO> list = myPageService.selectIssueAll();

        model.addAttribute("issueList", list);
        return "/mypage/my-cert-list";
    }

    @GetMapping("/my-vac-list")
    public String selectMyVacList() {
        return "/mypage/my-vac-list";
    }
}
