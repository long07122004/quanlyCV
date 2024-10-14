package com.example.quanlycv.Service;

import com.example.quanlycv.dto.NguoiDungDTO;
import com.example.quanlycv.dto.VaiTroQuyenTruyCapDTO;
import com.example.quanlycv.entity.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

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

    List<QuyenTruyCap> getAllQuyenTruyCap();
    Map<String, String> getGroupedRoles(List<VaiTroQuyenTruyCap> vtqtcList);

    VaiTroQuyenTruyCap saveRolesPermist(VaiTroQuyenTruyCapDTO vtqctDTO);

    List<VaiTroQuyenTruyCap> findAllVTQCT(String id);

    void updateRolePermissions(Integer id, List<Integer> permissionIds);
}
