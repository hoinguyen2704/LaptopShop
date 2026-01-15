package com.hoz.laptopshop.repository;

import com.hoz.laptopshop.entitis.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
    Optional<Product> findById(long id);

    Page<Product> findAll(Pageable page);

    Page<Product> findAll(Specification<Product> spec, Pageable page);
    
    /**
     * Đếm số products đang active
     * @param isActive true = active, false = inactive
     * @return Số lượng products
     */
    Long countByIsActive(boolean isActive);
    
    /**
     * Lấy top 10 sản phẩm bán chạy nhất (đang active)
     * @return List top 10 products ordered by sold DESC
     */
    List<Product> findTop10ByIsActiveTrueOrderBySoldDesc();
}
