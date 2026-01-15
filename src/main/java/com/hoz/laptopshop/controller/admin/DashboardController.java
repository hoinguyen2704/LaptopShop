package com.hoz.laptopshop.controller.admin;

import com.hoz.laptopshop.dto.response.DashboardStatsDTO;
import com.hoz.laptopshop.dto.response.RevenueDTO;
import com.hoz.laptopshop.dto.response.TopCustomerDTO;
import com.hoz.laptopshop.service.DashboardStatsService;
import com.hoz.laptopshop.service.IOrderService;
import com.hoz.laptopshop.service.IUserService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardStatsService dashboardStatsService;
    private final IUserService userService;
    private final IOrderService orderService;

    @GetMapping("/admin")
    public String getDashboard(Model model) {
        // Lấy dashboard statistics (tất cả metrics)
        DashboardStatsDTO stats = dashboardStatsService.getDashboardStats();
        model.addAttribute("stats", stats);

        // Lấy danh sách 10 khách hàng tiềm năng
        List<TopCustomerDTO> topCustomers = userService.getTop10PotentialCustomers();
        model.addAttribute("topCustomers", topCustomers);

        // Lấy doanh thu 7 ngày gần nhất (cho chart)
        List<RevenueDTO> last7DaysRevenue = orderService.getLast7DaysRevenue();
        model.addAttribute("revenueData", last7DaysRevenue);

        return "admin/dashboard/dashboard-page";
    }
}
