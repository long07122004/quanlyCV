package com.example.quanlycv.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Nguoi_Dung")
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nguoi_dung_id")
    Integer id;

    @Column(name = "ho_ten",nullable = false,  length = 100)
    String hoTen;

    @Column(name = "email",nullable = false, unique = true, length = 100)
    String email;

    @Column(name = "sdt", length = 15)
    String sdt;

    @Column(name = "password")
    String password;

    @ManyToOne
    @JoinColumn(name = "vai_tro_id")
    VaiTro vaiTro;

    @ManyToOne
    @JoinColumn(name = "nhan_vien_id")
    NhanVien nhanVien;

    @Column(name = "trang_thai", columnDefinition = "BIT DEFAULT 1")
    Boolean trangThai;






}