package com.example.quanlycv.Rep;


import com.example.quanlycv.entity.TruongPhong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TruongPhongRepo extends JpaRepository<TruongPhong, Integer> {
    @Query("""
        SELECT tp FROM TruongPhong tp where tp.tenTruongPhong = :name
""")
    TruongPhong findByName(String name);
}
