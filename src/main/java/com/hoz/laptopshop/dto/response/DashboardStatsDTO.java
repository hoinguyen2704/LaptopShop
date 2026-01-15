package com.hoz.laptopshop.dto.response;

import java.util.List;

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

    // ========== Breakdown Fields for Pie Charts ==========

    /**
     * Số orders PENDING (breakdown cho pie chart)
     */
    private Long pendingOrders;

    /**
     * Số orders SHIPPING (breakdown cho pie chart)
     */
    private Long shippingOrders;

    /**
     * Số orders CANCELLED only (breakdown cho pie chart)
     */
    private Long cancelledOrdersOnly;

    /**
     * Số orders RETURNED (breakdown cho pie chart)
     */
    private Long returnedOrders;

    /**
     * Số sản phẩm inactive (breakdown cho pie chart)
     */
    private Long inactiveProducts;

    /**
     * Số người dùng active (breakdown cho pie chart)
     */
    private Long activeUsers;

    /**
     * Số người dùng inactive (breakdown cho pie chart)
     */
    private Long inactiveUsers;

    // ========== Bar Chart Data ==========

    /**
     * Thống kê đơn hàng 7 ngày gần nhất (cho bar chart)
     */
    private List<DailyOrderStatsDTO> dailyOrderStats;

    /**
     * Top 10 sản phẩm bán chạy nhất (cho bar chart và table)
     */
    private List<ProductStatsDTO> topSellingProducts;

    // ========== Hero Card Metrics ==========

    /**
     * Phần trăm tăng/giảm doanh thu so với tháng trước
     * Positive = tăng, Negative = giảm
     */
    private Double revenueGrowthPercent;

    /**
     * Tỷ lệ giao hàng thành công (% orders COMPLETE / total orders)
     */
    private Double deliverySuccessRate;
}
