package com.example.quanlycv.repo;

import com.example.quanlycv.entity.VaiTro;
import com.example.quanlycv.entity.VaiTroQuyenTruyCap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VatTroQuyenTruyCapRepo extends JpaRepository<VaiTroQuyenTruyCap,Integer> {
    @Query("SELECT v FROM VaiTroQuyenTruyCap v WHERE v.vaiTro.id = :id")
    List<VaiTroQuyenTruyCap> findAllById(Integer id);
    @Query("SELECT v FROM VaiTro v WHERE v.tenVaiTro = :tenVaiTro")
    VaiTro findByTenVaiTro( String tenVaiTro);

    List<VaiTroQuyenTruyCap> findByVaiTro(VaiTro vaiTro);
}
