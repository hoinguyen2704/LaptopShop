package com.hoz.laptopshop.service;

import com.hoz.laptopshop.dto.response.DashboardStatsDTO;
import com.hoz.laptopshop.dto.response.RevenueDTO;
import com.hoz.laptopshop.entitis.enums.OrderStatus;
import com.hoz.laptopshop.repository.IOrderRepository;
import com.hoz.laptopshop.repository.IProductRepository;
import com.hoz.laptopshop.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service để tính toán tất cả statistics cho Dashboard
 */
@Service
@RequiredArgsConstructor
public class DashboardStatsService {

    private final RevenueService revenueService;
    private final IOrderRepository orderRepository;
    private final IProductRepository productRepository;
    private final IUserRepository userRepository;

    /**
     * Lấy tất cả statistics cho dashboard
     * 
     * @return DashboardStatsDTO chứa tất cả metrics
     */
    public DashboardStatsDTO getDashboardStats() {
        // 1. Revenue 7 ngày
        List<RevenueDTO> revenueData = revenueService.getLast7DaysRevenue();
        Double totalRevenue = revenueData.stream()
                .mapToDouble(RevenueDTO::getRevenue)
                .sum();
        Double avgRevenue = totalRevenue / 7;

        // 2. Orders theo status
        Long completedOrders = orderRepository.countByStatus(OrderStatus.COMPLETE);
        Long processingOrders = orderRepository.countByStatusIn(
                List.of(OrderStatus.PENDING, OrderStatus.SHIPPING));
        Long cancelledOrders = orderRepository.countByStatusIn(
                List.of(OrderStatus.CANCELLED, OrderStatus.RETURNED));

        // 3. Products và Users
        Long activeProducts = productRepository.countByIsActive(true);
        Long totalUsers = userRepository.count();

        return DashboardStatsDTO.builder()
                .totalRevenue7Days(totalRevenue)
                .avgRevenuePerDay(avgRevenue)
                .completedOrders(completedOrders)
                .processingOrders(processingOrders)
                .cancelledOrders(cancelledOrders)
                .activeProducts(activeProducts)
                .totalUsers(totalUsers)
                .build();
    }
}
