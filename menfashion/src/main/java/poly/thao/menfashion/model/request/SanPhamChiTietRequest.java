package poly.thao.menfashion.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import poly.thao.menfashion.model.EntityStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SanPhamChiTietRequest {

    private Integer id;

    private String ma;

    private Integer idSanPham;

    private Integer idKichThuoc;

    private Integer idMauSac;

    private Integer soLuong;

    private Double donGia;

    private EntityStatus trangThai;
}
