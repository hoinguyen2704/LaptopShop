package com.hoz.laptopshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hoz.laptopshop.dto.request.ProductCriteriaDTO;
import com.hoz.laptopshop.entitis.Product;
import com.hoz.laptopshop.repository.IProductRepository;
import com.hoz.laptopshop.service.IProductService;
import com.hoz.laptopshop.service.specification.ProductSpecs;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
// @Primary
public class ProductServiceImpl implements IProductService {
    private final IProductRepository iProductRepository;

    @Override
    public Product getAllProductNames(String name) {
        return iProductRepository.findByName(name);
    }

    @Override
    public Product createProduct(Product product) {
        return iProductRepository.save(product);
    }

    @Override
    public Page<Product> fetchProducts(Pageable page) {
        return iProductRepository.findAll(page);
    }

    @Override
    public Optional<Product> fetchProductById(long id) {
        return iProductRepository.findById(id);
    }

    @Override
    public void deleteProduct(long id) {
        iProductRepository.deleteById(id);
    }

    @Override
    public Page<Product> fetchProductsWithSpec(Pageable page, ProductCriteriaDTO productCriteriaDTO) {

        if (productCriteriaDTO.getTarget() == null
                && productCriteriaDTO.getFactory() == null
                && productCriteriaDTO.getPrice() == null) {
            return this.iProductRepository.findAll(page);
        }

        Specification<Product> combinedSpec = Specification.where((Specification<Product>) null);

        if (productCriteriaDTO.getTarget() != null && productCriteriaDTO.getTarget().isPresent()) {
            Specification<Product> currentSpecs = ProductSpecs.matchListTarget(productCriteriaDTO.getTarget().get());
            combinedSpec = combinedSpec.and(currentSpecs);
        }
        if (productCriteriaDTO.getFactory() != null && productCriteriaDTO.getFactory().isPresent()) {
            Specification<Product> currentSpecs = ProductSpecs.matchListFactory(productCriteriaDTO.getFactory().get());
            combinedSpec = combinedSpec.and(currentSpecs);
        }

        if (productCriteriaDTO.getPrice() != null && productCriteriaDTO.getPrice().isPresent()) {
            Specification<Product> currentSpecs = this.buildPriceSpecification(productCriteriaDTO.getPrice().get());
            combinedSpec = combinedSpec.and(currentSpecs);
        }

        return this.iProductRepository.findAll(combinedSpec, page);
    }

    // case 6
    @Override
    public Specification<Product> buildPriceSpecification(List<String> price) {
        Specification<Product> combinedSpec = Specification.where((Specification<Product>) null); // disconjunction
        for (String p : price) {
            double min = 0;
            double max = 0;

            // Set the appropriate min and max based on the price range string
            switch (p) {
                case "duoi-10-trieu":
                    min = 1;
                    max = 10000000;
                    break;
                case "10-15-trieu":
                    min = 10000000;
                    max = 15000000;
                    break;
                case "15-20-trieu":
                    min = 15000000;
                    max = 20000000;
                    break;
                case "tren-20-trieu":
                    min = 20000000;
                    max = 200000000;
                    break;
            }

            if (min != 0 && max != 0) {
                Specification<Product> rangeSpec = ProductSpecs.matchMultiplePrice(min, max);
                combinedSpec = combinedSpec.or(rangeSpec);
            }
        }

        return combinedSpec;
    }

    @Override
    public List<Product> fetchProducts() {
        return this.iProductRepository.findAll();
    }
}
