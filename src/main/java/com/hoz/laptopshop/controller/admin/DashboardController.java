package com.hoz.laptopshop.controller.admin;

import com.hoz.laptopshop.dto.response.DashboardStatsDTO;
import com.hoz.laptopshop.dto.response.RevenueDTO;
import com.hoz.laptopshop.dto.response.TopCustomerDTO;
import com.hoz.laptopshop.service.DashboardStatsService;
import com.hoz.laptopshop.service.RevenueService;
import com.hoz.laptopshop.service.TopCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private TopCustomerService topCustomerService;
    
    @Autowired
    private RevenueService revenueService;
    
    @Autowired
    private DashboardStatsService dashboardStatsService;

    @GetMapping("/admin")
    public String getDashboard(Model model) {
        // Lấy dashboard statistics (tất cả metrics)
        DashboardStatsDTO stats = dashboardStatsService.getDashboardStats();
        model.addAttribute("stats", stats);
        
        // Lấy danh sách 10 khách hàng tiềm năng
        List<TopCustomerDTO> topCustomers = topCustomerService.getTop10PotentialCustomers();
        model.addAttribute("topCustomers", topCustomers);
        
        // Lấy doanh thu 7 ngày gần nhất (cho chart)
        List<RevenueDTO> last7DaysRevenue = revenueService.getLast7DaysRevenue();
        model.addAttribute("revenueData", last7DaysRevenue);
        
        return "admin/dashboard/dashboard-page";
    }
}
