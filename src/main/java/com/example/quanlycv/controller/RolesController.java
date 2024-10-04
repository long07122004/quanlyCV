package com.example.quanlycv.controller;

import com.example.quanlycv.Service.RolesService;
import com.example.quanlycv.dto.NguoiDungDTO;
import com.example.quanlycv.entity.NguoiDung;
import com.example.quanlycv.entity.NhanVien;
import com.example.quanlycv.entity.VaiTro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
        if(keyword == null || keyword==""){
            List<NhanVien> listNV = rolesService.getALLNhanVIen();
            model.addAttribute("listNV", listNV);
            NguoiDungDTO nguoiDung = new NguoiDungDTO();
            model.addAttribute("formUser", nguoiDung);


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

        return  "roles/index-role";
    }

    @ModelAttribute(name = "VAITRO")
    public List<VaiTro> getAllVaiTro(){
        return rolesService.getAllVaiTro();
    }

    @PostMapping("/admin/role/add")
    public String save(@ModelAttribute("formUser") NguoiDungDTO nguoiDung, RedirectAttributes redirectAttributes){
        //System.out.println("Show Test Form: "+nguoiDung);
        NguoiDung nguoiDungEntity = new NguoiDung(
            nguoiDung.getId(),nguoiDung.getHoTen(),nguoiDung.getEmail(),nguoiDung.getSdt(),nguoiDung.getPassword(),
                new VaiTro(nguoiDung.getVaiTroId(),""),
                new NhanVien(nguoiDung.getNhanVienId(),"","","",null,null,null,null,"",null,null),
                nguoiDung.getTrangThai()
        );
        //System.out.println("Test người dùng entity: "+nguoiDungEntity);
        rolesService.saveRoles(nguoiDungEntity);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm người dùng thành công!");
        return  "redirect:/admin/role";
    }

    @GetMapping("/admin/role/delete/{id}")
    public String delete(Model model,@PathVariable("id") Integer id,RedirectAttributes redirectAttributes){
        //System.out.println("Test người dung: "+rolesService.getNguoiDungById(id));
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

}
