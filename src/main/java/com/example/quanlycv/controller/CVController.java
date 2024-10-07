package com.example.quanlycv.controller;

import com.example.quanlycv.Service.CVService;
import com.example.quanlycv.Service.CVStatusService;
import com.example.quanlycv.entity.CV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qlcv")
public class CVController {
    @Autowired
    private CVService cvService;

    @Autowired
    private CVStatusService cvStatusService; // Service kiểm tra trạng thái


    @PutMapping("/{id}")
    public ResponseEntity<?> updateCV(@PathVariable Integer id, @RequestBody CV cv) {
        // Kiểm tra xem cv_status_id có hợp lệ không
        if (!cvStatusService.existsById(cv.getCvStatusId())) {
            return ResponseEntity.badRequest().body("cv_status_id không hợp lệ");
        }

        cv.setCv_id(id);
        return ResponseEntity.ok(cvService.save(cv));
    }
    @GetMapping
    public List<CV> getAllCVs() {
        return cvService.findAll();
    }

    @GetMapping("/{id}")
    public CV getCVById(@PathVariable Integer id) {
        return cvService.findById(id).orElse(null);
    }

    @PostMapping
    public CV createCV(@RequestBody CV cv) {
        return cvService.save(cv);
    }

    @DeleteMapping("/{id}")
    public void deleteCV(@PathVariable Integer id) {
        cvService.deleteById(id);
    }
}
