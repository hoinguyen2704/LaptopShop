package com.hoz.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoz.laptopshop.entitis.Category;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {

}
