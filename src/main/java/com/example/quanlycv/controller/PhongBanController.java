package com.example.quanlycv.controller;

import com.example.quanlycv.dto.phongBanReponse;
import com.example.quanlycv.entity.PhongBan;
import com.example.quanlycv.Rep.PhongBanRepository;
import com.example.quanlycv.Service.PhongBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.model.IModel;

import org.springframework.http.HttpHeaders;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Controller
public class PhongBanController {
    @Autowired
    private PhongBanRepository PBRepo;

    @Autowired
    private PhongBanService PBservice;

//    @GetMapping("/search")
//    public ResponseEntity<Page<phongBanReponse>> searchPhongBan(
//            @RequestParam(required = false) String keyword,
//            @RequestParam(required = false, defaultValue = "0") Integer status,
//            @RequestParam(required = false, defaultValue = "0") Integer time,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//
//        // Tạo đối tượng Pageable
//        Pageable pageable = PageRequest.of(page, size);
//
//        // Gọi phương thức từ Service để tìm kiếm Phòng Ban
//        Page<phongBanReponse> results = PBservice.searchPhongBan(keyword, status, time, pageable);
//
//        // Trả về kết quả
//        return ResponseEntity.ok(results);
//    }

    @PostMapping("/updateTrangThai/{id}")
    public String updateTrangThai(@PathVariable int id,
                                  @RequestParam(required = false) String keyword,
                                  @RequestParam(required = false, defaultValue = "0") Integer status,
                                  @RequestParam(required = false, defaultValue = "1") Integer time,
                                  @RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
                                  @RequestParam(value = "itemsPerPage", defaultValue = "5") int itemsPerPage) {
        // Tìm phòng ban theo ID
        PhongBan pb = PBservice.findById(id);
        if (pb != null) {
            // Cập nhật trạng thái
            if (pb.getTrangThai() == true) {
                pb.setTrangThai(false);

            } else {
                pb.setTrangThai(true);
            }
            // Cập nhật ngày chỉnh sửa hiện tại
            pb.setNgayCapNhat(Instant.now());
            PBservice.updatePhongBan(pb);
        }
        // Chuyển hướng về trang phân trang đang đứng
        return String.format("redirect:/phongBan?page=%d&itemsPerPage=%d&keyword=%s&status=%d&time=%d",
                currentPage, itemsPerPage, keyword != null ? keyword : "", status, time);
    }

    @PostMapping("/addPhongBan")
    public String addPhongBan(@RequestParam("ma_phong_ban") String maPhongBan,
                              @RequestParam("ten_phong_ban") String tenPhongBan,
                              @RequestParam("trang_thai") boolean trangThai,
                              @RequestParam(required = false) String keyword,
                              @RequestParam(required = false, defaultValue = "0") Integer status,
                              @RequestParam(required = false, defaultValue = "1") Integer time,
                              @RequestParam(value = "itemsPerPage", defaultValue = "10") int itemsPerPage) {
        PhongBan phongBan = new PhongBan();
        phongBan.setMaPhongBan(maPhongBan);
        phongBan.setTenPhongBan(tenPhongBan);
        phongBan.setTrangThai(trangThai);
        phongBan.setNgayTao(Instant.now());
        phongBan.setNgayCapNhat(Instant.now());

        // Lưu đối tượng PhongBan vào cơ sở dữ liệu
        PBservice.savePhongBan(phongBan);

        // Chuyển hướng về trang phân trang hiện tại
        return String.format("redirect:/phongBan?page=%d&itemsPerPage=%d&keyword=%s&status=%d&time=%d",
                0, itemsPerPage, keyword != null ? keyword : "", status, time);
    }

    @GetMapping("/checkMaPhongBan")
    @ResponseBody
    public ResponseEntity<Boolean> checkMaPhongBan(@RequestParam("maPhongBan") String maPhongBan) {
        boolean exists = PBservice.existsByMaPhongBan(maPhongBan);
        return ResponseEntity.ok(exists); // Trả về true nếu mã đã tồn tại, false nếu chưa tồn tại
    }

    @GetMapping("/deletePhongBan/{id}")
    public String deletePhongBan(@PathVariable int id,
                                 @RequestParam(required = false) String keyword,
                                 @RequestParam(required = false, defaultValue = "0") Integer status,
                                 @RequestParam(required = false, defaultValue = "1") Integer time,
                                 @RequestParam("currentPage") int currentPage,
                                 @RequestParam("itemsPerPage") int itemsPerPage) {
        try {
            // Kiểm tra và xóa phòng ban
            if (PBRepo.existsById(id)) {
                PBservice.deletePhongBan(id);
            }

            // Sau khi xóa, kiểm tra xem trang hiện tại có còn phần tử không
            Pageable pageable = PageRequest.of(currentPage, itemsPerPage);
            Page<phongBanReponse> phongBanPage = PBservice.getAllPage(pageable);

            // Nếu trang hiện tại không còn phần tử và không phải là trang đầu tiên
            if (phongBanPage.isEmpty() && currentPage > 0) {
                currentPage--;  // Giảm giá trị của currentPage về trang trước đó
            }

            // Chuyển hướng về trang phân trang hiện tại
            return String.format("redirect:/phongBan?page=%d&itemsPerPage=%d&keyword=%s&status=%d&time=%d",
                    currentPage, itemsPerPage, keyword != null ? keyword : "", status, time);

        } catch (DataIntegrityViolationException e) {
            // Xử lý ngoại lệ liên quan đến ràng buộc khóa ngoại
            // Có thể thêm một thông báo lỗi vào mô hình để hiển thị trên giao diện người dùng
            // Ví dụ: "Không thể xóa phòng ban này vì nó đang được sử dụng trong các bản ghi khác."

            // Thêm thông báo lỗi vào mô hình

            // Quay lại trang hiện tại với thông báo lỗi
            return String.format("redirect:/phongBan?page=%d&itemsPerPage=%d&keyword=%s&status=%d&time=%d",
                    currentPage, itemsPerPage, keyword != null ? keyword : "", status, time);
        }
    }

