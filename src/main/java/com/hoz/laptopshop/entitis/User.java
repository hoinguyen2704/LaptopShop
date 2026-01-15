package com.hoz.laptopshop.entitis;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString
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
    // @StrongPassword(message = "Password phải có tối thiểu 8 ký tự, 1 chữ hoa, 1
    // chữ thường, 1 số, 1 ký tự đặc biệt")
    private String password;
    @NotNull
    @Size(min = 3, message = "Fullname phải có tối thiểu 3 ký tự")
    @Column(name = "fullname")
    private String fullName;
    @Column(name = "address")
    private String address;

    @NotNull
    @Size(min = 10, message = "Phone phải có tối thiểu 10 ký tự")
    @Column(name = "phone")
    private String phone;
    @Column(name = "avatar")
    private String avatar;
    // many user -> to one -> role

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToOne(mappedBy = "user")
    private Cart cart;
    
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
