package com.hoz.laptopshop.service.impl;

import com.hoz.laptopshop.dto.response.DailyOrderStatsDTO;
import com.hoz.laptopshop.dto.response.DashboardStatsDTO;
import com.hoz.laptopshop.dto.response.ProductStatsDTO;
import com.hoz.laptopshop.dto.response.RevenueDTO;
import com.hoz.laptopshop.entitis.Product;
import com.hoz.laptopshop.entitis.enums.OrderStatus;
import com.hoz.laptopshop.repository.IOrderRepository;
import com.hoz.laptopshop.repository.IProductRepository;
import com.hoz.laptopshop.repository.IUserRepository;
import com.hoz.laptopshop.service.IDashboardStatsService;
import com.hoz.laptopshop.service.IOrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardStatsServiceImpl implements IDashboardStatsService {

        private final IOrderRepository orderRepository;
        private final IProductRepository productRepository;
        private final IUserRepository userRepository;
        private final IOrderService orderService;

        /**
         * Lấy tất cả statistics cho dashboard
         * 
         * @return DashboardStatsDTO chứa tất cả metrics
         */
        @Override
        public DashboardStatsDTO getDashboardStats() {
                // 1. Revenue 7 ngày
                List<RevenueDTO> revenueData = orderService.getLast7DaysRevenue();
                Double totalRevenue = revenueData.stream()
                                .mapToDouble(RevenueDTO::getRevenue)
                                .sum();
                Double avgRevenue = totalRevenue / 7;

                // 2. Orders theo status (aggregate)
                Long completedOrders = orderRepository.countByStatus(OrderStatus.COMPLETE);
                Long processingOrders = orderRepository.countByStatusIn(
                                List.of(OrderStatus.PENDING, OrderStatus.SHIPPING));
                Long cancelledOrders = orderRepository.countByStatusIn(
                                List.of(OrderStatus.CANCELLED, OrderStatus.RETURNED));

                // 2.1. Orders breakdown (individual) for pie charts
                Long pendingOrders = orderRepository.countByStatus(OrderStatus.PENDING);
                Long shippingOrders = orderRepository.countByStatus(OrderStatus.SHIPPING);
                Long cancelledOrdersOnly = orderRepository.countByStatus(OrderStatus.CANCELLED);
                Long returnedOrders = orderRepository.countByStatus(OrderStatus.RETURNED);

                // 3. Products breakdown
                Long activeProducts = productRepository.countByIsActive(true);
                Long inactiveProducts = productRepository.countByIsActive(false);

                // 4. Users breakdown
                Long activeUsers = userRepository.countByIsActive(true);
                Long inactiveUsers = userRepository.countByIsActive(false);
                Long totalUsers = activeUsers + inactiveUsers;

                // 5. Thống kê đơn hàng 7 ngày (for bar chart)
                LocalDate sevenDaysAgo = LocalDate.now().minusDays(6); // 7 days including today
                List<IOrderRepository.IOrderCountProjection> rawOrderData = orderRepository
                                .getLast7DaysOrdersByStatus(sevenDaysAgo);

                // Process into DailyOrderStatsDTO - group by date
                Map<LocalDate, DailyOrderStatsDTO> dailyStatsMap = new HashMap<>();

                for (IOrderRepository.IOrderCountProjection projection : rawOrderData) {
                        LocalDate date = projection.getDate();
                        String status = projection.getStatus();
                        Long count = projection.getCount();

                        DailyOrderStatsDTO dailyStats = dailyStatsMap.computeIfAbsent(date,
                                        d -> DailyOrderStatsDTO.builder()
                                                        .date(String.format("%02d/%02d", d.getDayOfMonth(),
                                                                        d.getMonthValue()))
                                                        .completedOrders(0L)
                                                        .failedOrders(0L)
                                                        .build());

                        if ("COMPLETE".equals(status)) {
                                dailyStats.setCompletedOrders(dailyStats.getCompletedOrders() + count);
                        } else if ("RETURNED".equals(status) || "CANCELLED".equals(status)) {
                                dailyStats.setFailedOrders(dailyStats.getFailedOrders() + count);
                        }
                }

                // Ensure all 7 days are present (fill gaps with 0)
                List<DailyOrderStatsDTO> dailyOrderStats = new ArrayList<>();
                for (int i = 6; i >= 0; i--) {
                        LocalDate date = LocalDate.now().minusDays(i);
                        DailyOrderStatsDTO stats = dailyStatsMap.getOrDefault(date,
                                        DailyOrderStatsDTO.builder()
                                                        .date(String.format("%02d/%02d", date.getDayOfMonth(),
                                                                        date.getMonthValue()))
                                                        .completedOrders(0L)
                                                        .failedOrders(0L)
                                                        .build());
                        dailyOrderStats.add(stats);
                }

                // 6. Top 10 sản phẩm bán chạy (for bar chart and table)
                List<Product> topProducts = productRepository.findTop10ByIsActiveTrueOrderBySoldDesc();
                List<ProductStatsDTO> topProductsList = topProducts.stream()
                                .map(p -> ProductStatsDTO.builder()
                                                .productName(p.getName())
                                                .soldCount(p.getSold())
                                                .build())
                                .collect(Collectors.toList());

                // 5. Calculate Hero Card Metrics
                
                // 5.1. Revenue Growth % (current month vs previous month)
                LocalDate now = LocalDate.now();
                LocalDate startOfCurrentMonth = now.withDayOfMonth(1);
                LocalDate startOfPrevMonth = startOfCurrentMonth.minusMonths(1);
                LocalDate endOfPrevMonth = startOfCurrentMonth.minusDays(1);
                
                Double currentMonthRevenue = orderRepository.getRevenueByDateRange(
                        startOfCurrentMonth, now, OrderStatus.COMPLETE.name());
                Double prevMonthRevenue = orderRepository.getRevenueByDateRange(
                        startOfPrevMonth, endOfPrevMonth, OrderStatus.COMPLETE.name());
                
                Double revenueGrowthPercent = 0.0;
                if (prevMonthRevenue != null && prevMonthRevenue > 0 && currentMonthRevenue != null) {
                        revenueGrowthPercent = ((currentMonthRevenue - prevMonthRevenue) / prevMonthRevenue) * 100;
                } else if (currentMonthRevenue != null && currentMonthRevenue > 0 && (prevMonthRevenue == null || prevMonthRevenue == 0)) {
                        revenueGrowthPercent = 100.0; // 100% growth if no previous revenue
                }
                
                // 5.2. Delivery Success Rate (COMPLETE / total orders)
                long totalOrders = orderRepository.count();
                Double deliverySuccessRate = 0.0;
                if (totalOrders > 0) {
                        deliverySuccessRate = (completedOrders * 100.0) / totalOrders;
                }

                // 6. Build and return DTO
                return DashboardStatsDTO.builder()
                                .totalRevenue7Days(totalRevenue)
                                .avgRevenuePerDay(avgRevenue)
                                .completedOrders(completedOrders)
                                .processingOrders(processingOrders)
                                .cancelledOrders(cancelledOrders)
                                .activeProducts(activeProducts)
                                .totalUsers(totalUsers)
                                // Breakdown fields for pie charts
                                .pendingOrders(pendingOrders)
                                .shippingOrders(shippingOrders)
                                .cancelledOrdersOnly(cancelledOrdersOnly)
                                .returnedOrders(returnedOrders)
                                .inactiveProducts(inactiveProducts)
                                .activeUsers(activeUsers)
                                .inactiveUsers(inactiveUsers)
                                // Bar chart data
                                .dailyOrderStats(dailyOrderStats)
                                .topSellingProducts(topProductsList)
                                .revenueGrowthPercent(revenueGrowthPercent)
                                .deliverySuccessRate(deliverySuccessRate)
                                .build();
        }
}
