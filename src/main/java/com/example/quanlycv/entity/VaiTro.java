package com.example.quanlycv.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Vai_Tro")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class VaiTro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vai_tro_id")
     Integer id;

    @Column(name = "ten_vai_tro", nullable = false, length = 50)
     String tenVaiTro;
}
