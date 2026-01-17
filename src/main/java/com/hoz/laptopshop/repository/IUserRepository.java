package com.hoz.laptopshop.repository;

import com.hoz.laptopshop.entitis.User;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByFullName(String fullName);

    User findById(long id);

    boolean existsByEmail(String email);

    void deleteById(Long id);

    List<User> findOneByEmail(String email);

    List<User> findAll();

    /**
     * Lấy ra 10 khách hàng tiềm năng có tổng số tiền mua hàng lớn nhất
     * Bao gồm cả số lượng hàng hóa mà họ đã mua
     * Chỉ tính các đơn hàng đã hoàn thành (COMPLETE)
     * Sử dụng Native SQL với subquery để tránh duplicate totalPrice và tối ưu
     * performance
     *
     * @param pageable Pageable object để limit số lượng kết quả (page 0, size 10)
     * @param status   OrderStatus string (enum name) để filter các đơn hàng
     * @return Danh sách 10 khách hàng tiềm năng
     */
    @Query(value = """
            SELECT u.id AS id,
                   u.fullname AS fullname,
                   COUNT(o.id) AS totalOrders,
                   COALESCE(SUM(od.total_qty), 0) AS totalProducts,
                   SUM(o.total_price) AS totalSpent
            FROM users u
            JOIN orders o ON u.id = o.user_id
            LEFT JOIN (
                SELECT order_id, SUM(quantity) AS total_qty
                FROM order_details
                GROUP BY order_id
            ) od ON o.id = od.order_id
            WHERE o.status = :status
            GROUP BY u.id, u.fullname
            ORDER BY totalSpent DESC
            """, nativeQuery = true)
    List<ITopCustomerProjection> findTop10PotentialCustomers(
            Pageable pageable,
            @Param("status") String status);
    
    /**
     * Đếm số users theo trạng thái active/inactive
     * @param isActive true = active, false = inactive
     * @return Số lượng users
     */
    Long countByIsActive(boolean isActive);

    interface ITopCustomerProjection {
        Long getId();

        String getFullname();

        Long getTotalOrders();

        Long getTotalProducts();

        Double getTotalSpent();
    }
}
