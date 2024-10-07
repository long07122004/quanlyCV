package com.example.quanlycv.restcontroller;

import com.example.quanlycv.Service.RolesService;
import com.example.quanlycv.dto.NguoiDungDTO;
import com.example.quanlycv.entity.NguoiDung;
import com.example.quanlycv.entity.NhanVien;
import com.example.quanlycv.entity.VaiTro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class RestRoleController {
    @Autowired
    RolesService rolesService;


    @GetMapping("/admin/role/{id}")
    public NhanVien getNhanVienID(@PathVariable("id") Integer id){
        NhanVien nv = rolesService.getNhanVienById(id);
        return nv;
    }

    @GetMapping("/admin/role/edit/{id}")
    public NguoiDung edit(@PathVariable("id") Integer id, Model model){
        NguoiDung nv = rolesService.getNguoiDungById(id);
        return nv;
    }

//    @PostMapping("/admin/role/add")
//    public NguoiDung save(@ModelAttribute("formUser") NguoiDungDTO nguoiDung){
//
//        NhanVien nhanVien = null;
//        if(nguoiDung.getNhanVienId() != null){
//            nhanVien = new NhanVien(nguoiDung.getNhanVienId(),"","","",null,null,null,null,"",null,null);
//        }
//        NguoiDung nguoiDungEntity = new NguoiDung(
//                nguoiDung.getId(),nguoiDung.getHoTen(),nguoiDung.getEmail(),nguoiDung.getSdt(),
//                new VaiTro(nguoiDung.getVaiTroId(),""),nguoiDung.getPassword(),
//                nhanVien,
//                nguoiDung.getTrangThai()
//        );
//        System.out.println("Test data add: "+nguoiDungEntity);
//        //rolesService.saveRoles(nguoiDungEntity);
//        return  nguoiDungEntity;
//    }
}
