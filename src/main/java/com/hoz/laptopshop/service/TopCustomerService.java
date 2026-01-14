package com.hoz.laptopshop.service;

import com.hoz.laptopshop.dto.response.TopCustomerDTO;
import com.hoz.laptopshop.entitis.enums.OrderStatus;
import com.hoz.laptopshop.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service để lấy thông tin khách hàng tiềm năng
 */
@Service
public class TopCustomerService {

    @Autowired
    private IUserRepository userRepository;

    /**
     * Lấy 10 khách hàng tiềm năng có tổng tiền mua hàng lớn nhất
     * 
     * @return Danh sách 10 khách hàng tiềm năng dưới dạng TopCustomerDTO
     */
    public List<TopCustomerDTO> getTop10PotentialCustomers() {
        // Gọi repository với Native SQL query, truyền enum name
        List<IUserRepository.ITopCustomerProjection> projections = 
            userRepository.findTop10PotentialCustomers(
                PageRequest.of(0, 10), 
                OrderStatus.COMPLETE.name()  // Native SQL cần String, không phải enum
            );
        
        // Chuyển đổi từ Projection sang DTO
        return projections.stream()
            .map(p -> new TopCustomerDTO(
                p.getId(),
                p.getFullname(),
                p.getTotalOrders(),
                p.getTotalProducts(),
                p.getTotalSpent()
            ))
            .collect(Collectors.toList());
    }
}
