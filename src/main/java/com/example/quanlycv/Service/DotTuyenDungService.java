package com.example.quanlycv.Service;

import com.example.quanlycv.entity.QlTuyenDung;
import com.example.quanlycv.repo.TuyenDungRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DotTuyenDungService {

@Autowired
    private TuyenDungRep tuyenDungRep;

//    public Page<QlTuyenDung> getTuyenDungPage(Pageable pageable) {
//        return tuyenDungRep.findAll(pageable);
//    }

    public Page<QlTuyenDung> getAllPagination(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1,3);
        return this.tuyenDungRep.findAll(pageable);
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

    public QlTuyenDung update(Integer id, QlTuyenDung updatedTuyenDung) {


        return tuyenDungRep.findById(id)
                .map(existingTuyenDung -> {
                    // Cập nhật thông tin đối tượng
                    existingTuyenDung.setMaDot(updatedTuyenDung.getMaDot());
                    existingTuyenDung.setTenDot(updatedTuyenDung.getTenDot());
                    existingTuyenDung.setNoiDung(updatedTuyenDung.getNoiDung());
                    existingTuyenDung.setDeadline(updatedTuyenDung.getDeadline());
                    // Lưu lại đối tượng sau khi cập nhật
                    return tuyenDungRep.save(existingTuyenDung);
                }).orElseThrow(() -> new RuntimeException("DotTuyenDung với ID " + id + " không tồn tại"));

    }



    public Page<QlTuyenDung> search(String keyword, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        if (keyword != null && !keyword.isEmpty()) {
            return tuyenDungRep.searchByKeyword(keyword.toLowerCase(), pageable);
        }
        return tuyenDungRep.findAll(pageable);
    }


}
