package com.hoz.laptopshop.repository;

import com.hoz.laptopshop.entitis.Order;
import com.hoz.laptopshop.entitis.User;
import com.hoz.laptopshop.entitis.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long>{
    List<Order> findByUser(User user);
    
    /**
     * Lấy doanh thu theo ngày cho 7 ngày gần nhất
     * Chỉ tính orders có status = COMPLETE
     * Doanh thu được tính theo ngày UPDATE thành COMPLETE (updated_at), không phải ngày tạo
     * 
     * @param startDate Ngày bắt đầu (7 ngày trước)
     * @param status Status của order (COMPLETE)
     * @return Danh sách doanh thu theo ngày
     */
    @Query(value = """
            SELECT DATE(o.updated_at) AS date,
                   SUM(o.total_price) AS revenue
            FROM orders o
            WHERE o.status = :status
              AND DATE(o.updated_at) >= :startDate
            GROUP BY DATE(o.updated_at)
            ORDER BY DATE(o.updated_at) ASC
            """, nativeQuery = true)
    List<IRevenueProjection> getLast7DaysRevenue(
            @Param("startDate") LocalDate startDate,
            @Param("status") String status);
    
    /**
     * Đếm số orders theo status
     * @param status Status cần đếm
     * @return Số lượng orders
     */
    Long countByStatus(OrderStatus status);
    
    /**
     * Đếm số orders theo nhiều status (dùng cho PENDING + SHIPPING hoặc CANCELLED + RETURNED)
     * @param statuses List of statuses
     * @return Số lượng orders
     */
    Long countByStatusIn(List<OrderStatus> statuses);
    
    /**
     * Interface projection cho revenue query
     */
    interface IRevenueProjection {
        LocalDate getDate();
        Double getRevenue();
    }
}
