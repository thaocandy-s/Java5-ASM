package poly.thao.menfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import poly.thao.menfashion.entity.KichThuoc;
import poly.thao.menfashion.entity.MauSac;

import java.util.List;

@Repository
public interface KichThuocRepository extends JpaRepository<KichThuoc, Integer> {

    @Query("select kh from KichThuoc kh where kh.trangThai != 2 order by kh.id desc ")
    List<KichThuoc> findAll();

    @Query("select count(kh) > 0 from KichThuoc kh where kh.ma = ?1 and kh.trangThai != 2")
    boolean existsByMa(String ma);

    @Query("select count(kh) > 0 from KichThuoc kh where kh.ten = ?1 and kh.trangThai != 2")
    boolean existsByTen(String ten);

    @Query("select count(kh) > 0 from KichThuoc kh where kh.id  != ?2 and kh.ma = ?1 and kh.trangThai != 2")
    boolean existsByMa(String ma, Integer id);

    @Query("select count(kh) > 0 from KichThuoc kh where kh.id  != ?2 and kh.ten = ?1 and kh.trangThai != 2")
    boolean existsByTen(String ten, Integer id);
}
