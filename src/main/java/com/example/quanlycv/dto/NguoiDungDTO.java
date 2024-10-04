package com.example.quanlycv.dto;

import com.example.quanlycv.entity.NhanVien;
import com.example.quanlycv.entity.VaiTro;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NguoiDungDTO {

    Integer id;


    String hoTen;


    String email;


    String sdt;


    String password;


    Integer vaiTroId;


    Integer nhanVienId;


    Boolean trangThai;


}
