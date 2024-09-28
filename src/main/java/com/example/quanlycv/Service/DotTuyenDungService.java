package com.example.quanlycv.Service;

import com.example.quanlycv.Entity.QlTuyenDung;
import com.example.quanlycv.Rep.TuyenDungRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DotTuyenDungService {

@Autowired
    private TuyenDungRep tuyenDungRep;

    public Page<QlTuyenDung> getTuyenDungPage(Pageable pageable) {
        return tuyenDungRep.findAll(pageable);
    }

//    public List<QlTuyenDung> findAll() {
//        return tuyenDungRep.findAll(); // Tìm tất cả các đợt tuyển dụng
//    }
    public QlTuyenDung saveDotTuyenDung(QlTuyenDung qlTuyenDung) {
        return tuyenDungRep.save(qlTuyenDung);
    }

    public void deleteDotTuyenDung(Integer id) {
        tuyenDungRep.deleteById(id);
    }

    public QlTuyenDung getTuyenDungById(Integer id) {
        return tuyenDungRep.findById(id)
                .orElseThrow(() -> new RuntimeException("Đợt tuyển dụng không tồn tại"));
    }

    public void update(Integer id, QlTuyenDung updatedTuyenDung) {
        QlTuyenDung existingTuyenDung = tuyenDungRep.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        existingTuyenDung.setMaDot(updatedTuyenDung.getMaDot());
        existingTuyenDung.setTenDot(updatedTuyenDung.getTenDot());
        existingTuyenDung.setNoiDung(updatedTuyenDung.getNoiDung());
        existingTuyenDung.setDeadline(updatedTuyenDung.getDeadline());
        // any other fields you want to update

        tuyenDungRep.save(existingTuyenDung);
    }

//    public List<QlTuyenDung> searchTuyenDung(String keyword) {
//        // Gọi phương thức tìm kiếm trong repository
//        return tuyenDungRep.searchByKeyword(keyword);
//    }

//    public List<QlTuyenDung> searchByKeyword(String keyword) {
//        // Logic tìm kiếm với keyword, có thể sử dụng các hàm của JPA hoặc Query.
//        return tuyenDungRep.findByMaDotContainingOrTenDotContainingOrNoiDungContaining(keyword, keyword, keyword,keyword);
//    }


}
