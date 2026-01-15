package com.hoz.laptopshop.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hoz.laptopshop.dto.response.RevenueDTO;
import com.hoz.laptopshop.entitis.Order;
import com.hoz.laptopshop.entitis.User;
import com.hoz.laptopshop.entitis.enums.OrderStatus;

public interface IOrderService {
    Optional<Order> fetchOrderById(long id);

    void deleteOrderById(long id);

    Page<Order> fetchAllOrders(Pageable page);

    void updateOrder(Order order);

    List<Order> fetchOrderByUser(User user);

    ArrayList<OrderStatus> getNextStatus(OrderStatus status);

    public List<RevenueDTO> getLast7DaysRevenue();
}
