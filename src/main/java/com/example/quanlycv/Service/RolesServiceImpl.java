package com.example.quanlycv.Service;

import com.example.quanlycv.Rep.NguoiDungRepo;
import com.example.quanlycv.Rep.NhanVienRepo;
import com.example.quanlycv.Rep.VaiTroRepo;
import com.example.quanlycv.entity.NguoiDung;
import com.example.quanlycv.entity.NhanVien;
import com.example.quanlycv.entity.VaiTro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesServiceImpl implements RolesService{
    @Autowired
    NguoiDungRepo nguoiDungRepo;
    @Autowired
    NhanVienRepo nhanVienRepo;
    @Autowired
    VaiTroRepo vaiTroRepo;


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


}
