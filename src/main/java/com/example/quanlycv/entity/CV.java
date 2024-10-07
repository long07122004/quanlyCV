package com.example.quanlycv.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
@Entity
@Table(name = "CV")
public class CV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cv_id;

    @Column(name = "apply_datetime")
    private Date applyDateTime;

    @Column(name = "ho_ten")
    private String hoTen;

    @Column(name = "gioi_tinh")
    private String gioiTinh;

    @Column(name = "email")
    private String email;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "thanh_pho")
    private String thanhPho;

    @Column(name = "ten_cong_viec")
    private String tenCongViec;

    @Column(name = "so_nam_kinh_nghiem")
    private Integer soNamKinhNghiem;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "link_cv")
    private String linkCV;

    @Column(name = "nguon_tuyen_dung")
    private String nguonTuyenDung;

    @Column(name = "cv_status_id")
    private Integer cvStatusId;

    @Column(name = "nhan_su_quan_ly_id")
    private Integer nhanSuQuanLyId;
}
