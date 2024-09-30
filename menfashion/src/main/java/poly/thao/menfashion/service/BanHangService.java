package poly.thao.menfashion.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.thao.menfashion.entity.HoaDon;
import poly.thao.menfashion.entity.HoaDonChiTiet;
import poly.thao.menfashion.entity.KhachHang;
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.entity.SanPhamChiTiet;
import poly.thao.menfashion.model.EntityStatus;
import poly.thao.menfashion.model.request.Cart;
import poly.thao.menfashion.model.request.SanPhamsInCart;
import poly.thao.menfashion.model.response.ResponseObject;

import java.util.List;
import java.util.Map;

@Service
public class BanHangService {

    public Double getAmount(Map<SanPhamChiTiet, Integer> mapSP){
        Double amount = 0D;
        for (Map.Entry<SanPhamChiTiet, Integer> entry: mapSP.entrySet()){
            amount += entry.getKey().getDonGia()*entry.getValue();
        }
        return amount;
    }
}
