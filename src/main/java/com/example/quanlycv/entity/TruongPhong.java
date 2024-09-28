package com.example.quanlycv.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Truong_Phong")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TruongPhong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "truong_phong_id")
     Integer id;

    @Column(name = "ma_truong_phong",  length = 100)
     String maTruongPhong;

    @Column(name = "ten_truong_phong",  length = 100)
     String tenTruongPhong;

    @Column(name = "trang_thai", columnDefinition = "BIT DEFAULT 1")
     Boolean trangThai = true;
}
