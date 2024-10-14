package com.example.quanlycv.service;

import com.example.quanlycv.Rep.CVStatusRepository;
import com.example.quanlycv.entity.CVStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CVStatusService {
    @Autowired
    private CVStatusRepository cvStatusRepository;

    // Retrieve all CV statuses
    public List<CVStatus> findAll() {
        return cvStatusRepository.findAll();
    }

    // Retrieve a CV status by ID
    public CVStatus findById(Integer id) {
        return cvStatusRepository.findById(id).orElse(null);
    }

    // Save a CV status
    public void save(CVStatus cvStatus) {
        cvStatusRepository.save(cvStatus);
    }

    // Delete a CV status by ID
    public void deleteById(Integer id) {
        cvStatusRepository.deleteById(id);
    }
}
