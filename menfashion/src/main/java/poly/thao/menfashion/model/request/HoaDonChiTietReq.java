package poly.thao.menfashion.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HoaDonChiTietReq {
    public Integer id;
    public Integer idHoaDon;
    public Integer idSanPhamChiTiet;
    public Integer soLuong;

}
