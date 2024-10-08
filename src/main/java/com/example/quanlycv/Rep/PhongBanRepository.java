package com.example.quanlycv.Rep;

import com.example.quanlycv.dto.phongBanReponse;
import com.example.quanlycv.entity.PhongBan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhongBanRepository extends JpaRepository<PhongBan, Integer> {
    @Query("""
    select new com.example.quanlycv.dto.phongBanReponse(pb.id, pb.maPhongBan, pb.tenPhongBan, pb.trangThai, pb.ngayTao, pb.ngayCapNhat)
    from PhongBan pb
    where (:keyword IS NULL OR pb.tenPhongBan LIKE %:keyword% OR pb.maPhongBan LIKE %:keyword%)
    AND (
            (:status = 0) OR
            (:status = 1 AND pb.trangThai = true) OR
            (:status = 2 AND pb.trangThai = false)
        )
    ORDER BY 
    CASE WHEN :time = 1 THEN pb.ngayTao END DESC,
    CASE WHEN :time = 2 THEN pb.ngayTao END ASC,
    CASE WHEN :time = 3 THEN pb.ngayCapNhat END DESC
    """)

    Page<phongBanReponse> FindPhongBan(String keyword, Integer status, Integer time, Pageable pageable);

    @Query("""
            select new com.example.quanlycv.dto.phongBanReponse(pb.id,pb.maPhongBan,pb.tenPhongBan,pb.trangThai,pb.ngayTao,pb.ngayCapNhat)
            from PhongBan pb order by pb.ngayTao desc 
            """)
    Page<phongBanReponse> getALLPage(Pageable pageable);

    boolean existsByMaPhongBan(String maPhongBan);
}
