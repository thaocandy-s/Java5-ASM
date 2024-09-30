package poly.thao.menfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import poly.thao.menfashion.entity.MauSac;
import poly.thao.menfashion.entity.NhanVien;

import java.util.List;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Integer> {

    @Query("select kh from MauSac kh where kh.trangThai != 2 order by kh.id desc ")
    List<MauSac> findAll();

    @Query("select count(kh) > 0 from MauSac kh where kh.ma = ?1 and kh.trangThai != 2")
    boolean existsByMa(String ma);

    @Query("select count(kh) > 0 from MauSac kh where kh.ten = ?1 and kh.trangThai != 2")
    boolean existsByTen(String ten);

    @Query("select count(kh) > 0 from MauSac kh where kh.id  != ?2 and kh.ma = ?1 and kh.trangThai != 2")
    boolean existsByMa(String ma, Integer id);

    @Query("select count(kh) > 0 from MauSac kh where kh.id  != ?2 and kh.ten = ?1 and kh.trangThai != 2")
    boolean existsByTen(String ten, Integer id);
}
