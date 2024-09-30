package poly.thao.menfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import poly.thao.menfashion.entity.KhachHang;

import java.util.List;
import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {

    @Query("select kh from KhachHang kh where kh.trangThai != 2 order by kh.id desc ")
    List<KhachHang> findAll();

    @Query("select kh from KhachHang kh where kh.trangThai != 2 and kh.ma = ?1")
    Optional<KhachHang> findByMa(String ma);

    @Query("select count(kh) > 0 from KhachHang kh where kh.ma = ?1 and kh.trangThai != 2")
    boolean existsByMa(String ma);

    @Query("select count(kh) > 0 from KhachHang kh where kh.id  != ?2 and kh.ma = ?1 and kh.trangThai != 2")
    boolean existsByMa(String ma, Integer id);

}
