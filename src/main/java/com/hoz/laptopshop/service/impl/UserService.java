package com.hoz.laptopshop.service.impl;

import com.hoz.laptopshop.dto.request.RegisterDTO;
import com.hoz.laptopshop.dto.response.TopCustomerDTO;
import com.hoz.laptopshop.entitis.Role;
import com.hoz.laptopshop.entitis.User;
import com.hoz.laptopshop.entitis.enums.OrderStatus;
import com.hoz.laptopshop.repository.IOrderRepository;
import com.hoz.laptopshop.repository.IProductRepository;
import com.hoz.laptopshop.repository.IRoleRepository;
import com.hoz.laptopshop.repository.IUserRepository;
import com.hoz.laptopshop.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IProductRepository productRepository;
    private final IOrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<User> getAllUsers(Pageable page) {
        return this.userRepository.findAll(page);
    }

    @Override
    public List<User> getAllUsersByEmail(String email) {
        return this.userRepository.findOneByEmail(email);
    }

    @Override
    public User handleSaveUser(User user) {
        User response = userRepository.save(user);
        return response;
    }

    @Override
    public User getUserById(long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public void deleteAUser(long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public User registerDTOtoUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setPhone(registerDTO.getPhone());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;
    }

    @Override
    public boolean checkEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public long countUsers() {
        return this.userRepository.count();
    }

    @Override
    public long countProducts() {
        return this.productRepository.count();
    }

    @Override
    public long countOrders() {
        return this.orderRepository.count();
    }
    
    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public User register(RegisterDTO registerDTO) {
        User user = this.registerDTOtoUser(registerDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(this.getRoleByName("USER"));
        return handleSaveUser(user);
    }
    @Override
    public List<TopCustomerDTO> getTop10PotentialCustomers() {
        List<IUserRepository.ITopCustomerProjection> projections = 
            userRepository.findTop10PotentialCustomers(
                PageRequest.of(0, 10), 
                OrderStatus.COMPLETE.name()
            );
        
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
