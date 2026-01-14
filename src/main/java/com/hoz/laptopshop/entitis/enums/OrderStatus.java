package com.hoz.laptopshop.entitis.enums;

public enum OrderStatus {
    PENDING, // Đơn hàng đang chờ xử lý
    SHIPPING, // Đơn hàng đang được giao
    COMPLETE, // Đơn hàng đã được giao thành công
    CANCELLED, // Đơn hàng đã bị hủy bởi người dùng hoặc hệ thống
    RETURNED   // Đơn hàng đã được trả lại sau khi giao hàng thành công
}
