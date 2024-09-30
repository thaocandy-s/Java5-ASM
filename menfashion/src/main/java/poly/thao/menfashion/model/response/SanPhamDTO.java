package poly.thao.menfashion.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import poly.thao.menfashion.entity.SanPham;

@Getter
@Setter
@NoArgsConstructor
public class SanPhamDTO extends SanPham {
    private Integer soSanPhamChiTiet;
    public SanPhamDTO(SanPham sanPham, Integer soSanPhamChiTiet) {
        this.setId(sanPham.getId());
        this.setMa(sanPham.getMa());
        this.setTen(sanPham.getTen());
        this.setTrangThai(sanPham.getTrangThai());
        this.soSanPhamChiTiet = soSanPhamChiTiet;
    }
}
