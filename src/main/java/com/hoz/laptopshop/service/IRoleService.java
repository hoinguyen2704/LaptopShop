package com.hoz.laptopshop.service;

import com.hoz.laptopshop.entitis.Role;

public interface IRoleService {
    Role getRoleByName(String name);

    Role saveRole(Role role);

    Role getRoleById(long id);

    Role createRole(Role role);
}
