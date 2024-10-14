package com.example.quanlycv.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Level")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_id")
     Integer id;

    @Column(name = "ma_level",  length = 100)
     String maLevel;

    @Column(name = "ten_level",  length = 100)
     String tenLevel;

    @Column(name = "trang_thai", columnDefinition = "BIT DEFAULT 1")
     Boolean trangThai = true;
}
