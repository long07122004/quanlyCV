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

    public CV save(CV cv) {
        return cvRepository.save(cv);
    }

    public void deleteById(Integer id) {
        cvRepository.deleteById(id);
    }
}
