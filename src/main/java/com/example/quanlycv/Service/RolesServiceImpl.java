package com.example.quanlycv.Service;

import com.example.quanlycv.Rep.*;
import com.example.quanlycv.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RolesServiceImpl implements RolesService{
    @Autowired
    NguoiDungRepo nguoiDungRepo;
    @Autowired
    NhanVienRepo nhanVienRepo;
    @Autowired
    VaiTroRepo vaiTroRepo;
    @Autowired
    VatTroQuyenTruyCapRepo VTQTC;
    @Autowired
    QuyenTruyCapRepo quyenTruyCapRepo;


    @Override
    public List<NguoiDung> getAll() {
        return nguoiDungRepo.findAll();
    }

    @Override
    public List<NhanVien> getALLNhanVIen() {
        return nhanVienRepo.findAll();
    }

    @Override
    public NhanVien getNhanVienById(Integer id) {
        return nhanVienRepo.findById(id).get();
    }

    @Override
    public List<VaiTro> getAllVaiTro() {
        return vaiTroRepo.findAll();
    }

    @Override
    public void saveRoles(NguoiDung nguoiDung) {
        this.nguoiDungRepo.save(nguoiDung);
    }

    @Override
    public void deleteRoles(Integer id) {
        nguoiDungRepo.deleteById(id);
    }

    @Override
    public NguoiDung getNguoiDungById(Integer id) {
//        if(nguoiDungRepo.findById(id).get() == null)
//        {
//            return null;
//        }
        return nguoiDungRepo.findById(id).get();
    }

    @Override
    public Page<NguoiDung> getAllPagination(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1,2);
        return this.nguoiDungRepo.findAll(pageable);
    }

    @Override
    public List<NguoiDung> searchNguoiDung(String keyword) {
        return nguoiDungRepo.searchUser(keyword);
    }

    @Override
    public Page<NguoiDung> searchNguoiDung(String keyword, Integer pageNo) {
        List<NguoiDung> listSearch = this.searchNguoiDung(keyword);
        Pageable pageable = PageRequest.of(pageNo-1,2);
        Integer start =(int) pageable.getOffset();
        Integer end = (int) ((pageable.getOffset()+pageable.getPageSize()) > listSearch.size() ? listSearch.size() : pageable.getOffset() + pageable.getPageSize());
        listSearch=listSearch.subList(start,end);
        return new PageImpl<NguoiDung>(listSearch,pageable,this.searchNguoiDung(keyword).size());
    }

    @Override
    public void update(NguoiDung nguoiDungEntity) {
        this.nguoiDungRepo.save(nguoiDungEntity);
    }

    @Override
    public boolean emailExists(String email, Integer nguoiDungId) {
        //return nguoiDungRepo.existsByEmailAndIdNot(email, nguoiDungId);
        return true;
    }

    @Override
    public NguoiDung findById(Integer id) {
        return nguoiDungRepo.findById(id).get();
    }

    @Override
    public List<VaiTroQuyenTruyCap> findAllVTQCT() {
        return VTQTC.findAll();
    }

    @Override
    public List<QuyenTruyCap> getAllQuyenTruyCap() {
        return this.quyenTruyCapRepo.findAll();
    }

    @Override
    public Map<String, String> getGroupedRoles(List<VaiTroQuyenTruyCap> vtqtcList) {
        return  vtqtcList.stream()
                .collect(Collectors.groupingBy(
                        vtqtc -> vtqtc.getVaiTro().getTenVaiTro(),  // Nhóm theo tên vai trò
                        Collectors.mapping(vtqtc -> vtqtc.getQuyenTruyCap().getName(),  // Lấy quyền truy cập
                                Collectors.joining("/"))));
    }


}