    @PostMapping("/updatePhongBan")
    public String updatePhongBan(@RequestParam("id") int id,
                                 @RequestParam("ten_phong_ban") String tenPhongBan,
                                 @RequestParam("trang_thai") Boolean trangThai,
                                 @RequestParam(required = false) String keyword,
                                 @RequestParam(required = false, defaultValue = "0") Integer status,
                                 @RequestParam(required = false, defaultValue = "1") Integer time,
                                 @RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
                                 @RequestParam(value = "itemsPerPage", defaultValue = "5") int itemsPerPage) {
        // Tìm phòng ban theo ID

//        System.out.println("id lỗi là " + id);
        PhongBan pb = PBservice.findById(id);
        if (pb!= null) {
//            PhongBan phongBan = optionalPhongBan.get();

            // Cập nhật tên phòng ban và trạng thái
//            pb.setId(id);
            pb.setTenPhongBan(tenPhongBan);
            pb.setTrangThai(trangThai);

            // Cập nhật ngày chỉnh sửa hiện tại
            pb.setNgayCapNhat(Instant.now());

            // Lưu lại thông tin sau khi chỉnh sửa
            PBservice.updatePhongBan(pb);
        }

        // Chuyển hướng về trang phân trang đang đứng
        return String.format("redirect:/phongBan?page=%d&itemsPerPage=%d&keyword=%s&status=%d&time=%d",
                currentPage, itemsPerPage, keyword != null ? keyword : "", status, time);
    }

    @GetMapping("/getPhongBanById/{id}")
    @ResponseBody
    public ResponseEntity<phongBanReponse> getPhongBanById(@PathVariable int id) {
        // Tìm phòng ban theo ID
        PhongBan pb = PBservice.findById(id);

        if (pb != null) {
            // Trả về dữ liệu phòng ban dưới dạng JSON
            phongBanReponse response = new phongBanReponse(pb.getId(), pb.getMaPhongBan(), pb.getTenPhongBan(), pb.getTrangThai(), pb.getNgayTao(), pb.getNgayCapNhat());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Trả về 404 nếu không tìm thấy phòng ban
        }
    }

    @GetMapping("/phongBan")
    public String viewPhongBan(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "0") Integer status,
            @RequestParam(required = false, defaultValue = "1") Integer time,
            @RequestParam(value = "itemsPerPage", defaultValue = "5") int itemsPerPage,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {

        // Sử dụng Pageable để hỗ trợ phân trang
        Pageable phantrang = PageRequest.of(page, itemsPerPage);

        // Tìm danh sách phòng ban với phân trang
        Page<phongBanReponse> phongBanPage = PBservice.searchPhongBan(keyword, status, time, phantrang);

        // Thêm vào model
        model.addAttribute("listPB", phongBanPage.getContent());
        model.addAttribute("currentPage", phongBanPage.getNumber()); // Trang hiện tại
        model.addAttribute("totalPages", phongBanPage.getTotalPages()); // Tổng số trang
        model.addAttribute("itemsPerPage", itemsPerPage); // Số phần tử trên mỗi trang
        model.addAttribute("totalElements", phongBanPage.getTotalElements()); // Tổng số phần tử
        model.addAttribute("keyword", keyword); // Để giữ lại từ khóa tìm kiếm
        model.addAttribute("status", status); // Để giữ lại trạng thái đã chọn
        model.addAttribute("time", time); // Để giữ lại thời gian đã chọn

        return "PhongBan";
    }


    @GetMapping("/exportExcel")
    public ResponseEntity<byte[]> exportExcel() throws IOException {
        // Tạo workbook Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Phong Ban");

        // Tạo dòng đầu tiên (header)
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Tên Phòng Ban");
        headerRow.createCell(2).setCellValue("Trạng Thái");

        // Giả sử có danh sách các phòng ban lấy từ DB
        List<PhongBan> phongBanList = PBservice.getAll();

        int rowNum = 1;
        for (PhongBan pb : phongBanList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(pb.getId());
            row.createCell(1).setCellValue(pb.getTenPhongBan());
            row.createCell(2).setCellValue(pb.getTrangThai() ? "Đang Hoạt Động" : "Ngừng Hoạt Động");
        }

        // Viết dữ liệu ra file Excel trong một mảng byte
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        byte[] excelData = outputStream.toByteArray();

        // Thiết lập response headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=phongBan.xlsx");

        return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
    }

    @PostMapping("/importExcel")
    public String importExcel(@RequestParam("file") MultipartFile file) {
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // Bỏ qua dòng tiêu đề (header)
                    continue;
                }
                int id = (int) row.getCell(0).getNumericCellValue();
                String tenPhongBan = row.getCell(1).getStringCellValue();
                boolean trangThai = row.getCell(2).getStringCellValue().equals("Đang Hoạt Động");

                // Tạo đối tượng PhongBan và lưu vào DB
                PhongBan pb = new PhongBan();
                PBservice.savePhongBan(pb);
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/phongBan?error=File không hợp lệ";
        }

        return "redirect:/phongBan?success=Nhập dữ liệu thành công";
    }
}
