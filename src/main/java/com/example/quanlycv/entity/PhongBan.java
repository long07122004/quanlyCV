package com.example.quanlycv.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Phong_Ban")
public class PhongBan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phong_ban_id")
    private Integer id;

    @Column(name = "ten_phong_ban")
    private String tenPhongBan;
}
