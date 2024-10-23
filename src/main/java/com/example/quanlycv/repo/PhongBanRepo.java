package com.example.quanlycv.repo;

import com.example.quanlycv.entity.PhongBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhongBanRepo extends JpaRepository<PhongBan,Integer> {

    @Query("""
                        SELECT pb FROM PhongBan pb where pb.tenPhongBan = :name
                """)
    PhongBan findByName(String name);
}
