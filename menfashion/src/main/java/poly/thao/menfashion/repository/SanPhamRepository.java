package poly.thao.menfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import poly.thao.menfashion.entity.SanPham;
import poly.thao.menfashion.entity.SanPhamChiTiet;

import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {

    @Query("select kh from SanPham kh where kh.trangThai != 2 order by kh.id desc ")
    List<SanPham> findAll();

    @Query("select count(kh) > 0 from SanPham kh where kh.ma = ?1 and kh.trangThai != 2")
    boolean existsByMa(String ma);

    @Query("select count(kh) > 0 from SanPham kh where kh.id  != ?2 and kh.ma = ?1 and kh.trangThai != 2")
    boolean existsByMa(String ma, Integer id);

    @Query("select spct from SanPhamChiTiet spct where spct.sanPham.id = ?1 and spct.trangThai != 2")
    List<SanPhamChiTiet> findAllSanPhamChiTietBySanPhamId(Integer id);

    @Query("select count(*) from SanPhamChiTiet spct where spct.sanPham.id = ?1 and spct.trangThai != 2")
    Integer countSPCTbySanPhamId(Integer id);



}
