package com.example.quanlycv.controller;


import com.example.quanlycv.entity.*;
import com.example.quanlycv.repo.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class NhanVienController {

    @Autowired
    private NhanVienRepo nhanVienRepo;

    @Autowired
    private PhongBanRepo phongBanRepo;

    @Autowired
    private TruongPhongRepo truongPhongRepo;

    @Autowired
    private VaiTroRepo vaiTroRepo;

    @Autowired
    private viTriCongViecRepo viTriRepo;

    @GetMapping("/hien-thi")
    public String view(@RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 7);
        Page<NhanVien> nhanVienPage = nhanVienRepo.findAll(pageable);
        model.addAttribute("nhanVien", new NhanVien());
        model.addAttribute("list", nhanVienPage.getContent());
        model.addAttribute("currentPage", nhanVienPage.getNumber());
        model.addAttribute("totalPages", nhanVienPage.getTotalPages());
        model.addAttribute("listViTri", viTriRepo.findAll());
        model.addAttribute("listVaiTro", vaiTroRepo.findAll());
        model.addAttribute("listPhongBan", phongBanRepo.findAll());
        model.addAttribute("listTruongPhong", truongPhongRepo.findAll());
        return "nhan_vien/index";
    }

    @GetMapping("/xoa/{id}")
    public String delete(@PathVariable Integer id) {
        nhanVienRepo.deleteById(id);
        return "redirect:/nhan-vien/hien-thi?page=0";
    }


    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("nhanVien") NhanVien nhanVien,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listViTri", viTriRepo.findAll());
            model.addAttribute("listVaiTro", vaiTroRepo.findAll());
            model.addAttribute("listPhongBan", phongBanRepo.findAll());
            model.addAttribute("listTruongPhong", truongPhongRepo.findAll());
            return "nhan_vien/edit"; // Return to the edit page with errors
        }

        PhongBan phongBan = nhanVien.getPhongBan();
        if (phongBan != null && phongBan.getId() != null) {
            phongBan = phongBanRepo.findById(phongBan.getId()).orElse(null);
            nhanVien.setPhongBan(phongBan);
        }

        nhanVienRepo.save(nhanVien);
        return "redirect:/nhan-vien/hien-thi?page=0"; // Redirect after successful update
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("nhanVien") NhanVien nhanVien, BindingResult result, Model model) {
        // Load lists for dropdowns
        model.addAttribute("listViTri", viTriRepo.findAll());
        model.addAttribute("listVaiTro", vaiTroRepo.findAll());
        model.addAttribute("listPhongBan", phongBanRepo.findAll());
        model.addAttribute("listTruongPhong", truongPhongRepo.findAll());

        // Check for validation errors
        if (result.hasErrors()) {
            model.addAttribute("showModal", true); // Open modal on validation error
            return "nhan_vien/index"; // Return to the index page with existing data
        }

        PhongBan phongBan = nhanVien.getPhongBan();
        if (phongBan != null && phongBan.getId() != null) {
            phongBan = phongBanRepo.findById(phongBan.getId()).orElse(null);
            nhanVien.setPhongBan(phongBan);
        }

        // Save the new employee
        nhanVienRepo.save(nhanVien);
        return "redirect:/nhan-vien/hien-thi?page=0"; // Redirect on success
    }


    @GetMapping("/tim-kiem")
    public String timKiem(
            Model model,
            @RequestParam(value = "keyWord", required = false, defaultValue = "") String keyWord,
            @RequestParam(value = "page", defaultValue = "0") int page) {

        model.addAttribute("nhanVien", new NhanVien());

        Pageable pageable = PageRequest.of(page, 7);
        Page<NhanVien> nhanVienPage;

        if (keyWord != null && !keyWord.trim().isEmpty()) {
            nhanVienPage = nhanVienRepo.timKiemNhanVien(keyWord, pageable);
        } else {
            nhanVienPage = new PageImpl<>(new ArrayList<>(), pageable, 0);
        }

        model.addAttribute("list", nhanVienPage.getContent());
        model.addAttribute("currentPage", nhanVienPage.getNumber());
        model.addAttribute("totalPages", nhanVienPage.getTotalPages());

        model.addAttribute("listViTri", viTriRepo.findAll());
        model.addAttribute("listVaiTro", vaiTroRepo.findAll());
        model.addAttribute("listPhongBan", phongBanRepo.findAll());
        model.addAttribute("listTruongPhong", truongPhongRepo.findAll());

        model.addAttribute("keyWord", keyWord);

        return "nhan_vien/index";
    }

    @GetMapping("/filter")
    public String filter(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "role", required = false) String role,
            @RequestParam(value = "tenViTri", required = false) String tenViTri,
            Model model) {

        Pageable pageable = PageRequest.of(page, 7);
        Page<NhanVien> nhanVienPage;

        if (role != null && !role.isEmpty() && tenViTri != null && !tenViTri.isEmpty()) {
            nhanVienPage = nhanVienRepo.filter(role, tenViTri, pageable);
        } else if (role != null && !role.isEmpty()) {
            nhanVienPage = nhanVienRepo.filterByRole(role, pageable);
        } else if (tenViTri != null && !tenViTri.isEmpty()) {
            nhanVienPage = nhanVienRepo.filterByViTri(tenViTri, pageable);
        } else {
            nhanVienPage = nhanVienRepo.findAll(pageable);
        }

        model.addAttribute("list", nhanVienPage.getContent());
        model.addAttribute("currentPage", nhanVienPage.getNumber());
        model.addAttribute("totalPages", nhanVienPage.getTotalPages());
        model.addAttribute("nhanVien", new NhanVien());

        model.addAttribute("listViTri", viTriRepo.findAll());
        model.addAttribute("listVaiTro", vaiTroRepo.findAll());
        model.addAttribute("listPhongBan", phongBanRepo.findAll());
        model.addAttribute("listTruongPhong", truongPhongRepo.findAll());

        model.addAttribute("selectedRole", role);
        model.addAttribute("selectedViTri", tenViTri);

        return "nhan_vien/index";
    }

    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) {
        try {
            // Set the content type and the header for downloading the file
            response.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=Danh sách nhân viên.xlsx";
            response.setHeader(headerKey, headerValue);

            List<NhanVien> nhanVienList = nhanVienRepo.findAll();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("NhanVien");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Mã NV");
            headerRow.createCell(1).setCellValue("Họ Tên");
            headerRow.createCell(2).setCellValue("Email");
            headerRow.createCell(3).setCellValue("Số ĐT");
            headerRow.createCell(4).setCellValue("Vị Trí");
            headerRow.createCell(5).setCellValue("Trưởng Phòng");
            headerRow.createCell(6).setCellValue("Phòng Ban");
            headerRow.createCell(7).setCellValue("Vai Trò");
            headerRow.createCell(8).setCellValue("Trạng Thái");

            int rowCount = 1;
            for (NhanVien nhanVien : nhanVienList) {
                Row row = sheet.createRow(rowCount++);

                row.createCell(0).setCellValue(nhanVien.getMa());
                row.createCell(1).setCellValue(nhanVien.getHoTen());
                row.createCell(2).setCellValue(nhanVien.getEmail());
                row.createCell(3).setCellValue(nhanVien.getSdt());
                row.createCell(4).setCellValue(nhanVien.getViTri() != null ? nhanVien.getViTri().getTenViTri() : "");
                row.createCell(5).setCellValue(nhanVien.getVaiTro() != null ? nhanVien.getVaiTro().getTenVaiTro() : "");
                row.createCell(6).setCellValue(nhanVien.getTruongPhong() != null ? nhanVien.getTruongPhong().getTenTruongPhong() : "");
                row.createCell(7).setCellValue(nhanVien.getPhongBan() != null ? nhanVien.getPhongBan().getTenPhongBan() : "");
                row.createCell(8).setCellValue(nhanVien.getTrang_thai() ? "Active" : "Inactive");
            }

            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @PostMapping("/import")
    public String importNhanVien(@RequestParam("file") MultipartFile file) {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            List<NhanVien> nhanVienList = new ArrayList<>();

            for (Row currentRow : sheet) {
                if (currentRow.getRowNum() == 0) continue;
                NhanVien nhanVien = new NhanVien();
                nhanVien.setMa(getStringCellValue(currentRow.getCell(2)));
                nhanVien.setHoTen(getStringCellValue(currentRow.getCell(3)));
                nhanVien.setEmail(getStringCellValue(currentRow.getCell(4)));
                nhanVien.setSdt(getStringCellValue(currentRow.getCell(5)));

                String viTriName = getStringCellValue(currentRow.getCell(6));
                String vaiTroName = getStringCellValue(currentRow.getCell(7));
                String truongPhongName = getStringCellValue(currentRow.getCell(8));
                String phongBanName = getStringCellValue(currentRow.getCell(9));
                Boolean trangThai = getBooleanCellValue(currentRow.getCell(10));

                TruongPhong truongPhong = truongPhongRepo.findByName(truongPhongName);
                nhanVien.setTruongPhong(truongPhong);

                ViTriCongViec viTri = viTriRepo.findByName(viTriName);
                nhanVien.setViTri(viTri);

                VaiTro vaiTro = vaiTroRepo.findByName(vaiTroName);
                nhanVien.setVaiTro(vaiTro);

                PhongBan phongBan = phongBanRepo.findByName(phongBanName);
                nhanVien.setPhongBan(phongBan);

                nhanVien.setTrang_thai(trangThai);

                nhanVienList.add(nhanVien);
            }

            nhanVienRepo.saveAll(nhanVienList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/nhan-vien/hien-thi?page=0";
    }

    @PostMapping("/qr")
    public String addNhanVienFromQR(@RequestParam("file") String qrData, Model model) {
        try {
            // Thay vì đọc từ file, ta lấy dữ liệu trực tiếp từ qrData
            String text = qrData;

            // Parse the plain text
            NhanVien nhanVien = new NhanVien();
            String[] lines = text.split("\n");

            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    switch (key) {
                        case "MaNV":
                            nhanVien.setMa(value);
                            break;
                        case "HoTen":
                            nhanVien.setHoTen(value);
                            break;
                        case "Email":
                            nhanVien.setEmail(value);
                            break;
                        case "Sdt":
                            nhanVien.setSdt(value);
                            break;
                        case "ViTri":
                            ViTriCongViec viTri = viTriRepo.findByName(value);
                            nhanVien.setViTri(viTri);
                            break;
                        case "VaiTro":
                            VaiTro vaiTro = vaiTroRepo.findByName(value);
                            nhanVien.setVaiTro(vaiTro);
                            break;
                        case "PhongBan":
                            PhongBan phongBan = phongBanRepo.findByName(value);
                            nhanVien.setPhongBan(phongBan);
                            break;
                        case "TruongPhong":
                            TruongPhong truongPhong = truongPhongRepo.findByName(value);
                            nhanVien.setTruongPhong(truongPhong);
                            break;
                        case "TrangThai":
                            nhanVien.setTrang_thai("Active".equalsIgnoreCase(value));
                            break;
                        default:
                            break;
                    }
                }
            }

            // Save the new employee
            nhanVienRepo.save(nhanVien);
            model.addAttribute("message", "Thêm nhân viên thành công!");

        } catch (Exception e) {
            model.addAttribute("message", "Có lỗi xảy ra: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/nhan-vien/hien-thi?page=0";
    }



    private String getStringCellValue(Cell cell) {
        if (cell != null) {
            if (cell.getCellType() == CellType.STRING) {
                return cell.getStringCellValue();
            } else if (cell.getCellType() == CellType.NUMERIC) {
                return String.valueOf(cell.getNumericCellValue());
            }
        }
        return "";
    }

    private Boolean getBooleanCellValue(Cell cell) {
        if (cell != null && cell.getCellType() == CellType.BOOLEAN) {
            return cell.getBooleanCellValue();
        }
        return null;
    }

}
