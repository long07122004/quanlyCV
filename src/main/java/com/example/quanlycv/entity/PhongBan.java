package com.example.quanlycv.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "Phong_Ban")
public class PhongBan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phong_ban_id", nullable = false)
    private Integer id;

    @NotBlank(message = "Mã phòng ban không được để trống")
    @Size(max = 50, message = "Mã phòng ban không được quá 50 ký tự")
    @Column(name = "ma_phong_ban", nullable = false, length = 50)
    private String maPhongBan;

    @NotBlank(message = "Tên phòng ban không được để trống")
    @Size(max = 100, message = "Tên phòng ban không được quá 100 ký tự")
    @Column(name = "ten_phong_ban", nullable = false, length = 100)
    private String tenPhongBan;

    @ColumnDefault("1")
    @Column(name = "trang_thai")
    private Boolean trangThai;

    @ColumnDefault("getdate()")
    @Column(name = "ngay_tao")
    private Instant ngayTao;

    @ColumnDefault("getdate()")
    @Column(name = "ngay_cap_nhat")
    private Instant ngayCapNhat;

}