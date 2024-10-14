package com.example.quanlycv.restcontroller;

import com.example.quanlycv.Service.RolesService;
import com.example.quanlycv.entity.NguoiDung;
import com.example.quanlycv.entity.NhanVien;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

//    @GetMapping("/admin/role/edit/quyen-truy-cap/{id}")
//    public List<VaiTroQuyenTruyCap> RolesPermist(@PathVariable("id") String id, Model model){
//        List<VaiTroQuyenTruyCap> newVT = rolesService.findAllVTQCT(id);
//        System.out.println("newVT: "+newVT);
//        List<VaiTroQuyenTruyCap> test = rolesService.findAllVTQCT(id);
//        return test;
//    }



}
