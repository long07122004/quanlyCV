package com.example.quanlycv.Service;

import com.example.quanlycv.repository.CVStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CVStatusService {
    @Autowired
    private CVStatusRepository cvStatusRepository; // Repository cho bảng CV_status

    public boolean existsById(Integer id) {
        return cvStatusRepository.existsById(id);
    }
}
