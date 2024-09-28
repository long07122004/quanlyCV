package com.example.quanlycv.restcontroller;

import com.example.quanlycv.Service.RolesService;
import com.example.quanlycv.entity.NhanVien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestRoleController {
    @Autowired
    RolesService rolesService;

//    @GetMapping("/admin/role/{id}")
//    public String getNhanVienID(@PathVariable("id") Integer id){
//        System.out.println("TestGetID: "+id);
//        NhanVien nv = rolesService.getNhanVienById(id);
//        System.out.println("TestGet dữ liệu id: "+nv);
//        return "name:"+id;
//    }

    @GetMapping("/admin/role/{id}")
    public NhanVien getNhanVienID(@PathVariable("id") Integer id){
        //System.out.println("TestGetID: "+id);
        NhanVien nv = rolesService.getNhanVienById(id);
        //System.out.println("TestGet dữ liệu id: "+nv);
        return nv;
    }
}
