package com.example.quanlycv.entity;

import jakarta.persistence.*;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Dot_Tuyen_Dung")
public class QlTuyenDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dot_tuyen_dung_id;

    @Column(name = "ma_dot")

    private String maDot;

    @Column(name = "ten_dot")

    private String tenDot;

    @Column(name = "noi_dung")

    private String noiDung;

    @Column(name = "deadline")


    @DateTimeFormat(pattern = "yyyy-MM-dd")

    private LocalDate deadline;


    @ManyToOne
    @JoinColumn(name = "nhan_vien_id")
    private NhanVien nhanVien;





}
