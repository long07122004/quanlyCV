package com.example.qlcv.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UVRespon {
    @Id
    private Integer id;

    private String tenLoaiHd;

    private String ghiChu;

    private String chiaSe;

    private Date ngayTao;

    private String hoTen;
}
