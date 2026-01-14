package com.hoz.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hoz.laptopshop.entitis.OrderDetail;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}
