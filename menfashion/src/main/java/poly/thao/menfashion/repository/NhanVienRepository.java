package poly.thao.menfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.model.request.LoginReq;

import java.util.List;
import java.util.Optional;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {

    @Query("select kh from NhanVien kh where kh.trangThai != 2 order by kh.id desc ")
    List<NhanVien> findAll();

    @Query("select count(kh) > 0 from NhanVien kh where kh.ma = ?1 and kh.trangThai != 2")
    boolean existsByMa(String ma);

    @Query("select count(kh) > 0 from NhanVien kh where kh.tenDangNhap = ?1 and kh.trangThai != 2")
    boolean existsByTenDangNhap(String tdn);

    @Query("select count(kh) > 0 from NhanVien kh where kh.id  != ?2 and kh.ma = ?1 and kh.trangThai != 2")
    boolean existsByMa(String ma, Integer id);

    @Query("select count(kh) > 0 from NhanVien kh where kh.id  != ?2 and kh.tenDangNhap = ?1 and kh.trangThai != 2")
    boolean existsByTenDangNhap(String tdn, Integer id);

    @Query("select nv from NhanVien nv where nv.tenDangNhap = :#{#req.username} and nv.matKhau = :#{#req.password} and nv.trangThai != 2")
    Optional<NhanVien> findByTenDangNhapAndMatKhau(@Param("req") LoginReq req);

}
