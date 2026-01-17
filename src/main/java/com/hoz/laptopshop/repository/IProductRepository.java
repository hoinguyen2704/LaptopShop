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

    Long countByIsActive(boolean isActive);

    List<Product> findTop10ByIsActiveTrueOrderBySoldDesc();
}
