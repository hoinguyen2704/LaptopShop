package com.hoz.laptopshop.entitis;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Email(message = "Email không hợp lệ", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @Column(name = "email", nullable = false)
    private String email;
    @NotNull
    @Size(min = 2, message = "Password phải có tối thiểu 2 ký tự")
    @Column(name = "password", nullable = false)
    private String password;
    @NotNull
    @Size(min = 3, message = "Fullname phải có tối thiểu 3 ký tự")
    @Column(name = "fullname")
    private String fullName;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private String phone;
    @Column(name = "avatar")
    private String avatar;
    //  many user -> to one -> role
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
