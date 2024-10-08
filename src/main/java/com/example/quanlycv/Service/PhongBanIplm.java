package com.example.quanlycv.Service;

import com.example.quanlycv.dto.phongBanReponse;
import com.example.quanlycv.entity.PhongBan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PhongBanIplm {
    List<PhongBan> getAll();
    Page<phongBanReponse> getAllPage(Pageable pageable);
    void savePhongBan(PhongBan pb);
    void deletePhongBan(Integer id);
    void updatePhongBan(PhongBan pb);
    PhongBan findById(Integer id);
    boolean existsByMaPhongBan(String maPhongBan);
    Page<phongBanReponse> searchPhongBan(String keyword, Integer status, Integer time, Pageable pageable);

}
