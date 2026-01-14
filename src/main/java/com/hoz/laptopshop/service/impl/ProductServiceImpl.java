package com.hoz.laptopshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoz.laptopshop.dto.request.ProductCriteriaDTO;
import com.hoz.laptopshop.entitis.Cart;
import com.hoz.laptopshop.entitis.CartDetail;
import com.hoz.laptopshop.entitis.Order;
import com.hoz.laptopshop.entitis.OrderDetail;
import com.hoz.laptopshop.entitis.Product;
import com.hoz.laptopshop.entitis.User;
import com.hoz.laptopshop.entitis.enums.OrderStatus;
import com.hoz.laptopshop.repository.ICartDetailRepository;
import com.hoz.laptopshop.repository.ICartRepository;
import com.hoz.laptopshop.repository.IOrderDetailRepository;
import com.hoz.laptopshop.repository.IOrderRepository;
import com.hoz.laptopshop.repository.IProductRepository;
import com.hoz.laptopshop.service.IProductService;
import com.hoz.laptopshop.service.IUserService;
import com.hoz.laptopshop.service.specification.ProductSpecs;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
// @Primary
public class ProductServiceImpl implements IProductService {
    private final IProductRepository iProductRepository;
    private final IUserService userService;
    private final ICartRepository iCartRepository;
    private final ICartDetailRepository iCartDetailRepository;
    private final IOrderRepository iOrderRepository;
    private final IOrderDetailRepository iOrderDetailRepository;
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

    @Transactional
    public void handleAddProductToCart(String email, long productId, HttpSession session, long quantity) {

        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // check user đã có Cart chưa ? nếu chưa -> tạo mới
            Cart cart = this.iCartRepository.findByUser(user);

            if (cart == null) {
                // tạo mới cart
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);

                cart = this.iCartRepository.save(otherCart);
            }

            // save cart_detail
            // tìm product by id

            Optional<Product> productOptional = this.iProductRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product realProduct = productOptional.get();

                // check sản phẩm đã từng được thêm vào giỏ hàng trước đây chưa ?
                CartDetail oldDetail = this.iCartDetailRepository.findByCartAndProduct(cart, realProduct);
                //
                if (oldDetail == null) {
                    CartDetail cd = new CartDetail();
                    cd.setCart(cart);
                    cd.setProduct(realProduct);
                    cd.setPrice(realProduct.getPrice());
                    cd.setQuantity(quantity);
                    this.iCartDetailRepository.save(cd);

                    // update cart (sum);
                    int s = cart.getSum() + 1;
                    cart.setSum(s);
                    this.iCartRepository.save(cart);
                    session.setAttribute("sum", s);
                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + quantity);
                    this.iCartDetailRepository.save(oldDetail);
                }
            }
        }
    }

    @Override
    public Cart fetchByUser(User user) {
        return this.iCartRepository.findByUser(user);
    }

    @Override
    @Transactional
    public void handleRemoveCartDetail(long cartDetailId, HttpSession session) {
        Optional<CartDetail> cartDetailOptional = this.iCartDetailRepository.findById(cartDetailId);
        if (cartDetailOptional.isPresent()) {
            CartDetail cartDetail = cartDetailOptional.get();

            Cart currentCart = cartDetail.getCart();
            // delete cart-detail
            this.iCartDetailRepository.deleteById(cartDetailId);

            // update cart
            if (currentCart.getSum() > 1) {
                // update current cart
                int s = currentCart.getSum() - 1;
                currentCart.setSum(s);
                session.setAttribute("sum", s);
                this.iCartRepository.save(currentCart);
            } else {
                // delete cart (sum = 1)
                // Cần set user.cart = null trước khi xóa cart để tránh lỗi Hibernate
                User user = currentCart.getUser();
                user.setCart(null);
                this.iCartRepository.deleteById(currentCart.getId());
                session.setAttribute("sum", 0);
            }
        }
    }

    @Override
    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.iCartDetailRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.iCartDetailRepository.save(currentCartDetail);
            }
        }
    }

    @Override
    @Transactional
    public void handlePlaceOrder(
            User user, HttpSession session,
            String receiverName, String receiverAddress, String receiverPhone) {

        // step 1: get cart by user
        Cart cart = this.iCartRepository.findByUser(user);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();

            if (cartDetails != null) {

                // create order
                Order order = new Order();
                order.setUser(user);
                order.setReceiverName(receiverName);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setStatus(OrderStatus.PENDING);

                double sum = 0;
                for (CartDetail cd : cartDetails) {
                    sum += cd.getPrice() * cd.getQuantity();
                }
                order.setTotalPrice(sum);
                order = this.iOrderRepository.save(order);

                // create orderDetail

                for (CartDetail cd : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setPrice(cd.getPrice());
                    orderDetail.setQuantity(cd.getQuantity());
                    this.iOrderDetailRepository.save(orderDetail);
                }

                // step 2: delete cart_detail and cart
                for (CartDetail cd : cartDetails) {
                    Product product = cd.getProduct();
                    long newQuantity = product.getQuantity() - cd.getQuantity();
                    product.setQuantity(newQuantity > 0 ? newQuantity : 0);
                    // Không tăng sold ở đây, sẽ tăng khi order status chuyển sang COMPLETE
                    // product.setSold(product.getSold() + cd.getQuantity());
                    this.iProductRepository.save(product);
                    this.iCartDetailRepository.deleteById(cd.getId());
                }

                // Set user.cart = null trước khi xóa để tránh lỗi Hibernate
                User cartUser = cart.getUser();
                cartUser.setCart(null);
                this.iCartRepository.deleteById(cart.getId());

                // step 3 : update session
                session.setAttribute("sum", 0);
            }
        }

    }
}
