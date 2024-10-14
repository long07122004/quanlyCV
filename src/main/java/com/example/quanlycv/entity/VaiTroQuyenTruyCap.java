package com.example.quanlycv.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vai_tro_quyen_truy_cap")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VaiTroQuyenTruyCap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "vai_tro_id", nullable = false)
    private VaiTro vaiTro;

    @ManyToOne
    @JoinColumn(name = "quyen_truy_cap_id", nullable = false)
    private QuyenTruyCap quyenTruyCap;
}
