package com.example.quanlycv.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Loai_Hoat_Dong")
public class LoaiHoatDong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loai_hoat_dong_id")
    private Integer id;

    @Column(name = "ten_loai")
    private String tenLoaiHd;


}
