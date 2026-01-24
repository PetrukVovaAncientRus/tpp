package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {
    private final SqlService sqlService;

    public WebController(SqlService sqlService) {
        this.sqlService = sqlService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/execute")
    public String execute(@RequestParam String sql, Model model) {
        model.addAttribute("sql", sql);
        try {
            model.addAttribute("result", sqlService.executeUnsafe(sql));
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "index";
    }


}
