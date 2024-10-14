package com.example.quanlycv.Rep;

import com.example.quanlycv.entity.CVStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CVStatusRepository extends JpaRepository<CVStatus, Integer> {
}
