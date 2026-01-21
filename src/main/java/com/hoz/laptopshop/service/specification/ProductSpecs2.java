package com.hoz.laptopshop.service.specification;

import org.springframework.data.jpa.domain.Specification;

import com.hoz.laptopshop.entitis.Product;
import com.hoz.laptopshop.entitis.Product_;

public class ProductSpecs2 {
    // case 1: Lấy ra tất cả sản phẩm có giá cả tối thiểu là 1000 (vnd)
    public static Specification<Product> minPrice(double price){
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(Product_.PRICE), price);
    }
    // giá trong đoạn min max 

    public static Specification<Product> priceBetWeen(double min, double max){
        return(root, query, criterBuilder)-> criterBuilder.and(
            criterBuilder.ge(root.get(Product_.PRICE), min),
            criterBuilder.le(root.get(Product_.PRICE), max)
        );
    }

}
