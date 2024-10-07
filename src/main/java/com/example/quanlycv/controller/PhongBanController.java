package com.example.quanlycv.controller;

import com.example.quanlycv.entity.PhongBan;
import com.example.quanlycv.Rep.PhongBanRepository;
import com.example.quanlycv.Service.PhongBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.Optional;

@Controller
public class PhongBanController {
    @Autowired
    private PhongBanRepository PBRepo;

    @Autowired
    private PhongBanService PBservice;

    @PostMapping("/updateTrangThai/{id}")
    public String updateTrangThai(@PathVariable int id,
                                  @RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
                                  @RequestParam(value = "itemsPerPage", defaultValue = "5") int itemsPerPage) {
        // Tìm phòng ban theo ID
        PhongBan pb = PBRepo.findById(id).orElse(null);
        if (pb != null) {
            // Cập nhật trạng thái
            if (pb.getTrangThai() == true) {
                pb.setTrangThai(false);

            } else {
                pb.setTrangThai(true);
            }
            // Cập nhật ngày chỉnh sửa hiện tại
            pb.setNgayCapNhat(Instant.now());
            PBRepo.save(pb);
        }
        // Chuyển hướng về trang phân trang đang đứng
        return String.format("redirect:/phongBan?page=%d&itemsPerPage=%d", currentPage, itemsPerPage);
    }

    @PostMapping("/addPhongBan")
    public String addPhongBan(@RequestParam("ma_phong_ban") String maPhongBan,
                              @RequestParam("ten_phong_ban") String tenPhongBan,
                              @RequestParam("trang_thai") boolean trangThai,
                              @RequestParam(value = "page", defaultValue = "1") int currentPage,
                              @RequestParam(value = "itemsPerPage", defaultValue = "10") int itemsPerPage) {

        PhongBan phongBan = new PhongBan();
        phongBan.setMaPhongBan(maPhongBan);
        phongBan.setTenPhongBan(tenPhongBan);
        phongBan.setTrangThai(trangThai);

        // Lấy thời gian hiện tại với giờ, phút, giây
        Instant ngayTao = Instant.now();
        Instant ngayCapNhat = Instant.now();

        phongBan.setNgayTao(ngayTao);
        phongBan.setNgayCapNhat(ngayCapNhat);

        // Lưu đối tượng PhongBan vào cơ sở dữ liệu
        PBRepo.save(phongBan);

        // Chuyển hướng về trang phân trang hiện tại
        return String.format("redirect:/phongBan?page=%d&itemsPerPage=%d", currentPage, itemsPerPage);
    }

    @PostMapping("/deletePhongBan")
    public String deletePhongBan(@RequestParam("id") Integer id,
                                 @RequestParam("currentPage") int currentPage,
                                 @RequestParam("itemsPerPage") int itemsPerPage) {
        // Kiểm tra và xóa phòng ban
        if (PBRepo.existsById(id)) {
            PBRepo.deleteById(id);
        }
        // Chuyển hướng về trang phân trang hiện tại
        return String.format("redirect:/phongBan?page=%d&itemsPerPage=%d", currentPage, itemsPerPage);
    }

    @PostMapping("/updatePhongBan")
    public String updatePhongBan(@RequestParam("id") Integer id,
                                 @RequestParam("ten_phong_ban") String tenPhongBan,
                                 @RequestParam("trang_thai") Boolean trangThai,
                                 @RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
                                 @RequestParam(value = "itemsPerPage", defaultValue = "5") int itemsPerPage) {
        // Tìm phòng ban theo ID
        Optional<PhongBan> optionalPhongBan = PBRepo.findById(id);
        if (optionalPhongBan.isPresent()) {
            PhongBan phongBan = optionalPhongBan.get();

            // Cập nhật tên phòng ban và trạng thái
            phongBan.setTenPhongBan(tenPhongBan);
            phongBan.setTrangThai(trangThai);

            // Cập nhật ngày chỉnh sửa hiện tại
            phongBan.setNgayCapNhat(Instant.now());

            // Lưu lại thông tin sau khi chỉnh sửa
            PBRepo.save(phongBan);
        }

        // Chuyển hướng về trang phân trang đang đứng
        return String.format("redirect:/phongBan?page=%d&itemsPerPage=%d", currentPage, itemsPerPage);
    }

    @GetMapping("/getPhongBanById")
    public String getPhongBanById(@RequestParam("id") Integer id,
                                  Model model) {
        // Tìm phòng ban theo ID
        Optional<PhongBan> optionalPhongBan = PBRepo.findById(id);

        if (optionalPhongBan.isPresent()) {
            model.addAttribute("phongBan", optionalPhongBan.get());
        } else {
            model.addAttribute("errorMessage", "Không tìm thấy phòng ban với ID: " + id);
        }

        return "PhongBan";
    }

//    @GetMapping("/getPhongBanById")
//    @ResponseBody
//    public PhongBan getPhongBanById(@RequestParam("id") Integer id) {
//        // Tìm phòng ban theo ID
//        return PBRepo.findById(id).orElse(null);
//    }


    @GetMapping("/phongBan")
    public String viewPhongBan(
            @RequestParam(value = "itemsPerPage", defaultValue = "5") int itemsPerPage,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {

        // Sử dụng Pageable để hỗ trợ phân trang
        Pageable phantrang = PageRequest.of(page, itemsPerPage);

        // Tìm danh sách phòng ban với phân trang
        Page<PhongBan> phongBanPage = PBRepo.findAll(phantrang);

        // Thêm vào model
        model.addAttribute("listPB", phongBanPage.getContent());
        model.addAttribute("currentPage", phongBanPage.getNumber()); // Trang hiện tại
        model.addAttribute("totalPages", phongBanPage.getTotalPages()); // Tổng số trang
        model.addAttribute("itemsPerPage", itemsPerPage); // Số phần tử trên mỗi trang
        model.addAttribute("totalElements", phongBanPage.getTotalElements()); // Tổng số phần tử
        return "PhongBan";
    }

}
