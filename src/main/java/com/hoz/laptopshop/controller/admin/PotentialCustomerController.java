package com.hoz.laptopshop.controller.admin;

import com.hoz.laptopshop.dto.response.TopCustomerDTO;
import com.hoz.laptopshop.service.TopCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller để xử lý các request liên quan đến khách hàng tiềm năng
 */
@Controller
@RequestMapping("/admin/potential-customers")
public class PotentialCustomerController {

    @Autowired
    private TopCustomerService topCustomerService;

    /**
     * Hiển thị danh sách 10 khách hàng tiềm năng
     * 
     * @param model Model để truyền dữ liệu sang view
     * @return Tên view để hiển thị
     */
    @GetMapping
    public String showPotentialCustomers(Model model) {
        List<TopCustomerDTO> topCustomers = topCustomerService.getTop10PotentialCustomers();
        model.addAttribute("topCustomers", topCustomers);
        return "admin/customer/potential-customers";
    }
}
