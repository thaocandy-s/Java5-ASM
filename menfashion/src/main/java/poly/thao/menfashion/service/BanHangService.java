package poly.thao.menfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @Autowired
    private NhanVienService nhanVienService;

    public Double getAmount(Map<SanPhamChiTiet, Integer> mapSP){
        Double amount = 0D;
        for (Map.Entry<SanPhamChiTiet, Integer> entry: mapSP.entrySet()){
            amount += entry.getKey().getDonGia()*entry.getValue();
        }
        return amount;
    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseObject<String> pay(Cart cart) {
        try {

            if (cart.idKhachHang == null){
                throw new RuntimeException("idKhachHang must not null");
            }
            if (cart.idKhachHang <= 0){
                throw new RuntimeException("invalid idKhachHang");
            }
            if (khachHangService.findById(cart.idKhachHang).isHasError){
                throw new RuntimeException("khachHang not found");
            }
            if (cart.idNhanVien == null){
                throw new RuntimeException("idNhanVien must not null");
            }
            if (cart.idNhanVien <= 0){
                throw new RuntimeException("invalid idNhanVien");
            }
            if (nhanVienService.findById(cart.idNhanVien).isHasError){
                throw new RuntimeException("nhanVien not found");
            }
            if (cart.mapSanPham == null){
                throw new RuntimeException("SanPhams must not null");
            }
            if (cart.mapSanPham.isEmpty()){
                throw new RuntimeException("SanPhams must not empty");
            }

            NhanVien nv = AppService.currentUser;
            KhachHang kh = khachHangService.findById(cart.idKhachHang).data;

            HoaDon hoaDon = new HoaDon();
            hoaDon.setNhanVien(nv);
            hoaDon.setKhachHang(kh);
            hoaDon.setTrangThai(EntityStatus.ACTIVE);
            ResponseObject<HoaDon> savedHoadon = hoaDonService.add(hoaDon);
            if (savedHoadon.isHasError) {
                throw new RuntimeException(savedHoadon.message);
            }

            for (Map.Entry<Integer, Integer> sp : cart.mapSanPham.entrySet()) {
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setHoaDon(savedHoadon.data);
                SanPhamChiTiet spct = sanPhamChiTietService.findById(sp.getKey()).data;
                hoaDonChiTiet.setSanPhamChiTiet(spct);
                hoaDonChiTiet.setDonGia(spct.getDonGia());
                hoaDonChiTiet.setSoLuong(sp.getValue());
                hoaDonChiTiet.setTrangThai(EntityStatus.ACTIVE);
                ResponseObject<HoaDonChiTiet> savedHDCT = hoaDonChiTietService.add(hoaDonChiTiet);

                if (savedHDCT.isHasError) {
                    throw new RuntimeException(savedHDCT.message);
                }
                spct.setSoLuong(spct.getSoLuong() - sp.getValue());
                sanPhamChiTietService.update(spct);
            }

            return new ResponseObject<>(false, "OK", "Thanh toán thành công.");
        } catch (Exception ex) {
            return new ResponseObject<>(true, null, "Lỗi: " + ex.getMessage());
        }
    }
}
