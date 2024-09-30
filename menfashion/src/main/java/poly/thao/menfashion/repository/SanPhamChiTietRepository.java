package poly.thao.menfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import poly.thao.menfashion.entity.SanPhamChiTiet;
import poly.thao.menfashion.model.response.SanPhamChiTietDTO;

import java.util.List;

@Repository
public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet, Integer> {

    @Query("select kh from SanPhamChiTiet kh where kh.trangThai != 2 order by kh.id desc ")
    List<SanPhamChiTiet > findAll();

    @Query("SELECT new poly.thao.menfashion.model.response.SanPhamChiTietDTO(" +
            "spct.id, " +
            "spct.ma, " +
            "spct.sanPham.ten, " +
            "spct.kichThuoc.ten, " +
            "spct.mauSac.ten, " +
            "spct.soLuong, " +
            "spct.donGia, " +
            "spct.trangThai) " +
            "FROM SanPhamChiTiet spct " +
            "join SanPham sp on sp.id = spct.sanPham.id " +
            "join MauSac ms on ms.id = spct.mauSac.id " +
            "join KichThuoc kt on kt.id = spct.kichThuoc.id " +
            "where spct.trangThai != 2 and ms.trangThai != 2 and kt.trangThai != 2 and sp.trangThai !=2 " +
            "ORDER BY spct.id DESC")
    List<SanPhamChiTietDTO> getSPCTDTO();

    @Query("select spct from SanPhamChiTiet spct " +
            "join SanPham sp on sp.id = spct.sanPham.id " +
            "join MauSac ms on ms.id = spct.mauSac.id " +
            "join KichThuoc kt on kt.id = spct.kichThuoc.id " +
            "where spct.trangThai = 0 and ms.trangThai = 0 and kt.trangThai = 0 and sp.trangThai = 0"
    )
    List<SanPhamChiTiet> getListSPCTSale();

    @Query("select count(kh) > 0 from SanPhamChiTiet kh where kh.ma = ?1 and kh.trangThai != 2")
    boolean existsByMa(String ma);

    @Query("select count(kh) > 0 from SanPhamChiTiet kh where kh.id  != ?2 and kh.ma = ?1 and kh.trangThai != 2")
    boolean existsByMa(String ma, Integer id);

    @Query("select spct from SanPhamChiTiet spct where spct.sanPham.id = ?1 and spct.trangThai != 2")
    List<SanPhamChiTiet> findAllSanPhamChiTietBySanPhamId(Integer id);

    @Query("select count(*) from SanPhamChiTiet spct where spct.sanPham.id = ?1 and spct.trangThai != 2")
    Integer countSPCTbySanPhamId(Integer id);



}
