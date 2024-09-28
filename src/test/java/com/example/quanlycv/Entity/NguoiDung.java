package com.example.quanlycv.Entity;

import com.example.quanlycv.entity.NhanVien;
import com.example.quanlycv.entity.VaiTro;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Nguoi_Dung")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nguoi_dung_id;


}
