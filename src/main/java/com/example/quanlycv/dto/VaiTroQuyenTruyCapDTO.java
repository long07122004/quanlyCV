package com.example.quanlycv.dto;

import com.example.quanlycv.entity.QuyenTruyCap;
import com.example.quanlycv.entity.VaiTro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaiTroQuyenTruyCapDTO {

    private Integer id;


    private Integer vaiTroIDS;

    private Integer quyenTruyCapID;
}
