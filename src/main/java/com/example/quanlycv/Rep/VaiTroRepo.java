package com.example.quanlycv.Rep;

import com.example.quanlycv.entity.VaiTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VaiTroRepo extends JpaRepository<VaiTro,Integer> {

    @Query("""
                    SELECT vt FROM VaiTro vt where vt.tenVaiTro = :name
            """)
    VaiTro findByName(String name);
}
