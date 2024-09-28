package com.example.quanlycv.Rep;

import com.example.quanlycv.Entity.QlTuyenDung;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TuyenDungRep extends JpaRepository<QlTuyenDung,Integer> {

    Page<QlTuyenDung> findAll(Pageable pageable);

//    @Query("SELECT td FROM QlTuyenDung td WHERE td.maDot LIKE %?1% OR td.tenDot LIKE %?1% OR td.noiDung LIKE %?1%  OR td.deadline LIKE %?1% ")
//    List<QlTuyenDung> searchByKeyword(String keyword);

//    List<QlTuyenDung> findByMaDotContainingOrTenDotContainingOrNoiDungContaining(String maDot, String tenDot, String noiDung, Date deadline);
}
