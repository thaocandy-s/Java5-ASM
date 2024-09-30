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
public class HoaDonReq {

    public Integer id;

    public Integer idKhachHang;

    public Integer idNhanVien;


}
