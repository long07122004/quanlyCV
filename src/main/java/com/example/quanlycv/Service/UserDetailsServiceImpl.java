package com.example.quanlycv.Service;


import com.example.quanlycv.entity.NguoiDung;
import com.example.quanlycv.repo.NguoiDungRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        // Ánh xạ vai trò thành authorities
        List<GrantedAuthority> authorities = new ArrayList<>();
        int vaiTroId = nguoiDung.getVaiTro().getId(); // Giả sử bạn có phương thức getId() để lấy ID của vai trò

        if (vaiTroId == 1) { // ADMIN
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (vaiTroId == 2) { // MANAGER
            authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        } else if (vaiTroId == 3) { // MEMBER
            authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
        }



        // Trả về đối tượng UserDetails
        return org.springframework.security.core.userdetails.User
                .withUsername(nguoiDung.getEmail())
                .password(nguoiDung.getMatKhau()) // Sử dụng mật khẩu đã mã hóa từ cơ sở dữ liệu
                .authorities(authorities) // Sử dụng danh sách authorities
                .accountLocked(!nguoiDung.isTrangThai()) // Kiểm tra trạng thái người dùng
                .build();
    }

}
