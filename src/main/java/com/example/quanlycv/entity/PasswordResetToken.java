package com.example.quanlycv.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor  // Thêm để đồng bộ với constructor mặc định
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = NguoiDung.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "nguoi_dung_id")
    private NguoiDung nguoiDung;

    @Column(name = "created_at")
    private Date createdAt;


    // Bạn không cần constructor mặc định vì @NoArgsConstructor đã xử lý
    // Getters and setters sẽ được Lombok xử lý
}
