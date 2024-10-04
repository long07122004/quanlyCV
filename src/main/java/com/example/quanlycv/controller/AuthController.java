package com.example.quanlycv.controller;

import com.example.quanlycv.Rep.NguoiDungRepo;
import com.example.quanlycv.Rep.PasswordResetTokenRepository;
import com.example.quanlycv.entity.NguoiDung;
import com.example.quanlycv.entity.PasswordResetToken;
import com.example.quanlycv.Service.EmailService;
import com.example.quanlycv.Service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private NguoiDungRepo nguoiDungRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @GetMapping("/login")
    public String login() {
        return "login"; // Trả về trang đăng nhập
    }

    @PostMapping("/login")
    public String loginError(Model model) {
        model.addAttribute("error", "Sai tên đăng nhập hoặc mật khẩu. Vui lòng thử lại.");
        return "login"; // Trả về trang đăng nhập
    }


    @GetMapping("/home")
    public String home() {
        return "home"; // Trả về trang chính sau khi đăng nhập
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // Trả về trang đăng ký
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String hoTen,
                               @RequestParam String email,
                               @RequestParam String sdt,
                               @RequestParam String matKhau,
                               Model model) {
        try {
            // Mã hóa mật khẩu trước khi lưu
            String hashedPassword = passwordEncoder.encode(matKhau);
            nguoiDungService.saveNguoiDung(hoTen, email, sdt, hashedPassword);
            model.addAttribute("message", "Đăng ký thành công!");
            return "redirect:/login"; // Chuyển hướng đến trang đăng nhập
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi. Vui lòng thử lại.");
        }
        return "register"; // Trả về lại trang đăng ký
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password"; // Trả về trang quên mật khẩu
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, Model model) {
        try {
            // Tạo token và gửi email
            nguoiDungService.generatePasswordResetToken(email);
            model.addAttribute("message", "Một email đã được gửi để đặt lại mật khẩu.");
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi. Vui lòng thử lại.");
        }
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        // Kiểm tra token hợp lệ không
        Optional<PasswordResetToken> resetToken = passwordResetTokenRepository.findByToken(token);
        if (!resetToken.isPresent()) {
            model.addAttribute("error", "Token không hợp lệ.");
            return "reset-password";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam String token,
                                       @RequestParam String newPassword,
                                       Model model) {
        // Kiểm tra và đổi mật khẩu
        Optional<PasswordResetToken> resetToken = passwordResetTokenRepository.findByToken(token);
        if (!resetToken.isPresent()) {
            model.addAttribute("error", "Token không hợp lệ.");
            return "reset-password";
        }

        NguoiDung nguoiDung = resetToken.get().getNguoiDung();
        nguoiDung.setMatKhau(passwordEncoder.encode(newPassword));
        nguoiDungRepository.save(nguoiDung);

        model.addAttribute("message", "Đặt lại mật khẩu thành công!");
        return "redirect:/login";
    }

}
