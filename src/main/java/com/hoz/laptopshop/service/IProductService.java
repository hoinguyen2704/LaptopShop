package com.hoz.laptopshop.service;

import com.hoz.laptopshop.dto.request.ProductCriteriaDTO;
import com.hoz.laptopshop.entitis.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    Product getAllProductNames(String name);


    Product createProduct(Product product);

    Page<Product> fetchProducts(Pageable page);

    List<Product> fetchProducts();
    Optional<Product> fetchProductById(long id);

    void deleteProduct(long id);

    Page<Product> fetchProductsWithSpec(Pageable page, ProductCriteriaDTO productCriteriaDTO);

    Specification<Product> buildPriceSpecification(List<String> price);

}
