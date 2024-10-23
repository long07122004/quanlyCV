package com.example.quanlycv.Service;

import com.example.quanlycv.Rep.CVRepository;
import com.example.quanlycv.entity.CV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CVService {
    @Autowired
    private CVRepository cvRepository;

    public List<CV> findAll() {
        return cvRepository.findAll();
    }

    public Optional<CV> findById(Integer id) {
        return cvRepository.findById(id);
    }

    public void save(CV cv) {
        cvRepository.save(cv);
    }

    public void deleteById(Integer id) {
        cvRepository.deleteById(id);
    }

    public List<CV> findByGioiTinh(String gioiTinh) {
        return cvRepository.findByGioiTinh(gioiTinh);
    }

    public List<CV> findByCvStatusId(Integer cvStatusId) {
        return cvRepository.findByCvStatusId(cvStatusId);
    }

    public List<CV> search(String keyword, String gioiTinh, Integer cvStatusId) {
        if (gioiTinh != null && cvStatusId != null) {
            return cvRepository.findByGioiTinhAndCvStatusIdAndHoTenContaining(gioiTinh, cvStatusId, keyword);
        } else if (gioiTinh != null) {
            return cvRepository.findByGioiTinh(gioiTinh);
        } else if (cvStatusId != null) {
            return cvRepository.findByCvStatusId(cvStatusId);
        } else {
            return cvRepository.findByHoTenContaining(keyword);
        }
    }
}
