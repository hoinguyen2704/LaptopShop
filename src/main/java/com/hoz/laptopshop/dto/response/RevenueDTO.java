package com.hoz.laptopshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO để trả về dữ liệu doanh thu theo ngày
 * Dùng cho Area Chart hiển thị doanh thu 7 ngày gần nhất
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevenueDTO {

    /**
     * Ngày
     */
    private LocalDate date;

    /**
     * Tổng doanh thu trong ngày (chỉ tính orders COMPLETE)
     */
    private Double revenue;
}
