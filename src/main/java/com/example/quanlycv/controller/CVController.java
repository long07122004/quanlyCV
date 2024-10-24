package com.example.quanlycv.controller;

import com.example.quanlycv.Service.CVService;
import com.example.quanlycv.entity.CV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cv")
public class CVController {
    @Autowired
    private CVService cvService;

    @Autowired
    private com.example.quanlycv.service.CVStatusService cvStatusService;

    @GetMapping
    public String listCVs(Model model) {
        model.addAttribute("cvs", cvService.findAll());
        model.addAttribute("statuses", cvStatusService.findAll()); // Đảm bảo status được truyền vào
        return "cv/list";
    }


    @GetMapping("/add")
    public String addCVForm(Model model) {
        model.addAttribute("cv", new CV());
        model.addAttribute("statuses", cvStatusService.findAll());
        return "cv/add";
    }

    @PostMapping("/save")
    public String saveCV(@ModelAttribute CV cv) {
        // Set the applyDateTime to the current date and time
        cv.setApplyDateTime(new Date()); // or LocalDateTime.now() if you are using Java 8+ DateTime API

        // Save the CV
        cvService.save(cv);

        return "redirect:/cv"; // Redirect to the CV list page
    }


    @GetMapping("/edit/{id}")
    public String editCVForm(@PathVariable Integer id, Model model) {
        CV cv = cvService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid CV ID: " + id));
        model.addAttribute("cv", cv);
        model.addAttribute("statuses", cvStatusService.findAll());
        return "cv/edit";
    }

    @PostMapping("/update/{id}")
    public String updateCV(@PathVariable Integer id, @ModelAttribute CV cv) {

    // Kiểm tra xem CV có tồn tại không
        Optional<CV> existingCVOptional = cvService.findById(id);

        if (existingCVOptional.isPresent()) {
            CV existingCV = existingCVOptional.get(); // Lấy đối tượng CV hiện tại

            // Cập nhật các trường cần thiết
            existingCV.setHoTen(cv.getHoTen());
            existingCV.setGioiTinh(cv.getGioiTinh());
            existingCV.setEmail(cv.getEmail());
            existingCV.setSdt(cv.getSdt());
            existingCV.setThanhPho(cv.getThanhPho());
            existingCV.setTenCongViec(cv.getTenCongViec());
            existingCV.setSoNamKinhNghiem(cv.getSoNamKinhNghiem());
            existingCV.setGhiChu(cv.getGhiChu());
            existingCV.setLinkCV(cv.getLinkCV());
            existingCV.setNguonTuyenDung(cv.getNguonTuyenDung());
            existingCV.setCvStatusId(cv.getCvStatusId());

            // Lưu đối tượng CV đã được cập nhật
            cvService.save(existingCV);
        } else {
            throw new IllegalArgumentException("Invalid CV ID: " + id);
        }

        return "redirect:/cv"; // Chuyển hướng về trang danh sách CV
    }

    @GetMapping("/delete/{id}")
    public String deleteCV(@PathVariable Integer id) {
        cvService.deleteById(id);
        return "redirect:/cv";
    }

    @GetMapping("/search")
    public String searchCVs(@RequestParam String keyword,
                            @RequestParam(required = false) String gioiTinh,
                            @RequestParam(required = false) Integer cvStatusId,
                            Model model) {
        List<CV> results = cvService.search(keyword, gioiTinh, cvStatusId);
        model.addAttribute("cvs", results);
        model.addAttribute("statuses", cvStatusService.findAll());
        return "cv/list";
    }

}
