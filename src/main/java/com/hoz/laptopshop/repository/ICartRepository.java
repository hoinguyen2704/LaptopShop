package com.hoz.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoz.laptopshop.entitis.Cart;
import com.hoz.laptopshop.entitis.User;
@Repository
public interface ICartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
