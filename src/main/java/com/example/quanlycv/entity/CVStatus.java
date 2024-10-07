package com.example.quanlycv.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CV_status")
public class CVStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cv_status_id")
    private Integer cvStatusId;

    @Column(name = "ma_cv_status", nullable = false, unique = true)
    private String maCvStatus;

    @Column(name = "ten_cv_status", nullable = false)
    private String tenCvStatus;
}
