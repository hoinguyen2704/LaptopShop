package com.hoz.laptopshop.entitis;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @NotEmpty(message = "Tên sản phẩm không được để trống")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @DecimalMin(value = "0", inclusive = false, message = "Price phải lớn hơn 0")
    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "image")
    private String image;

    @NotNull
    @NotEmpty(message = "detailDesc không được để trống")
    @Column(name = "detaiDesc", columnDefinition = "MEDIUMTEXT")
    private String detailDesc;
    @NotNull
    @NotEmpty(message = "shortDesc không được để trống")
    @Column(name = "shortDesc", nullable = false)
    private String shortDesc;
    @NotNull
    @Min(value = 1, message = "Số lượng cần lớn hơn hoặc bằng 1")
    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Column(name = "sold")
    private long sold;
    @Column(name = "factory")
    private String factory;
    @Column(name = "target")
    private String target;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

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
