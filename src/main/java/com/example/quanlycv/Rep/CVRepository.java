package com.example.quanlycv.Rep;

import com.example.quanlycv.entity.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CVRepository extends JpaRepository<CV, Integer> {
    List<CV> findByGioiTinh(String gioiTinh);
    List<CV> findByCvStatusId(Integer cvStatusId);
    List<CV> findByHoTenContaining(String keyword);

    // New method to search by gender, status, and name
    List<CV> findByGioiTinhAndCvStatusIdAndHoTenContaining(String gioiTinh, Integer cvStatusId, String keyword);
}
