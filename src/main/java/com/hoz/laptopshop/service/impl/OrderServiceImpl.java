package com.hoz.laptopshop.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hoz.laptopshop.dto.response.RevenueDTO;
import com.hoz.laptopshop.entitis.Order;
import com.hoz.laptopshop.entitis.OrderDetail;
import com.hoz.laptopshop.entitis.Product;
import com.hoz.laptopshop.entitis.User;
import com.hoz.laptopshop.entitis.enums.OrderStatus;
import com.hoz.laptopshop.repository.IOrderDetailRepository;
import com.hoz.laptopshop.repository.IOrderRepository;
import com.hoz.laptopshop.repository.IProductRepository;
import com.hoz.laptopshop.service.IOrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements IOrderService {
    private final IOrderRepository orderRepository;
    private final IOrderDetailRepository orderDetailRepository;
    private final IProductRepository productRepository;

    @Override
    public Optional<Order> fetchOrderById(long id) {
        return this.orderRepository.findById(id);
    }

    @Override
    public void deleteOrderById(long id) {
        // delete order detail
        Optional<Order> orderOptional = this.fetchOrderById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            List<OrderDetail> orderDetails = order.getOrderDetails();
            for (OrderDetail orderDetail : orderDetails) {
                this.orderDetailRepository.deleteById(orderDetail.getId());
            }
        }

        this.orderRepository.deleteById(id);
    }

    @Override
    public Page<Order> fetchAllOrders(Pageable page) {
        return this.orderRepository.findAll(page);
    }

    @Override
    public void updateOrder(Order order) {
        Optional<Order> orderOptional = this.fetchOrderById(order.getId());
        if (orderOptional.isPresent()) {
            Order currentOrder = orderOptional.get();
            OrderStatus oldStatus = currentOrder.getStatus();
            OrderStatus newStatus = order.getStatus();

            // VALIDATION: Không cho phép update nếu đơn hàng đã RETURNED
            // RETURNED là trạng thái cuối cùng, không thể thay đổi
            if (oldStatus == OrderStatus.RETURNED) {
                // Có thể throw exception hoặc log warning
                // Hiện tại chỉ return không làm gì (silent fail)
                return;
            }

            // Chỉ update product nếu status thực sự thay đổi
            if (oldStatus != newStatus) {
                updateProductQuantityAndSold(currentOrder, oldStatus, newStatus);
            }

            currentOrder.setStatus(newStatus);
            this.orderRepository.save(currentOrder);
        }
    }

    /**
     * Cập nhật quantity và sold của products khi order status thay đổi
     * 
     * State Machine (từ getNextStatus):
     * - PENDING → SHIPPING, CANCELLED
     * - SHIPPING → COMPLETE, CANCELLED
     * - COMPLETE → RETURNED
     * - CANCELLED → PENDING, SHIPPING
     * - RETURNED → (không có chuyển tiếp)
     * 
     * Nghiệp vụ inventory:
     * - Checkout (PENDING): Đã giảm quantity
     * - COMPLETE: Tăng sold (cumulative metric)
     * - CANCELLED/RETURNED: Hoàn quantity, GIỮ NGUYÊN sold
     * 
     * @param order     Order cần update
     * @param oldStatus Status cũ
     * @param newStatus Status mới
     */
    private void updateProductQuantityAndSold(Order order, OrderStatus oldStatus, OrderStatus newStatus) {
        List<OrderDetail> orderDetails = order.getOrderDetails();

        for (OrderDetail orderDetail : orderDetails) {
            Product product = orderDetail.getProduct();
            long quantity = orderDetail.getQuantity();

            // ============================================
            // TRANSITIONS TỪ PENDING
            // ============================================

            // PENDING → SHIPPING: Không thay đổi quantity/sold
            if (oldStatus == OrderStatus.PENDING && newStatus == OrderStatus.SHIPPING) {
                // Không làm gì - quantity đã giảm khi checkout
            }

            // PENDING → CANCELLED: Hoàn quantity về kho
            else if (oldStatus == OrderStatus.PENDING && newStatus == OrderStatus.CANCELLED) {
                product.setQuantity(product.getQuantity() + quantity);
            }

            // ============================================
            // TRANSITIONS TỪ SHIPPING
            // ============================================

            // SHIPPING → COMPLETE: Tăng sold (đánh dấu đã bán thành công)
            else if (oldStatus == OrderStatus.SHIPPING && newStatus == OrderStatus.COMPLETE) {
                product.setSold(product.getSold() + quantity);
                // sold CHỈ TĂNG, KHÔNG BAO GIỜ GIẢM (cumulative metric)
            }

            // SHIPPING → CANCELLED: Hoàn quantity về kho
            else if (oldStatus == OrderStatus.SHIPPING && newStatus == OrderStatus.CANCELLED) {
                product.setQuantity(product.getQuantity() + quantity);
            }

            // ============================================
            // TRANSITIONS TỪ COMPLETE
            // ============================================

            // COMPLETE → RETURNED: Hoàn quantity, GIỮ NGUYÊN sold
            else if (oldStatus == OrderStatus.COMPLETE && newStatus == OrderStatus.RETURNED) {
                product.setQuantity(product.getQuantity() + quantity);
                // KHÔNG giảm sold - giữ nguyên cho báo cáo
            }

            // ============================================
            // TRANSITIONS TỪ CANCELLED (Khôi phục)
            // ============================================

            // CANCELLED → PENDING: Giảm quantity (vì đã hoàn về khi hủy)
            else if (oldStatus == OrderStatus.CANCELLED && newStatus == OrderStatus.PENDING) {
                long newQuantity = product.getQuantity() - quantity;
                product.setQuantity(newQuantity > 0 ? newQuantity : 0);
            }

            // CANCELLED → SHIPPING: Giảm quantity (vì đã hoàn về khi hủy)
            else if (oldStatus == OrderStatus.CANCELLED && newStatus == OrderStatus.SHIPPING) {
                long newQuantity = product.getQuantity() - quantity;
                product.setQuantity(newQuantity > 0 ? newQuantity : 0);
            }

            // ============================================
            // RETURNED không có transition nào hợp lệ
            // (đã được validate ở updateOrder method)
            // ============================================

            this.productRepository.save(product);
        }
    }

    @Override
    public List<Order> fetchOrderByUser(User user) {
        return this.orderRepository.findByUser(user);
    }

    @Override
    public ArrayList<OrderStatus> getNextStatus(OrderStatus status) {
        HashMap<String, ArrayList<OrderStatus>> nextStatus = new HashMap<>();
        nextStatus.put("PENDING", new ArrayList<>(List.of(OrderStatus.SHIPPING, OrderStatus.CANCELLED)));
        nextStatus.put("SHIPPING", new ArrayList<>(List.of(OrderStatus.COMPLETE, OrderStatus.CANCELLED)));
        nextStatus.put("COMPLETE", new ArrayList<>(List.of(OrderStatus.RETURNED)));
        nextStatus.put("CANCELLED", new ArrayList<>(List.of(OrderStatus.PENDING, OrderStatus.SHIPPING)));
        nextStatus.put("RETURNED", new ArrayList<>(List.of(OrderStatus.RETURNED)));
        return nextStatus.get(status.name());
    }

    @Override
    public List<RevenueDTO> getLast7DaysRevenue() {
        // Tính ngày bắt đầu (6 ngày trước + hôm nay = 7 ngày)
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6);

        // Lấy dữ liệu từ database (chỉ orders COMPLETE)
        List<IOrderRepository.IRevenueProjection> projections = orderRepository.getLast7DaysRevenue(startDate,
                OrderStatus.COMPLETE.name());

        // Convert sang Map để dễ lookup
        Map<LocalDate, Double> revenueMap = projections.stream()
                .collect(Collectors.toMap(
                        IOrderRepository.IRevenueProjection::getDate,
                        IOrderRepository.IRevenueProjection::getRevenue));

        // Tạo list 7 ngày đầy đủ, fill 0 cho ngày không có dữ liệu
        List<RevenueDTO> result = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = startDate.plusDays(i);
            Double revenue = revenueMap.getOrDefault(date, 0.0);

            result.add(RevenueDTO.builder()
                    .date(date)
                    .revenue(revenue)
                    .build());
        }

        return result;
    }
}
