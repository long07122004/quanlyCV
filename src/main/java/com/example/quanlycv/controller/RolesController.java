package com.example.quanlycv.controller;

import com.example.quanlycv.Service.RolesService;
import com.example.quanlycv.dto.NguoiDungDTO;
import com.example.quanlycv.entity.NguoiDung;
import com.example.quanlycv.entity.NhanVien;
import com.example.quanlycv.entity.VaiTro;
import com.example.quanlycv.entity.VaiTroQuyenTruyCap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class RolesController {
    @Autowired
    RolesService rolesService;



//    @GetMapping("/admin/role")
//    public String viewRole(Model model){
//        //người dùng
//        List<NguoiDung> listND = rolesService.getAll();
//        model.addAttribute("listND", listND);
//        // nhân viên
//        List<NhanVien> listNV = rolesService.getALLNhanVIen();
//        model.addAttribute("listNV", listNV);
//
//        NguoiDungDTO nguoiDung = new NguoiDungDTO();
//        model.addAttribute("formUser", nguoiDung);
//        return  "roles/index-role";
//    }

    @GetMapping("/admin/role")
    public String viewRolePagination(Model model,
                   @RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
                   @Param("keyword") String keyword){
        if(keyword != null ){
            List<NhanVien> listNV = rolesService.getALLNhanVIen();
            model.addAttribute("listNV", listNV);
            NguoiDungDTO nguoiDung = new NguoiDungDTO();
            model.addAttribute("formUser", nguoiDung);
            Page<NguoiDung> listND = rolesService.getAllPagination(pageNo);
            model.addAttribute("totalPage",listND.getTotalPages());
            model.addAttribute("curentPage",pageNo);
            model.addAttribute("listND", listND);

        }else {
            // nhân viên
            List<NhanVien> listNV = rolesService.getALLNhanVIen();
            model.addAttribute("listNV", listNV);

            NguoiDungDTO nguoiDung = new NguoiDungDTO();
            model.addAttribute("formUser", nguoiDung);

            Page<NguoiDung> listND = rolesService.getAllPagination(pageNo);
            model.addAttribute("totalPage",listND.getTotalPages());
            model.addAttribute("curentPage",pageNo);
            model.addAttribute("listND", listND);
        }

        List<VaiTroQuyenTruyCap> vtqtcList = rolesService.findAllVTQCT();
        Map<String, String> groupedRoles = rolesService.getGroupedRoles(vtqtcList);
        System.out.println("group: "+groupedRoles);
        model.addAttribute("VTQTC",groupedRoles);
        return  "roles/index-role";
    }

    @ModelAttribute(name = "VAITRO")
    public List<VaiTro> getAllVaiTro(){
        return rolesService.getAllVaiTro();
    }

    @PostMapping("/admin/role/add")
    public String save(@ModelAttribute("formUser") NguoiDungDTO nguoiDung, RedirectAttributes redirectAttributes){
        try{
            NhanVien nhanVien = null;
            if(nguoiDung.getNhanVienId() != null){
                nhanVien = new NhanVien(nguoiDung.getNhanVienId(),"","","",null,null,null,null,"",null,null);
            }
            NguoiDung nguoiDungEntity = new NguoiDung(
                    nguoiDung.getId(),nguoiDung.getHoTen(),nguoiDung.getEmail(),nguoiDung.getSdt(),nguoiDung.getPassword(),
                    new VaiTro(nguoiDung.getVaiTroId(),""),
                    nhanVien,
                    nguoiDung.getTrangThai()
            );

            rolesService.saveRoles(nguoiDungEntity);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm người dùng thành công!");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Thêm người dùng thất bại!" +e);
        }

        return  "redirect:/admin/role";
    }

    @PostMapping("/admin/role/update/{id}")
    public String update(@ModelAttribute("formUser") NguoiDungDTO nguoiDung,@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        NguoiDung currentNguoiDung = rolesService.findById(nguoiDung.getId());
        //System.out.println("Test người dùng: "+id);

        //kiểm tra email đã tồn tại
        if (!nguoiDung.getEmail().equals(currentNguoiDung.getEmail()) && rolesService.emailExists(nguoiDung.getEmail(), nguoiDung.getId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email đã tồn tại!");
            return "redirect:/admin/role";
        }

        NhanVien nhanVien = null;
        if(nguoiDung.getNhanVienId() != null){
            //System.out.println("nhan vien ID: "+nguoiDung.getNhanVienId());
            nhanVien = new NhanVien(nguoiDung.getNhanVienId(),"","","",null,null,null,null,"",null,null);
        }

        currentNguoiDung.setId(nguoiDung.getId());
        currentNguoiDung.setHoTen(nguoiDung.getHoTen());
        currentNguoiDung.setEmail(nguoiDung.getEmail());
        currentNguoiDung.setSdt(nguoiDung.getSdt());
        currentNguoiDung.setVaiTro(new VaiTro(nguoiDung.getVaiTroId(),""));
        currentNguoiDung.setNhanVien(nhanVien);
        currentNguoiDung.setTrangThai(nguoiDung.getTrangThai());

        rolesService.update(currentNguoiDung);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật người dùng thành công!");

        return  "redirect:/admin/role";
    }

    @GetMapping("/admin/role/delete/{id}")
    public String delete(Model model,@PathVariable("id") Integer id,RedirectAttributes redirectAttributes){
        if(rolesService.getNguoiDungById(id) == null){
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy người dùng để xóa!");
            return "redirect:/admin/role";
        }

        try{
            rolesService.deleteRoles(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xoá người dùng thành công!");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Xoá người dùng thất bại! "+e);
        }
        return  "redirect:/admin/role";
    }

    @GetMapping("/admin/role/change-status/{id}")
    public String changeStatus(Model model,@PathVariable("id") Integer id,RedirectAttributes redirectAttributes){

        if(rolesService.getNguoiDungById(id) == null){
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy người dùng!");
            return "redirect:/admin/role";
        }

        NguoiDung nd = rolesService.getNguoiDungById(id);
        nd.setId(id);
        System.out.println("get Trang Thai: "+nd.getTrangThai());
        if(nd.getTrangThai()){
            nd.setTrangThai(false);
        }else {
            nd.setTrangThai(true);
        }
        rolesService.update(nd);

        return  "redirect:/admin/role";
    }



}
