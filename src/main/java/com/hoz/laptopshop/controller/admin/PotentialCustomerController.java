package com.hoz.laptopshop.controller.admin;

import com.hoz.laptopshop.dto.response.TopCustomerDTO;
import com.hoz.laptopshop.service.IUserService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/potential-customers")
@RequiredArgsConstructor
public class PotentialCustomerController {

    private final IUserService userService;

    @GetMapping
    public String showPotentialCustomers(Model model) {
        List<TopCustomerDTO> topCustomers = userService.getTop10PotentialCustomers();
        model.addAttribute("topCustomers", topCustomers);
        return "admin/customer/potential-customers";
    }
}
