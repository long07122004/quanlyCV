package com.example.quanlycv.Service;


import com.example.quanlycv.entity.NguoiDung;
import com.example.quanlycv.entity.VaiTro;
import com.example.quanlycv.repo.NguoiDungRepo;
import com.example.quanlycv.repo.VaiTroRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends OidcUserService {

    @Autowired
    private NguoiDungRepo nguoiDungRepository;

    @Autowired
    private VaiTroRepo vaiTroRepository;

    @Autowired
    @Lazy // Thêm @Lazy để trì hoãn khởi tạo PasswordEncoder
    private PasswordEncoder passwordEncoder;


    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        String email = oidcUser.getAttribute("email");

        Optional<NguoiDung> nguoiDungOptional = nguoiDungRepository.findByEmail(email);

        if (nguoiDungOptional.isEmpty()) {
            // Nếu chưa tồn tại người dùng, tạo tài khoản mới
            NguoiDung nguoiDung = new NguoiDung();
            nguoiDung.setHoTen(oidcUser.getAttribute("name"));
            nguoiDung.setEmail(email);
            nguoiDung.setMatKhau(passwordEncoder.encode("oauth2user")); // Bạn có thể đặt mật khẩu mặc định
            nguoiDung.setTrangThai(true);

            VaiTro vaiTro = vaiTroRepository.findById(3)
                    .orElseThrow(() -> new IllegalArgumentException("Vai trò không tồn tại với ID: 3"));
            nguoiDung.setVaiTro(vaiTro);

            nguoiDungRepository.save(nguoiDung);
        }

        return oidcUser;
    }

}
