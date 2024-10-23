package com.example.quanlycv.Rep;

import com.example.quanlycv.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NguoiDungRep extends JpaRepository<NguoiDung, Integer> {

}
