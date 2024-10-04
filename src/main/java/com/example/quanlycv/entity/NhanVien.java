package com.example.quanlycv.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Nhan_Vien")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nhan_vien_id")
     Integer id;

    @Column(name = "ho_ten", nullable = false, length = 100)
     String hoTen;

    @Column(name = "email", nullable = false, unique = true, length = 100)
     String email;

    @Column(name = "sdt", nullable = false, unique = true, length = 15)
     String sdt;

    @ManyToOne
    @JoinColumn(name = "vi_tri_id")
     ViTriCongViec viTriCongViec;

    @ManyToOne
    @JoinColumn(name = "vai_tro_id")
     VaiTro vaiTro;

    @ManyToOne
    @JoinColumn(name = "phong_ban_id")
    private PhongBan phongBan;

    @ManyToOne
    @JoinColumn(name = "truong_phong_id")
    private TruongPhong truongPhong;

    @Column(name = "qr_code", length = 255)
    private String qrCode;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao = LocalDateTime.now();

    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat = LocalDateTime.now();
}
