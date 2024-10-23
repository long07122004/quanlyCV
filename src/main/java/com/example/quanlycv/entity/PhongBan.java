package com.example.quanlycv.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Phong_Ban")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class PhongBan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phong_ban_id")
     Integer id;

    @Column(name = "ma_phong_ban",  unique = true, length = 50)
     String maPhongBan;

    @Column(name = "ten_phong_ban",  length = 100)
     String tenPhongBan;

    @Column(name = "trang_thai", columnDefinition = "BIT DEFAULT 1")
     Boolean trangThai = true;

    @Column(name = "ngay_tao")
     LocalDateTime ngayTao = LocalDateTime.now();

    @Column(name = "ngay_cap_nhat")
     LocalDateTime ngayCapNhat = LocalDateTime.now();
}
