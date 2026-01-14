package com.hoz.laptopshop.service;

import com.hoz.laptopshop.dto.response.RevenueDTO;
import com.hoz.laptopshop.entitis.enums.OrderStatus;
import com.hoz.laptopshop.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service để lấy dữ liệu doanh thu
 */
@Service
@RequiredArgsConstructor
public class RevenueService {

    private final IOrderRepository orderRepository;

    /**
     * Lấy doanh thu 7 ngày gần nhất (bao gồm hôm nay)
     * Đảm bảo có đầy đủ 7 ngày, ngày nào không có dữ liệu sẽ fill = 0
     * 
     * @return Danh sách 7 ngày với doanh thu tương ứng
     */
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
