package com.example.quanlycv.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "Vi_Tri_Cong_Viec")
public class ViTriCongViec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vi_tri_id")
    private Integer id;

    @Column(name = "ma_vi_tri")
    private String maViTri;

    @Column(name = "ten_vi_tri")
    private String tenViTri;

    @ManyToOne
    @JoinColumn(name = "phong_ban_id")
    private PhongBan phongBan;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    @Column(name = "trang_thai")
    private Boolean trangThai;

    @Column(name = "ngay_tao")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayTao;

    @Column(name = "ngay_cap_nhat")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayCapNhat;

    public String getTrangThaiNe(){
        if(this.trangThai == true){
            return "Đang hoạt động";
        }else{
            return "Không hoạt động";
        }
    }

}
