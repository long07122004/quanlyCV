package com.example.quanlycv.Rep;

import com.example.quanlycv.entity.PhongBan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhongBanRepository extends JpaRepository<PhongBan,Integer> {
//@Query(value = """
//select new com.example.qlcv.dto.phongBanReponse(pb.id,pb.maPhongBan,pb.tenPhongBan,pb.trangThai,pb.ngayTao,pb.ngayCapNhat)
//from PhongBan pb where (pb.maPhongBan =:keyFind or pb.tenPhongBan =:keyFind) =:filter
//""",nativeQuery = true)
//    List<phongBanReponse> FindPhongBan(String keyFind , String filter);
}
