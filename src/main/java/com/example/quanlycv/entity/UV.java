package com.example.quanlycv.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Table(name = "Hoat_Dong")
public class UV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hoat_dong_id")
    private Integer id;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "chia_se_voi")
    private String chiaSe;  // Trường chia sẻ

    @Column(name = "ngay_tao")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayTao;  // Trường ngày tạo

    @ManyToOne
    @JoinColumn(name = "loai_hoat_dong_id")
    private LoaiHoatDong loaiHoatDong;

    @ManyToOne(cascade = CascadeType.PERSIST) // hoặc CascadeType.ALL
    @JoinColumn(name = "nguoi_dung_id")
    private NguoiDung nguoiDung;
}



