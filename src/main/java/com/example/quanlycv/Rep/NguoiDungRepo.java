package com.example.quanlycv.Rep;

import com.example.quanlycv.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NguoiDungRepo extends JpaRepository<NguoiDung,Integer> {
//    @Query("SELECT p FROM NguoiDung p WHERE p.hoTen LIKE %?1%")
//    List<NguoiDung> searchUser(String keyword);
    @Query("SELECT p FROM NguoiDung p WHERE p.hoTen LIKE %?1% or p.email LIKE %?1% or p.sdt LIKE %?1% or p.vaiTro.tenVaiTro LIKE %?1%")
    List<NguoiDung> searchUser(String keyword);
}
