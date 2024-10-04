package com.example.quanlycv.Service;

import com.example.quanlycv.dto.NguoiDungDTO;
import com.example.quanlycv.entity.NguoiDung;
import com.example.quanlycv.entity.NhanVien;
import com.example.quanlycv.entity.VaiTro;
import com.example.quanlycv.entity.VaiTroQuyenTruyCap;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RolesService {
    List<NguoiDung> getAll();
    List<NhanVien> getALLNhanVIen();
    NhanVien getNhanVienById(Integer id);
    List<VaiTro> getAllVaiTro();
    void saveRoles(NguoiDung nguoiDung);
    void deleteRoles(Integer id);
    NguoiDung getNguoiDungById(Integer id);
    Page<NguoiDung> getAllPagination(Integer pageNo);
    List<NguoiDung> searchNguoiDung(String keyword);
    Page<NguoiDung> searchNguoiDung(String keyword,Integer pageNo);

    void update(NguoiDung nguoiDungEntity);
    boolean emailExists(String email, Integer nguoiDungId);

    NguoiDung findById(Integer id);
    List<VaiTroQuyenTruyCap> findAllVTQCT();
}
