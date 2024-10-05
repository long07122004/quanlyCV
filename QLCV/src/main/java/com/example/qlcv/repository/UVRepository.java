package com.example.qlcv.repository;

import com.example.qlcv.entities.UV;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UVRepository extends JpaRepository<UV, Integer> {
    Page<UV> findByNguoiDung_HoTenContaining(String searchUser, Pageable pageable);
    Page<UV> findByLoaiHoatDong_TenLoaiHdContaining(String filterType, Pageable pageable);
    Page<UV> findByNguoiDung_HoTenContainingAndLoaiHoatDong_TenLoaiHdContaining(String searchUser, String filterType, Pageable pageable);}
