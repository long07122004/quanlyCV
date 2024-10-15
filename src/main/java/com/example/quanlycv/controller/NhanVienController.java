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
@RequestMapping("/nhan-vien")
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
        return "nhanvien";
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

//        // Check for validation errors
//        if (result.hasErrors()) {
//            model.addAttribute("showModal", true); // Open modal on validation error
//            return "nhanvien"; // Return to the index page with existing data
//        }

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

        return "nhanvien";
    }

    @GetMapping("/filter")
    public String filter(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "role", required = false) String role,
            @RequestParam(value = "tenViTri", required = false) String tenViTri,
            Model model) {

        Pageable pageable = PageRequest.of(page, 7);
        Page<NhanVien> nhanVienPage;

        // Kiểm tra nếu có cả hai tiêu chí
        if (role != null && !role.isEmpty() && tenViTri != null && !tenViTri.isEmpty()) {
            nhanVienPage = nhanVienRepo.filter(role, tenViTri, pageable);
        }
        // Nếu chỉ có vai trò
        else if (role != null && !role.isEmpty()) {
            nhanVienPage = nhanVienRepo.filterByRole(role, pageable);
        }
        // Nếu chỉ có vị trí
        else if (tenViTri != null && !tenViTri.isEmpty()) {
            nhanVienPage = nhanVienRepo.filterByViTri(tenViTri, pageable);
        }
        // Nếu không có tiêu chí nào, lấy tất cả
        else {
            nhanVienPage = nhanVienRepo.findAll(pageable);
        }

        // Truyền dữ liệu ra view
        model.addAttribute("list", nhanVienPage.getContent());
        model.addAttribute("currentPage", nhanVienPage.getNumber());
        model.addAttribute("totalPages", nhanVienPage.getTotalPages());
        model.addAttribute("nhanVien", new NhanVien());
        model.addAttribute("listViTri", viTriRepo.findAll());
        model.addAttribute("listVaiTro", vaiTroRepo.findAll());

        // Truyền giá trị của bộ lọc đã chọn để hiển thị lại trong UI
        model.addAttribute("selectedRole", role);
        model.addAttribute("selectedViTri", tenViTri);

        return "nhanvien";
    }


    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) {
        // Set the content type and header for the response
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Danh_sach_nhan_vien.xlsx";
        response.setHeader(headerKey, headerValue);

        // Initialize the workbook and sheet
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("NhanVien");

            // Create the header row
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

            // Fetch employee data
            List<NhanVien> nhanVienList = nhanVienRepo.findAll();

            // Fill the sheet with employee data
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

            // Write the workbook to the output stream
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            // Log the error and send a response indicating the error
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                response.getWriter().write("Error occurred while generating the Excel file.");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @PostMapping("/import")
    public String importNhanVien(@RequestParam("file") MultipartFile file) {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            List<NhanVien> nhanVienList = new ArrayList<>();

            for (Row currentRow : sheet) {
                if (currentRow.getRowNum() == 0) continue; // Skip header row

                NhanVien nhanVien = new NhanVien();
                nhanVien.setMa(getStringCellValue(currentRow.getCell(0)));
                nhanVien.setHoTen(getStringCellValue(currentRow.getCell(1)));
                nhanVien.setEmail(getStringCellValue(currentRow.getCell(2)));
                nhanVien.setSdt(getStringCellValue(currentRow.getCell(3)));

                String viTriName = getStringCellValue(currentRow.getCell(4));
                String truongPhongName = getStringCellValue(currentRow.getCell(5));
                String phongBanName = getStringCellValue(currentRow.getCell(6));
                String vaiTroName = getStringCellValue(currentRow.getCell(7));
                Boolean trangThai = getBooleanCellValue(currentRow.getCell(8));

                // Debug logging to check values being read
                System.out.println("Processing row: " + currentRow.getRowNum());
                System.out.println("ViTri: " + viTriName);
                System.out.println("TruongPhong: " + truongPhongName);
                System.out.println("PhongBan: " + phongBanName);
                System.out.println("VaiTro: " + vaiTroName);
                System.out.println("TrangThai: " + trangThai);

                // Fetch and set ViTri
                ViTriCongViec viTri = (viTriName != null && !viTriName.isEmpty()) ? viTriRepo.findByName(viTriName) : null;
                if (viTri != null) {
                    nhanVien.setViTri(viTri);
                } else {
                    System.err.println("ViTri not found: " + viTriName);
                }

                // Fetch and set TruongPhong
                TruongPhong truongPhong = (truongPhongName != null && !truongPhongName.isEmpty()) ? truongPhongRepo.findByName(truongPhongName) : null;
                if (truongPhong != null) {
                    nhanVien.setTruongPhong(truongPhong);
                } else {
                    System.err.println("TruongPhong not found: " + truongPhongName);
                }

                // Fetch and set PhongBan
                PhongBan phongBan = (phongBanName != null && !phongBanName.isEmpty()) ? phongBanRepo.findByName(phongBanName) : null;
                if (phongBan != null) {
                    nhanVien.setPhongBan(phongBan);
                } else {
                    System.err.println("PhongBan not found: " + phongBanName);
                }

                // Fetch and set VaiTro
                VaiTro vaiTro = (vaiTroName != null && !vaiTroName.isEmpty()) ? vaiTroRepo.findByName(vaiTroName) : null;
                if (vaiTro != null) {
                    nhanVien.setVaiTro(vaiTro);
                } else {
                    System.err.println("VaiTro not found: " + vaiTroName);
                }

                // Set trangThai
                nhanVien.setTrang_thai(trangThai != null ? trangThai : false); // Default to false if null

                // Add to the list
                nhanVienList.add(nhanVien);
            }

// Save all valid NhanVien objects to the database
            nhanVienRepo.saveAll(nhanVienList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/nhan-vien/hien-thi?page=0";
    }

    // Helper method to get a String value from the cell
    private String getStringCellValue(Cell cell) {
        if (cell != null) {
            return cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : String.valueOf(cell.getNumericCellValue());
        }
        return "";
    }

    // Helper method to get a Boolean value from the cell
    private Boolean getBooleanCellValue(Cell cell) {
        if (cell != null) {
            if (cell.getCellType() == CellType.BOOLEAN) {
                return cell.getBooleanCellValue();
            } else if (cell.getCellType() == CellType.STRING) {
                return "TRUE".equalsIgnoreCase(cell.getStringCellValue().trim());
            }
        }
        return null;
    }

}
