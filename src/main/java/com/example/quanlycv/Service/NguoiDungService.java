package com.example.quanlycv.Service;

import com.example.quanlycv.Rep.NguoiDungRepo;
import com.example.quanlycv.Rep.PasswordResetTokenRepository;
import com.example.quanlycv.Rep.VaiTroRepo;
import com.example.quanlycv.entity.NguoiDung;
import com.example.quanlycv.entity.PasswordResetToken;
import com.example.quanlycv.entity.VaiTro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class NguoiDungService {
    @Autowired
    private NguoiDungRepo nguoiDungRepository;

    @Autowired
    private VaiTroRepo vaiTroRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(NguoiDungService.class);

    public void saveNguoiDung(String hoTen, String email, String sdt, String matKhau) {
        // Kiểm tra xem email đã tồn tại chưa
        if (nguoiDungRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email đã được sử dụng.");
        }

        // Tạo một người dùng mới
        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setHoTen(hoTen);
        nguoiDung.setEmail(email);
        nguoiDung.setSdt(sdt);
        nguoiDung.setMatKhau(matKhau); // Lưu mật khẩu đã mã hóa
        nguoiDung.setTrangThai(true); // Đặt trạng thái thành 1

        // Gán vai trò là 3
        VaiTro vaiTro = vaiTroRepository.findById(3) // Chọn vai trò có ID là 3
                .orElseThrow(() -> new IllegalArgumentException("Vai trò không tồn tại với ID: 3"));
        nguoiDung.setVaiTro(vaiTro);

        // Lưu người dùng vào cơ sở dữ liệu
        nguoiDungRepository.save(nguoiDung);
        logger.info("Người dùng {} đã được lưu thành công.", email);
    }

    public void generatePasswordResetToken(String email) throws Exception {
        Optional<NguoiDung> nguoiDung = nguoiDungRepository.findByEmail(email);
        if (!nguoiDung.isPresent()) {
            throw new Exception("Email không tồn tại");
        }

        // Tạo token ngẫu nhiên
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken(null,token, nguoiDung.get(),new Date());
        passwordResetTokenRepository.save(passwordResetToken);

        // Gửi email chứa token
        emailService.sendResetPasswordEmail(email, token);
    }

}
