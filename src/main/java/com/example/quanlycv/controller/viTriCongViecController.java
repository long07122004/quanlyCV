package com.example.quanlycv.controller;

import com.example.quanlycv.entity.ViTriCongViec;
import com.example.quanlycv.Rep.PhongBanRepo;
import com.example.quanlycv.Rep.levelRepo;
import com.example.quanlycv.Rep.viTriCongViecRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class viTriCongViecController {
    @Autowired
    levelRepo levelRepo;
    @Autowired
    PhongBanRepo phongBanRepo;
    @Autowired
    viTriCongViecRepo viTriCongViecRepo;

    @GetMapping("/quan-ly-vi-tri")
    public String vitri(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ViTriCongViec> viTriCongViecs = viTriCongViecRepo.findAll(pageable);
        model.addAttribute("viTri", viTriCongViecs);
        model.addAttribute("level", levelRepo.findAll());
        model.addAttribute("phongBan", phongBanRepo.findAll());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", viTriCongViecs.getTotalPages());

        return "viTriCongViec";
    }

    @PostMapping("/add-viTri")
    public String addViTri(Model model, ViTriCongViec viTriCongViec){
        viTriCongViec.setTrangThai(true);
        viTriCongViecRepo.save(viTriCongViec);
        return "redirect:/quan-ly-vi-tri";
    }
    @GetMapping("/lay-id-Vitri/{id}")
    public String layVitri(@PathVariable Integer id,Model model){
        ViTriCongViec viTriCongViec =  viTriCongViecRepo.getReferenceById(id);
        System.out.println("Trạng thái : " + viTriCongViec.getTrangThai());
        model.addAttribute("viTriId", viTriCongViec);
        model.addAttribute("viTri", viTriCongViecRepo.findAll());
        model.addAttribute("level", levelRepo.findAll());
        model.addAttribute("phongBan", phongBanRepo.findAll());
        return "viTriCongViec";
    }
    @PostMapping("/update-viTri")
    public String update(ViTriCongViec viTriCongViec){
        viTriCongViecRepo.save(viTriCongViec);
        return "redirect:/quan-ly-vi-tri";
    }

    @GetMapping("/doitrangThai")
    public String doiTrangThai(@RequestParam Integer id){
        ViTriCongViec viTriCongViec = viTriCongViecRepo.findById(id).get();
        if(viTriCongViec.getTrangThai() == true){
            viTriCongViec.setTrangThai(false);
        } else if (viTriCongViec.getTrangThai() == false) {
            viTriCongViec.setTrangThai(true);
        }
        viTriCongViecRepo.save(viTriCongViec);
        return "redirect:/quan-ly-vi-tri";
    }

    @GetMapping("/delete/{id}")
    public String xoa(@PathVariable Integer id){
        viTriCongViecRepo.deleteById(id);
        return  "redirect:/quan-ly-vi-tri";
    }

    @GetMapping("/find-combined")
    public String findCombined(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            @RequestParam(name = "key", required = false) String key,
            @RequestParam(name = "level", required = false) Integer level,
            Model model) {

            // nếu page < 0 , return page = 0
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<ViTriCongViec> viTriCongViecs;
        if (key == null || key.isEmpty()) {
            if (level != null && level > 0) {
                viTriCongViecs = viTriCongViecRepo.findByLevel_Id(level, pageable);
            } else {
                viTriCongViecs = viTriCongViecRepo.findAll(pageable);
            }
        } else {
            if (level != null && level > 0) {
                viTriCongViecs = viTriCongViecRepo.findByTenViTriContainingOrMaViTriContainingAndLevel_Id(key, key, level, pageable);
            } else {
                viTriCongViecs = viTriCongViecRepo.findByTenViTriContainingOrMaViTriContaining(key, key, pageable);
            }
        }
        if (viTriCongViecs.isEmpty() && page > 0) {
            return "redirect:/find-combined?page=0&size=" + size + "&key=" + key + "&level=" + level;
        }

        model.addAttribute("viTri", viTriCongViecs);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", viTriCongViecs.getTotalPages());
        model.addAttribute("level", levelRepo.findAll());
        model.addAttribute("phongBan", phongBanRepo.findAll());
        return "view/viTriCongViec";
    }

}
