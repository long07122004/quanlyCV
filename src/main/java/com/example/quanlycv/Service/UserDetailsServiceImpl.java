package com.example.quanlycv.Service;

import com.example.quanlycv.Rep.NguoiDungRepo;
import com.example.quanlycv.entity.NguoiDung;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private NguoiDungRepo nguoiDungRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("Không tìm thấy người dùng với email: " + email);
                    return new UsernameNotFoundException("User not found with email: " + email);
                });

        // Logging thông tin người dùng
        System.out.println("User found: " + nguoiDung.getEmail());

        return org.springframework.security.core.userdetails.User
                .withUsername(nguoiDung.getEmail())
                .password(nguoiDung.getMatKhau()) // Sử dụng mật khẩu đã mã hóa từ cơ sở dữ liệu
                .authorities(nguoiDung.getAuthorities()) // Sử dụng authorities từ thực thể
                .accountLocked(!nguoiDung.isTrangThai()) // Kiểm tra trạng thái người dùng
                .build();
    }

}
