package com.example.quanlycv.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Vi_Tri_Cong_Viec")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViTriCongViec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vi_tri_id")
     Integer id;

    @Column(name = "ma_vi_tri",  unique = true, length = 50)
     String maViTri;

    @Column(name = "ten_vi_tri",  length = 100)
     String tenViTri;

    @ManyToOne
    @JoinColumn(name = "phong_ban_id")
     PhongBan phongBan;

    @ManyToOne
    @JoinColumn(name = "level_id")
     Level level;

    @Column(name = "trang_thai", columnDefinition = "BIT DEFAULT 1")
     Boolean trangThai = true;

    @Column(name = "ngay_tao")
     LocalDateTime ngayTao = LocalDateTime.now();

    @Column(name = "ngay_cap_nhat")
     LocalDateTime ngayCapNhat = LocalDateTime.now();
}
