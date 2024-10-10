package com.example.quanlycv.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;

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
    private Integer nhanVienId;

    @NotEmpty(message = "Vui lòng nhập họ tên")
    @Column(name = "ho_ten")
    private String hoTen;

    @NotEmpty(message = "Vui lòng nhập mã nhân viên")
    @Column(name = "ma")
    private String ma;

    @Email(message = "Email không hợp lệ")
    @NotEmpty(message = "Vui lòng nhập Email")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Vui lòng nhập số điện thoại")
    @Column(name = "sdt")
    private String sdt;

    @ManyToOne
    @JoinColumn(name = "vi_tri_id")
    private ViTriCongViec viTri;

    @ManyToOne
    @JoinColumn(name = "vai_tro_id")
    private VaiTro vaiTro;

    @Column(name = "ngay_tao")
    private Date ngay_tao;

    @Column(name = "ngay_cap_nhat")
    private Date ngay_cap_nhat;

    @ManyToOne
    @JoinColumn(name = "truong_phong_id")
    private TruongPhong truongPhong;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "phong_ban_id")
    private PhongBan phongBan;



    @Column(name = "TrangThai")
    private Boolean trang_thai;
}
