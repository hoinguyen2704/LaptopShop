package com.hoz.laptopshop.repository;

import com.hoz.laptopshop.entitis.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByFullname(String fullname);
    User findById(long id);
    boolean existsByEmail(String email);
    void deleteById (Long id);
    List<User> findOneByEmail(String email);

    List<User> findAll();

}
