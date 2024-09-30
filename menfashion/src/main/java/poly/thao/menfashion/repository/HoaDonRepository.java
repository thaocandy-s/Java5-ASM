package poly.thao.menfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import poly.thao.menfashion.entity.HoaDon;
import poly.thao.menfashion.entity.MauSac;

import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon , Integer> {

    @Query("select hd from HoaDon hd where hd.trangThai != 2 order by hd.id desc ")
    List<HoaDon> findAll();

    @Query("select sum(hdct.donGia) " +
            "from HoaDon hd " +
            "join HoaDonChiTiet hdct on hd.id = hdct.hoaDon.id " +
            "where hd.trangThai != 2 " +
            "group by hd.id " +
            "order by hd.id desc")
    List<Double> listTongGiaHoaDon();

}
