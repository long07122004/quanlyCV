package com.example.quanlycv.Service;


import com.example.quanlycv.entity.PasswordResetToken;
import com.example.quanlycv.repo.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    // Các phương thức để làm việc với passwordResetToken
    public void saveToken(PasswordResetToken token) {
        passwordResetTokenRepository.save(token);
    }

    // Thêm các phương thức khác nếu cần
}
