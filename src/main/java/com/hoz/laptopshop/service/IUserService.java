package com.hoz.laptopshop.service;

import com.hoz.laptopshop.dto.request.RegisterDTO;
import com.hoz.laptopshop.entitis.Role;
import com.hoz.laptopshop.entitis.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    public Page<User> getAllUsers(Pageable page);

    public List<User> getAllUsersByEmail(String email);

    public User handleSaveUser(User user);

    public User getUserById(long id);

    public void deleteAUser(long id);

    public User registerDTOtoUser(RegisterDTO registerDTO);

    public boolean checkEmailExist(String email);

    public User getUserByEmail(String email);

    public long countUsers();

    public long countProducts();

    public long countOrders();

    public Role getRoleByName(String name);

    User register(RegisterDTO registerDTO);
    
}
