package com.example.quanlycv.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "quyen_truy_cap")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuyenTruyCap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quyen_truy_cap_id")
    Integer id;

    @Column(name = "quyen_truy_cap_name")
    String name;
}
