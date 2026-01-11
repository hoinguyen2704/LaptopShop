package com.hoz.laptopshop.service.impl;

import com.hoz.laptopshop.dto.request.RegisterDTO;
import com.hoz.laptopshop.entitis.Role;
import com.hoz.laptopshop.entitis.User;
import com.hoz.laptopshop.repository.IOrderRepository;
import com.hoz.laptopshop.repository.IProductRepository;
import com.hoz.laptopshop.repository.IRoleRepository;
import com.hoz.laptopshop.repository.IUserRepository;
import com.hoz.laptopshop.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IProductRepository productRepository;
    private final IOrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<User> getAllUsers(Pageable page) {
        return this.userRepository.findAll(page);
    }

    public List<User> getAllUsersByEmail(String email) {
        return this.userRepository.findOneByEmail(email);
    }

    public User handleSaveUser(User user) {
        User response = userRepository.save(user);
        return response;
    }

    public User getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public void deleteAUser(long id) {
        this.userRepository.deleteById(id);
    }

    public User registerDTOtoUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setPhone(registerDTO.getPhone());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;
    }


    public boolean checkEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public long countUsers() {
        return this.userRepository.count();
    }

    public long countProducts() {
        return this.productRepository.count();
    }

    public long countOrders() {
        return this.orderRepository.count();
    }
    
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }
    public User register(RegisterDTO registerDTO) {
        User user = this.registerDTOtoUser(registerDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));  
        user.setRole(this.getRoleByName("USER"));
        return handleSaveUser(user);
    }
}
