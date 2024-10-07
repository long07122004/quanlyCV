package com.example.quanlycv.Service;

import com.example.quanlycv.entity.PhongBan;

import java.util.List;

public interface PhongBanIplm {
    List<PhongBan> getAll();
    void savePhongBan(PhongBan pb);
    void deletePhongBan(Integer id);
    void updatePhongBan(PhongBan pb);
    PhongBan findById(Integer id);
//    Page<PhongBan> getAllPagination(Integer pageNo);
//    List<PhongBan> searchPhongBan(String keyword);
//    Page<PhongBan> searchPhongBan(String keyword,Integer pageNo);
}
