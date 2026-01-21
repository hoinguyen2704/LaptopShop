package com.hoz.laptopshop.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

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
import com.hoz.laptopshop.service.IUserService;

import jakarta.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductServiceImpl Tests - iPhone Theme")
class ProductServiceImplTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private IUserService userService;

    @Mock
    private ICartRepository cartRepository;

    @Mock
    private ICartDetailRepository cartDetailRepository;

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IOrderDetailRepository orderDetailRepository;

    @Mock
    private HttpSession session;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product iPhone15ProMax;
    private Product iPhone15Pro;
    private User testUser;
    private Cart testCart;

    @BeforeEach
    void setUp() {
        // iPhone 15 Pro Max - flagship product
        iPhone15ProMax = new Product();
        iPhone15ProMax.setId(1L);
        iPhone15ProMax.setName("iPhone");
        iPhone15ProMax.setPrice(34990000); // 34.99 triệu VND
        iPhone15ProMax.setQuantity(50L);
        iPhone15ProMax.setSold(120L);
        iPhone15ProMax.setFactory("Apple");
        iPhone15ProMax.setTarget("Premium");

        // iPhone 15 Pro - another product for multi-product tests
        iPhone15Pro = new Product();
        iPhone15Pro.setId(2L);
        iPhone15Pro.setName("iPhone 15 Pro");
        iPhone15Pro.setPrice(28990000); // 28.99 triệu VND
        iPhone15Pro.setQuantity(100L);
        iPhone15Pro.setSold(200L);
        iPhone15Pro.setFactory("Apple");
        iPhone15Pro.setTarget("Premium");

        // Test user
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("nguyen.van.a@gmail.com");
        testUser.setFullName("Nguyễn Văn A");

        // Test cart
        testCart = new Cart();
        testCart.setId(1L);
        testCart.setUser(testUser);
        testCart.setSum(0);
    }

    // ==================== CRUD Tests ====================
    @Nested
    @DisplayName("CRUD Operations - iPhone Products")
    class CrudTests {

        @Test
        @DisplayName("Tìm iPhone")
        void testFindIPhoneByName() {
            when(productRepository.findByName("iPhone")).thenReturn(iPhone15ProMax);

            Product result = productService.getAllProductNames("iPhone");
            System.out.println("result với tên iPhone: " + result);
            assertEquals("iPhone", result.getName());
            assertEquals(34990000, result.getPrice());
            assertEquals("Apple", result.getFactory());
            verify(productRepository).findByName("iPhone");
        }

        @Test
        @DisplayName("Thêm iPhone mới vào database")
        void testCreateNewIPhone() {
            Product iPhone16 = new Product();
            iPhone16.setName("iPhone 16");
            iPhone16.setPrice(25990000);
            iPhone16.setFactory("Apple");

            when(productRepository.save(iPhone16)).thenReturn(iPhone16);

            Product result = productService.createProduct(iPhone16);

            assertNotNull(result);
            assertEquals("iPhone 16", result.getName());
            verify(productRepository).save(iPhone16);
        }

        @Test
        @DisplayName("Lấy danh sách iPhone với phân trang")
        void testFetchIPhonesPaginated() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<Product> iphonePage = new PageImpl<>(Arrays.asList(iPhone15ProMax, iPhone15Pro));
            when(productRepository.findAll(pageable)).thenReturn(iphonePage);

            Page<Product> result = productService.fetchProducts(pageable);

            assertNotNull(result);
            assertEquals(2, result.getContent().size());
            assertEquals("iPhone 15 Pro Max", result.getContent().get(0).getName());
            assertEquals("iPhone 15 Pro", result.getContent().get(1).getName());
            verify(productRepository).findAll(pageable);
        }

        @Test
        @DisplayName("Lấy tất cả iPhone")
        void testFetchAllIPhones() {
            when(productRepository.findAll()).thenReturn(Arrays.asList(iPhone15ProMax, iPhone15Pro));

            List<Product> result = productService.fetchProducts();

            assertNotNull(result);
            assertEquals(2, result.size());
            verify(productRepository).findAll();
        }

        @Test
        @DisplayName("Tìm iPhone theo ID - thành công")
        void testFindIPhoneByIdSuccess() {
            when(productRepository.findById(1L)).thenReturn(Optional.of(iPhone15ProMax));

            Optional<Product> result = productService.fetchProductById(1L);

            assertTrue(result.isPresent());
            assertEquals("iPhone 15 Pro Max", result.get().getName());
            assertEquals(34990000, result.get().getPrice());
        }

        @Test
        @DisplayName("Tìm iPhone theo ID - không tìm thấy")
        void testFindIPhoneByIdNotFound() {
            when(productRepository.findById(999L)).thenReturn(Optional.empty());

            Optional<Product> result = productService.fetchProductById(999L);

            assertFalse(result.isPresent());
        }

        @Test
        @DisplayName("Xóa iPhone khỏi database")
        void testDeleteIPhone() {
            doNothing().when(productRepository).deleteById(1L);

            productService.deleteProduct(1L);

            verify(productRepository).deleteById(1L);
        }
    }

    // ==================== Price Specification Tests ====================
    @Nested
    @DisplayName("Lọc theo khoảng giá")
    class PriceSpecificationTests {

        @Test
        @DisplayName("iPhone dưới 10 triệu (không có)")
        void testPriceUnder10Million() {
            Specification<Product> result = productService.buildPriceSpecification(
                    Arrays.asList("duoi-10-trieu"));
            assertNotNull(result);
        }

        @Test
        @DisplayName("iPhone từ 10-15 triệu")
        void testPrice10To15Million() {
            Specification<Product> result = productService.buildPriceSpecification(
                    Arrays.asList("10-15-trieu"));
            assertNotNull(result);
        }

        @Test
        @DisplayName("iPhone từ 15-20 triệu")
        void testPrice15To20Million() {
            Specification<Product> result = productService.buildPriceSpecification(
                    Arrays.asList("15-20-trieu"));
            assertNotNull(result);
        }

        @Test
        @DisplayName("iPhone trên 20 triệu (iPhone 15 Pro Max)")
        void testPriceOver20Million() {
            Specification<Product> result = productService.buildPriceSpecification(
                    Arrays.asList("tren-20-trieu"));
            assertNotNull(result);
        }

        @Test
        @DisplayName("Nhiều khoảng giá cùng lúc")
        void testMultiplePriceRanges() {
            Specification<Product> result = productService.buildPriceSpecification(
                    Arrays.asList("15-20-trieu", "tren-20-trieu"));
            assertNotNull(result);
        }

        @Test
        @DisplayName("Không có khoảng giá")
        void testEmptyPriceList() {
            Specification<Product> result = productService.buildPriceSpecification(new ArrayList<>());
            assertNotNull(result);
        }

        @Test
        @DisplayName("Khoảng giá không hợp lệ")
        void testInvalidPriceRange() {
            Specification<Product> result = productService.buildPriceSpecification(
                    Arrays.asList("invalid-range"));
            assertNotNull(result);
        }
    }

    // ==================== Filter with Specification Tests ====================
    @Nested
    @DisplayName("Lọc iPhone với nhiều tiêu chí")
    class FetchProductsWithSpecTests {

        @Test
        @DisplayName("Không có tiêu chí - trả về tất cả iPhone")
        void testNoCriteria() {
            Pageable pageable = PageRequest.of(0, 10);
            ProductCriteriaDTO criteria = new ProductCriteriaDTO();
            Page<Product> page = new PageImpl<>(Arrays.asList(iPhone15ProMax, iPhone15Pro));

            when(productRepository.findAll(pageable)).thenReturn(page);

            Page<Product> result = productService.fetchProductsWithSpec(pageable, criteria);

            assertEquals(2, result.getContent().size());
            verify(productRepository).findAll(pageable);
        }

        @Test
        @DisplayName("Lọc theo hãng Apple")
        @SuppressWarnings("unchecked")
        void testFilterByApple() {
            Pageable pageable = PageRequest.of(0, 10);
            ProductCriteriaDTO criteria = ProductCriteriaDTO.builder()
                    .factory(Optional.of(Arrays.asList("Apple")))
                    .build();
            Page<Product> page = new PageImpl<>(Arrays.asList(iPhone15ProMax, iPhone15Pro));

            when(productRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

            Page<Product> result = productService.fetchProductsWithSpec(pageable, criteria);

            assertEquals(2, result.getContent().size());
            verify(productRepository).findAll(any(Specification.class), eq(pageable));
        }

        @Test
        @DisplayName("Lọc theo đối tượng Premium")
        @SuppressWarnings("unchecked")
        void testFilterByPremiumTarget() {
            Pageable pageable = PageRequest.of(0, 10);
            ProductCriteriaDTO criteria = ProductCriteriaDTO.builder()
                    .target(Optional.of(Arrays.asList("Premium")))
                    .build();
            Page<Product> page = new PageImpl<>(Arrays.asList(iPhone15ProMax, iPhone15Pro));

            when(productRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

            Page<Product> result = productService.fetchProductsWithSpec(pageable, criteria);

            assertEquals(2, result.getContent().size());
        }

        @Test
        @DisplayName("Lọc iPhone trên 20 triệu")
        @SuppressWarnings("unchecked")
        void testFilterByPriceOver20M() {
            Pageable pageable = PageRequest.of(0, 10);
            ProductCriteriaDTO criteria = ProductCriteriaDTO.builder()
                    .price(Optional.of(Arrays.asList("tren-20-trieu")))
                    .build();
            Page<Product> page = new PageImpl<>(Arrays.asList(iPhone15ProMax)); // chỉ Pro Max

            when(productRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

            Page<Product> result = productService.fetchProductsWithSpec(pageable, criteria);

            assertEquals(1, result.getContent().size());
            assertEquals("iPhone 15 Pro Max", result.getContent().get(0).getName());
        }

        @Test
        @DisplayName("Lọc kết hợp: Apple + Premium + trên 20 triệu")
        @SuppressWarnings("unchecked")
        void testFilterCombined() {
            Pageable pageable = PageRequest.of(0, 10);
            ProductCriteriaDTO criteria = ProductCriteriaDTO.builder()
                    .factory(Optional.of(Arrays.asList("Apple")))
                    .target(Optional.of(Arrays.asList("Premium")))
                    .price(Optional.of(Arrays.asList("tren-20-trieu")))
                    .build();
            Page<Product> page = new PageImpl<>(Arrays.asList(iPhone15ProMax));

            when(productRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

            Page<Product> result = productService.fetchProductsWithSpec(pageable, criteria);

            assertEquals(1, result.getContent().size());
        }
    }

    // ==================== Cart Tests ====================
    @Nested
    @DisplayName("Giỏ hàng - Thêm iPhone")
    class CartTests {

        @Test
        @DisplayName("Lấy giỏ hàng của user")
        void testFetchCartByUser() {
            when(cartRepository.findByUser(testUser)).thenReturn(testCart);

            Cart result = productService.fetchByUser(testUser);

            assertNotNull(result);
            assertEquals(testCart.getId(), result.getId());
        }

        @Test
        @DisplayName("Thêm iPhone vào giỏ hàng mới")
        void testAddIPhoneToNewCart() {
            when(userService.getUserByEmail("nguyen.van.a@gmail.com")).thenReturn(testUser);
            when(cartRepository.findByUser(testUser)).thenReturn(null);
            when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> {
                Cart cart = invocation.getArgument(0);
                cart.setId(1L);
                return cart;
            });
            when(productRepository.findById(1L)).thenReturn(Optional.of(iPhone15ProMax));
            when(cartDetailRepository.findByCartAndProduct(any(Cart.class), eq(iPhone15ProMax))).thenReturn(null);

            productService.handleAddProductToCart("nguyen.van.a@gmail.com", 1L, session, 1L);

            verify(cartRepository, times(2)).save(any(Cart.class));
            verify(cartDetailRepository).save(any(CartDetail.class));
            verify(session).setAttribute("sum", 1);
        }

        @Test
        @DisplayName("Thêm iPhone vào giỏ hàng có sẵn")
        void testAddIPhoneToExistingCart() {
            testCart.setSum(1); // Đã có 1 sản phẩm
            when(userService.getUserByEmail("nguyen.van.a@gmail.com")).thenReturn(testUser);
            when(cartRepository.findByUser(testUser)).thenReturn(testCart);
            when(productRepository.findById(2L)).thenReturn(Optional.of(iPhone15Pro));
            when(cartDetailRepository.findByCartAndProduct(testCart, iPhone15Pro)).thenReturn(null);

            productService.handleAddProductToCart("nguyen.van.a@gmail.com", 2L, session, 1L);

            verify(cartRepository).save(testCart);
            verify(session).setAttribute("sum", 2);
        }

        @Test
        @DisplayName("Tăng số lượng iPhone đã có trong giỏ")
        void testIncreaseIPhoneQuantity() {
            CartDetail existingDetail = new CartDetail();
            existingDetail.setId(1L);
            existingDetail.setCart(testCart);
            existingDetail.setProduct(iPhone15ProMax);
            existingDetail.setQuantity(1L); // Đã có 1 iPhone

            when(userService.getUserByEmail("nguyen.van.a@gmail.com")).thenReturn(testUser);
            when(cartRepository.findByUser(testUser)).thenReturn(testCart);
            when(productRepository.findById(1L)).thenReturn(Optional.of(iPhone15ProMax));
            when(cartDetailRepository.findByCartAndProduct(testCart, iPhone15ProMax)).thenReturn(existingDetail);

            productService.handleAddProductToCart("nguyen.van.a@gmail.com", 1L, session, 2L);

            assertEquals(3L, existingDetail.getQuantity()); // 1 + 2 = 3
            verify(cartDetailRepository).save(existingDetail);
        }

        @Test
        @DisplayName("User không tồn tại - không thêm vào giỏ")
        void testAddToCartUserNotFound() {
            when(userService.getUserByEmail("khongtontai@gmail.com")).thenReturn(null);

            productService.handleAddProductToCart("khongtontai@gmail.com", 1L, session, 1L);

            verify(cartRepository, never()).save(any());
            verify(cartDetailRepository, never()).save(any());
        }

        @Test
        @DisplayName("Xóa iPhone khỏi giỏ - còn sản phẩm khác")
        void testRemoveIPhoneFromCart() {
            testCart.setSum(2); // Có 2 sản phẩm
            CartDetail cartDetail = new CartDetail();
            cartDetail.setId(1L);
            cartDetail.setCart(testCart);
            cartDetail.setProduct(iPhone15ProMax);

            when(cartDetailRepository.findById(1L)).thenReturn(Optional.of(cartDetail));

            productService.handleRemoveCartDetail(1L, session);

            verify(cartDetailRepository).deleteById(1L);
            verify(cartRepository).save(testCart);
            assertEquals(1, testCart.getSum());
            verify(session).setAttribute("sum", 1);
        }

        @Test
        @DisplayName("Xóa iPhone cuối cùng - xóa luôn giỏ hàng")
        void testRemoveLastIPhoneDeletesCart() {
            testCart.setSum(1);
            testUser.setCart(testCart);
            testCart.setUser(testUser);

            CartDetail cartDetail = new CartDetail();
            cartDetail.setId(1L);
            cartDetail.setCart(testCart);
            cartDetail.setProduct(iPhone15ProMax);

            when(cartDetailRepository.findById(1L)).thenReturn(Optional.of(cartDetail));

            productService.handleRemoveCartDetail(1L, session);

            verify(cartDetailRepository).deleteById(1L);
            verify(cartRepository).deleteById(testCart.getId());
            verify(session).setAttribute("sum", 0);
            assertNull(testUser.getCart());
        }

        @Test
        @DisplayName("Cập nhật số lượng trước khi checkout")
        void testUpdateQuantityBeforeCheckout() {
            CartDetail detail1 = new CartDetail();
            detail1.setId(1L);
            detail1.setQuantity(3L); // Muốn mua 3 cái

            CartDetail existingDetail = new CartDetail();
            existingDetail.setId(1L);
            existingDetail.setQuantity(1L);

            when(cartDetailRepository.findById(1L)).thenReturn(Optional.of(existingDetail));

            productService.handleUpdateCartBeforeCheckout(Arrays.asList(detail1));

            assertEquals(3L, existingDetail.getQuantity());
            verify(cartDetailRepository).save(existingDetail);
        }
    }

    // ==================== Order Tests ====================
    @Nested
    @DisplayName("Đặt hàng iPhone")
    class OrderTests {

        @Test
        @DisplayName("Đặt hàng thành công - 2 iPhone")
        void testPlaceOrderSuccess() {
            testCart.setSum(2);
            testUser.setCart(testCart);

            // iPhone 15 Pro Max x2
            CartDetail detail1 = new CartDetail();
            detail1.setId(1L);
            detail1.setCart(testCart);
            detail1.setProduct(iPhone15ProMax);
            detail1.setPrice(34990000);
            detail1.setQuantity(2L);

            // iPhone 15 Pro x1
            CartDetail detail2 = new CartDetail();
            detail2.setId(2L);
            detail2.setCart(testCart);
            detail2.setProduct(iPhone15Pro);
            detail2.setPrice(28990000);
            detail2.setQuantity(1L);

            testCart.setCartDetails(Arrays.asList(detail1, detail2));

            when(cartRepository.findByUser(testUser)).thenReturn(testCart);
            when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
                Order order = invocation.getArgument(0);
                order.setId(1L);
                return order;
            });

            productService.handlePlaceOrder(testUser, session,
                    "Nguyễn Văn A",
                    "123 Nguyễn Huệ, Q1, TP.HCM",
                    "0909123456");

            ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
            verify(orderRepository).save(orderCaptor.capture());
            Order savedOrder = orderCaptor.getValue();

            assertEquals("Nguyễn Văn A", savedOrder.getReceiverName());
            assertEquals("123 Nguyễn Huệ, Q1, TP.HCM", savedOrder.getReceiverAddress());
            assertEquals("0909123456", savedOrder.getReceiverPhone());
            assertEquals(OrderStatus.PENDING, savedOrder.getStatus());
            // Total: 34.99M * 2 + 28.99M * 1 = 98.97M
            assertEquals(98970000, savedOrder.getTotalPrice());

            verify(orderDetailRepository, times(2)).save(any(OrderDetail.class));
            verify(productRepository, times(2)).save(any(Product.class));
            verify(session).setAttribute("sum", 0);
        }

        @Test
        @DisplayName("Đặt hàng - User chưa có giỏ hàng")
        void testPlaceOrderNoCart() {
            when(cartRepository.findByUser(testUser)).thenReturn(null);

            productService.handlePlaceOrder(testUser, session, "Tên", "Địa chỉ", "0909123456");

            verify(orderRepository, never()).save(any());
        }

        @Test
        @DisplayName("Đặt hàng - Giỏ hàng trống")
        void testPlaceOrderEmptyCart() {
            testCart.setCartDetails(null);
            when(cartRepository.findByUser(testUser)).thenReturn(testCart);

            productService.handlePlaceOrder(testUser, session, "Tên", "Địa chỉ", "0909123456");

            verify(orderRepository, never()).save(any());
        }

        @Test
        @DisplayName("Đặt hàng - Số lượng vượt quá tồn kho")
        void testPlaceOrderExceedsStock() {
            testCart.setSum(1);
            testUser.setCart(testCart);
            iPhone15ProMax.setQuantity(2L); // Chỉ còn 2 cái

            CartDetail detail = new CartDetail();
            detail.setId(1L);
            detail.setCart(testCart);
            detail.setProduct(iPhone15ProMax);
            detail.setPrice(34990000);
            detail.setQuantity(5L); // Đặt 5 cái

            testCart.setCartDetails(Arrays.asList(detail));

            when(cartRepository.findByUser(testUser)).thenReturn(testCart);
            when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
                Order order = invocation.getArgument(0);
                order.setId(1L);
                return order;
            });

            productService.handlePlaceOrder(testUser, session, "Tên", "Địa chỉ", "0909123456");

            // Kiểm tra số lượng không âm
            ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
            verify(productRepository).save(productCaptor.capture());
            assertEquals(0L, productCaptor.getValue().getQuantity());
        }
    }
}
