package com.example.quanlycv.Service;

import com.example.quanlycv.entity.PhongBan;
import com.example.quanlycv.Rep.PhongBanRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return PBRepo.findById(id).get();
    }
}
