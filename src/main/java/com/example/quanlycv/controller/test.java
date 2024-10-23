package com.example.quanlycv.controller;

import com.example.quanlycv.Rep.LoaiHoatDongRepository;
import com.example.quanlycv.Rep.NguoiDungRepo;
import com.example.quanlycv.Rep.UVRepository;
import com.example.quanlycv.entity.LoaiHoatDong;
import com.example.quanlycv.entity.NguoiDung;
import com.example.quanlycv.entity.QlTuyenDung;
import com.example.quanlycv.Service.DotTuyenDungService;
import com.example.quanlycv.entity.UV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
public class test {
    @Autowired
    private DotTuyenDungService dotTuyenDungService;
    @Autowired
    NguoiDungRepo nguoiDungRepo;

    @GetMapping("/")
    public String viewTest1(){
        return "Menu";
    }


//    @GetMapping("/tuyen-dung")
//    public String listtuyendung( Model model){
//        model.addAttribute("tuyenDung", dotTuyenDungService.findAll());
//        model.addAttribute("qlTuyenDung", new QlTuyenDung());
//
//        return "tuyen-dung";
//    }

    @GetMapping("/tuyen-dung")
    public String listTuyenDung(Model model,
                                @RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
                                @Param("keyword") String keyword) {
        LocalDate today = LocalDate.now();

        // Ngày sau 6 tháng
        LocalDate sixMonthsLater = today.plusMonths(6);

        // Định dạng ngày theo kiểu yyyy-MM-dd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String minDate = today.format(formatter); // ngày hiện tại
        String maxDate = sixMonthsLater.format(formatter); // ngày sau 6 tháng

        // Thêm các biến vào model để sử dụng trong view Thymeleaf
        model.addAttribute("minDate", minDate);
        model.addAttribute("maxDate", maxDate);

        Page<QlTuyenDung> tuyenDungPage = dotTuyenDungService.getAllPagination(pageNo);
                model.addAttribute("tuyenDungPage", tuyenDungPage);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", tuyenDungPage.getTotalPages());
        model.addAttribute("qlTuyenDung", new QlTuyenDung());


        return "tuyen-dung";
    }
    @GetMapping("/tuyen-dung/search")
    public String searchTuyenDung(@RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(defaultValue = "1") int pageNo,
                                  Model model) {
        int pageSize = 5; // Số lượng item trên mỗi trang

        Page<QlTuyenDung> tuyenDungPage = dotTuyenDungService.search(keyword, pageNo, pageSize);

        model.addAttribute("tuyenDungPage", tuyenDungPage);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", tuyenDungPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("qlTuyenDung", new QlTuyenDung());

        return "tuyen-dung";
    }



    @PostMapping("/tuyen-dung/add")
    public String addDotTuyenDung(@ModelAttribute("qlTuyenDung") QlTuyenDung qlTuyenDung, RedirectAttributes redirectAttributes) {
        dotTuyenDungService.saveDotTuyenDung(qlTuyenDung);
        redirectAttributes.addFlashAttribute("message", "Đợt tuyển dụng đã được thêm thành công!");
        return "redirect:/tuyen-dung";
    }


    @GetMapping("/tuyen-dung/delete/{id}")
    public String deleteTuyenDung(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            dotTuyenDungService.deleteDotTuyenDung(id);
            redirectAttributes.addFlashAttribute("message", "Xóa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Xóa không thành công!");
        }
        return "redirect:/tuyen-dung";
    }

    @GetMapping("/tuyen-dung/detail/{id}")
    public String viewDetail(@PathVariable("id") Integer id, Model model) {



        // Tìm đợt tuyển dụng theo id
        QlTuyenDung tuyenDung = dotTuyenDungService.getTuyenDungById(id);
        // Đưa thông tin đợt tuyển dụng vào model để hiển thị trên trang chi tiết
        model.addAttribute("tuyenDung", tuyenDung);
        return "tuyen-dung-detail";  // Tên file HTML của trang chi tiết
    }

    // Show the update form
//    @GetMapping("/tuyen-dung/edit/{id}")
//    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
//        QlTuyenDung tuyenDung = dotTuyenDungService.getTuyenDungById(id);
//        model.addAttribute("tuyenDung", tuyenDung);
//        return "tuyen-dung-detail"; // Name of the HTML file for the form
//    }

    // Handle the update request
    @PostMapping("/tuyen-dung/update/{id}")
    public String updateTuyenDung(@PathVariable("id") Integer id, @ModelAttribute("tuyenDung") QlTuyenDung tuyenDung,Model model) {
        LocalDate today = LocalDate.now();

        // Ngày sau 6 tháng
        LocalDate sixMonthsLater = today.plusMonths(6);

        // Định dạng ngày theo kiểu yyyy-MM-dd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String minDate = today.format(formatter); // ngày hiện tại
        String maxDate = sixMonthsLater.format(formatter); // ngày sau 6 tháng

        // Thêm các biến vào model để sử dụng trong view Thymeleaf
        model.addAttribute("minDate", minDate);
        model.addAttribute("maxDate", maxDate);

        try {
            dotTuyenDungService.update(id, tuyenDung);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "tuyen-dung-detail"; // Trả về form với thông báo lỗi
        }


        return "redirect:/tuyen-dung"; // Redirect back to the list
    }

    @Autowired
    private UVRepository uvRepository;

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
    public String viewDetail1(@RequestParam("id") Integer id, Model model) {
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
        model.addAttribute("nguoiDungList",nguoiDungRepo.findAll());
        return "addUV";
    }

    @PostMapping("/index-uv/save")
    public String saveUV(@ModelAttribute("uv") UV uv) {
        uv.setNgayTao(new Date());

        // Lấy người dùng dựa trên ID
        if (uv.getNguoiDung() != null && uv.getNguoiDung().getNguoiDungId() != null) {
            NguoiDung nguoiDung = nguoiDungRepo.findById(uv.getNguoiDung().getNguoiDungId()).orElse(null);
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
        model.addAttribute("nguoiDungList", nguoiDungRepo.findAll());
        return "editUV";
    }
    @PostMapping("/index-uv/update")
    public String updateUV(@ModelAttribute("uv") UV uv, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("loaiHoatDongList", loaiHoatDongRepository.findAll());
            model.addAttribute("nguoiDungList", nguoiDungRepo.findAll());
            return "editUV";
        }

        if (uv.getNgayTao() == null) {
            uv.setNgayTao(new Date());  // Gán giá trị mặc định nếu ngày tạo bị trống
        }

        uvRepository.save(uv);
        return "redirect:/index-uv";
    }


    @GetMapping("/test-qlcv")
    public String viewqlcv() {
        return "test-qlcv"; // Thay vì "qlcv", tránh trùng với phương thức trong CVController
    }


}





