package com.example.quanlycv.repo;

import com.example.quanlycv.entity.ViTriCongViec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface viTriCongViecRepo extends JpaRepository<ViTriCongViec,Integer> {
    Page<ViTriCongViec> findByTenViTriContainingOrMaViTriContaining(String tenViTri, String maViTri, Pageable pageable);
   Page<ViTriCongViec>  findByTenViTriContainingOrMaViTriContainingAndLevel_Id(String ten, String ma, Integer levelId, Pageable pageable);
    Page<ViTriCongViec> findByLevel_Id(Integer levelId,Pageable pageable);


    Page<ViTriCongViec> findAll(Pageable pageable);

}
