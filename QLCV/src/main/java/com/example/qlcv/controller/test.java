package com.example.qlcv.controller;
import com.example.qlcv.entities.LoaiHoatDong;
import com.example.qlcv.entities.NguoiDung;
import com.example.qlcv.repository.NguoiDungRepository;
import com.example.qlcv.entities.UV;
import com.example.qlcv.repository.LoaiHoatDongRepository;
import com.example.qlcv.repository.UVRepository;
import com.example.qlcv.repository.UVRespoRepositoey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class test {
    @Autowired
    private UVRepository uvRepository;

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private LoaiHoatDongRepository loaiHoatDongRepository;

    @GetMapping("/index-uv")
    public String viewUvList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sort", defaultValue = "ngayTao,desc") String sort,
            @RequestParam(value = "searchUser", defaultValue = "") String searchUser,
            @RequestParam(value = "filterType", defaultValue = "") String filterType,
            Model model) {

        String[] sortParams = sort.split(",");
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]));

        Page<UV> uvPage;
        if (searchUser.isEmpty() && filterType.isEmpty()) {
            uvPage = uvRepository.findAll(pageable);
        } else if (!searchUser.isEmpty() && filterType.isEmpty()) {
            uvPage = uvRepository.findByNguoiDung_HoTenContaining(searchUser, pageable);
        } else if (searchUser.isEmpty() && !filterType.isEmpty()) {
            uvPage = uvRepository.findByLoaiHoatDong_TenLoaiHdContaining(filterType, pageable);
        } else {
            uvPage = uvRepository.findByNguoiDung_HoTenContainingAndLoaiHoatDong_TenLoaiHdContaining(searchUser, filterType, pageable);
        }

        model.addAttribute("data", uvPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", uvPage.getTotalPages());
        model.addAttribute("totalItems", uvPage.getTotalElements());
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);
        model.addAttribute("searchUser", searchUser);
        model.addAttribute("filterType", filterType);

        return "TrangChuUv";
    }

    @GetMapping("/index-uv/detail")
    public String viewDetail(@RequestParam("id") Integer id, Model model) {
        UV uv = uvRepository.findById(id).orElse(null);
        if (uv == null) {
            return "redirect:/index-uv";
        }
        model.addAttribute("uv", uv);
        return "uvDetail";
    }

    @GetMapping("/index-uv/add")
    public String showAddForm(Model model) {
        UV uv = new UV();
        model.addAttribute("uv", uv);
        model.addAttribute("loaiHoatDongList", loaiHoatDongRepository.findAll());
        model.addAttribute("nguoiDungList", nguoiDungRepository.findAll());
        return "addUV";
    }

    @PostMapping("/index-uv/save")
    public String saveUV(@ModelAttribute("uv") UV uv) {
        uv.setNgayTao(new Date());

        // Lấy người dùng dựa trên ID
        if (uv.getNguoiDung() != null && uv.getNguoiDung().getId() != null) {
            NguoiDung nguoiDung = nguoiDungRepository.findById(uv.getNguoiDung().getId()).orElse(null);
            if (nguoiDung != null) {
                uv.setNguoiDung(nguoiDung);
            }
        }

        // Lấy loại hoạt động dựa trên ID
        if (uv.getLoaiHoatDong() != null && uv.getLoaiHoatDong().getId() != null) {
            LoaiHoatDong loaiHoatDong = loaiHoatDongRepository.findById(uv.getLoaiHoatDong().getId()).orElse(null);
            if (loaiHoatDong != null) {
                uv.setLoaiHoatDong(loaiHoatDong);
            }
        }

        uvRepository.save(uv);
        return "redirect:/index-uv";
    }

    @GetMapping("/index-uv/delete")
    public String deleteUV(@RequestParam("id") Integer id) {
        uvRepository.deleteById(id);
        return "redirect:/index-uv";
    }

    @GetMapping("/index-uv/edit")
    public String showEditForm(@RequestParam("id") Integer id, Model model) {
        UV uv = uvRepository.findById(id).orElse(null);
        if (uv == null) {
            return "redirect:/index-uv";
        }
        model.addAttribute("uv", uv);
        model.addAttribute("loaiHoatDongList", loaiHoatDongRepository.findAll());
        model.addAttribute("nguoiDungList", nguoiDungRepository.findAll());
        return "editUV";
    }
    @PostMapping("/index-uv/update")
    public String updateUV(@ModelAttribute("uv") UV uv, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("loaiHoatDongList", loaiHoatDongRepository.findAll());
            model.addAttribute("nguoiDungList", nguoiDungRepository.findAll());
            return "editUV";
        }

        if (uv.getNgayTao() == null) {
            uv.setNgayTao(new Date());  // Gán giá trị mặc định nếu ngày tạo bị trống
        }

        uvRepository.save(uv);
        return "redirect:/index-uv";
    }






}
