package com.hoz.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoz.laptopshop.entitis.Cart;
import com.hoz.laptopshop.entitis.CartDetail;
import com.hoz.laptopshop.entitis.Product;
@Repository
public interface ICartDetailRepository extends JpaRepository<CartDetail, Long>{
    boolean existsByCartAndProduct(Cart cart, Product product);

    CartDetail findByCartAndProduct(Cart cart, Product product);
}
