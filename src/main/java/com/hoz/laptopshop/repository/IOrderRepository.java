package com.hoz.laptopshop.repository;

import com.hoz.laptopshop.entitis.Order;
import com.hoz.laptopshop.entitis.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long>{
    Order findById(long id);
    List<Order> findByUser(User user);
}
