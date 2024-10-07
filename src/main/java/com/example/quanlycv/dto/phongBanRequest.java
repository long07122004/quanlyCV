package com.example.quanlycv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class phongBanRequest {
    private String maPhongBan;

    private String tenPhongBan;


    private Boolean trangThai;

//    private Instant ngayTao;
//    private Instant ngayCapNhat;
}
