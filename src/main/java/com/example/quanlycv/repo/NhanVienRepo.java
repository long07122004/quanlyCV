package com.example.quanlycv.repo;

import com.example.quanlycv.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienRepo extends JpaRepository<NhanVien, Integer> {
    @Query("""  
            SELECT nv FROM NhanVien nv WHERE 
            (nv.ma LIKE %:keyWord%) OR 
            (nv.hoTen LIKE %:keyWord%) OR 
            (nv.email LIKE %:keyWord%) OR 
            (nv.sdt LIKE %:keyWord%)
        """)
    Page<NhanVien> timKiemNhanVien(@Param("keyWord") String keyWord, Pageable pageable);

    @Query("SELECT n FROM NhanVien n " +
            "WHERE (:role IS NULL OR n.vaiTro.tenVaiTro = :role) " +
            "AND (:tenViTri IS NULL OR n.viTri.tenViTri = :tenViTri)")
    Page<NhanVien> filter(@Param("role") String role, @Param("tenViTri") String tenViTri, Pageable pageable);

    @Query("SELECT n FROM NhanVien n WHERE n.vaiTro.tenVaiTro = :role")
    Page<NhanVien> filterByRole(@Param("role") String role, Pageable pageable);

    @Query("SELECT n FROM NhanVien n WHERE n.viTri.tenViTri = :tenViTri")
    Page<NhanVien> filterByViTri(@Param("tenViTri") String tenViTri, Pageable pageable);
}
