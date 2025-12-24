package com.hoz.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
    @GetMapping("/admin")
    public String getDashboard() {
        return "admin/dashboard/show";
    }
}
