package com.hoz.laptopshop.entitis;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
@ToString
public class Role {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 255)
    private String Description;
    // role - one => many - users
    // lưu nhiều file =>  ctrl + k . press 's'
    @OneToMany(mappedBy = "role")
    private List<User> users;
}
