package poly.thao.menfashion.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import poly.thao.menfashion.entity.SanPhamChiTiet;
import poly.thao.menfashion.model.EntityStatus;

@Getter
@Setter
@NoArgsConstructor
public class SanPhamChiTietDTO {

    private Integer id;

    private String ma;

    private String tenSanPham;

    private String tenKichThuoc;

    private String tenMauSac;

    private Integer soLuong;

    private Double donGia;

    private EntityStatus trangThai;

    public SanPhamChiTietDTO(Integer id, String ma, String tenSanPham, String tenKichThuoc, String tenMauSac, Integer soLuong, Double donGia, EntityStatus trangThai) {
        this.id = id;
        this.ma = ma;
        this.tenSanPham = tenSanPham;
        this.tenKichThuoc = tenKichThuoc;
        this.tenMauSac = tenMauSac;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.trangThai = trangThai;
    }

}
