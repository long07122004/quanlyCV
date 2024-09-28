package com.example.quanlycv.Rep;

import com.example.quanlycv.entity.QlTuyenDung;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TuyenDungRep extends JpaRepository<QlTuyenDung,Integer> {

    Page<QlTuyenDung> findAll(Pageable pageable);

//    @Query("SELECT td FROM QlTuyenDung td WHERE td.maDot LIKE %?1% OR td.tenDot LIKE %?1% OR td.noiDung LIKE %?1%  OR td.deadline LIKE %?1% ")
//    List<QlTuyenDung> searchByKeyword(String keyword);

//    List<QlTuyenDung> findByMaDotContainingOrTenDotContainingOrNoiDungContaining(String maDot, String tenDot, String noiDung, Date deadline);
}
