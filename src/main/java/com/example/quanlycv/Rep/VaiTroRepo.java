package com.example.quanlycv.Rep;

import com.example.quanlycv.entity.VaiTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaiTroRepo extends JpaRepository<VaiTro,Integer> {
}
