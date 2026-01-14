package com.hoz.laptopshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO để trả về thông tin khách hàng tiềm năng
 * Dựa trên tổng số tiền mua hàng và số lượng sản phẩm đã mua
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TopCustomerDTO {
    /**
     * ID của khách hàng
     */
    private Long id;

    /**
     * Họ và tên của khách hàng
     */
    private String fullname;

    /**
     * Tổng số đơn hàng
     */
    private Long totalOrders;

    /**
     * Tổng số lượng sản phẩm đã mua
     */
    private Long totalProducts;

    /**
     * Tổng số tiền đã chi tiêu
     */
    private Double totalSpent;
}
