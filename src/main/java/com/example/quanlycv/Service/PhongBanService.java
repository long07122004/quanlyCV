package com.example.quanlycv.Service;

import com.example.quanlycv.dto.phongBanReponse;
import com.example.quanlycv.entity.PhongBan;
import com.example.quanlycv.Rep.PhongBanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhongBanService implements PhongBanIplm{

    @Autowired
    PhongBanRepository PBRepo;


    @Override
    public List<PhongBan> getAll() {
        return PBRepo.findAll();
    }

    @Override
    public Page<phongBanReponse> getAllPage(Pageable pageable) {
        return PBRepo.getALLPage(pageable);
    }

    @Override
    public void savePhongBan(PhongBan pb) {
        this.PBRepo.save(pb);
    }

    @Override
    public void deletePhongBan(Integer id) {
        this.PBRepo.deleteById(id);
    }

    @Override
    public void updatePhongBan(PhongBan pb) {
        this.PBRepo.save(pb);
    }

    @Override
    public PhongBan findById(Integer id) {
        return PBRepo.findById(id).orElse(null);
    }

    @Override
    public boolean existsByMaPhongBan(String maPhongBan) {
        return PBRepo.existsByMaPhongBan(maPhongBan);
    }

    @Override
    public Page<phongBanReponse> searchPhongBan(String keyword, Integer status, Integer time, Pageable pageable) {
        return PBRepo.FindPhongBan(keyword, status, time, pageable);
    }
}
