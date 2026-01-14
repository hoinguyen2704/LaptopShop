package com.hoz.laptopshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO chứa tất cả thống kê cho Dashboard
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {

    /**
     * Tổng doanh thu 7 ngày gần nhất (chỉ orders COMPLETE)
     */
    private Double totalRevenue7Days;

    /**
     * Doanh thu trung bình mỗi ngày
     */
    private Double avgRevenuePerDay;

    /**
     * Tổng số orders đã hoàn thành (COMPLETE)
     */
    private Long completedOrders;

    /**
     * Số orders đang xử lý (PENDING + SHIPPING)
     */
    private Long processingOrders;

    /**
     * Số orders đã hủy/hoàn (CANCELLED + RETURNED)
     */
    private Long cancelledOrders;

    /**
     * Tổng số sản phẩm đang active
     */
    private Long activeProducts;

    /**
     * Tổng số người dùng
     */
    private Long totalUsers;
}
