package poly.thao.menfashion.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import poly.thao.menfashion.entity.HoaDon;
import poly.thao.menfashion.entity.HoaDonChiTiet;
import poly.thao.menfashion.utils.helper.Helper;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HoaDonResponse {

    private HoaDon hoaDon;

    private Double tongGia;

    private List<HoaDonChiTiet> listHDCT;

    public HoaDonResponse(List<HoaDonChiTiet> listHDCT, Double tongGia) {
        this.listHDCT = listHDCT;
        this.hoaDon = listHDCT.get(0).hoaDon;
        this.tongGia = tongGia;
    }
}
