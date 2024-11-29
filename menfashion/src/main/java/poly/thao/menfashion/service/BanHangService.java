package poly.thao.menfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
    private final KhachHangService khachHangService;

    @Autowired
    private final SanPhamChiTietService sanPhamChiTietService;

    @Autowired
    private final HoaDonService hoaDonService;

    @Autowired
    private final HoaDonChiTietService hoaDonChiTietService;

    @Autowired
    private final NhanVienService nhanVienService;

    public BanHangService(KhachHangService khachHangService, SanPhamChiTietService sanPhamChiTietService, HoaDonService hoaDonService, HoaDonChiTietService hoaDonChiTietService, NhanVienService nhanVienService) {
        this.khachHangService = khachHangService;
        this.sanPhamChiTietService = sanPhamChiTietService;
        this.hoaDonService = hoaDonService;
        this.hoaDonChiTietService = hoaDonChiTietService;
        this.nhanVienService = nhanVienService;
    }

    public Double getAmount(Map<SanPhamChiTiet, Integer> mapSP){
        Double amount = 0D;
        for (Map.Entry<SanPhamChiTiet, Integer> entry: mapSP.entrySet()){
            amount += entry.getKey().getDonGia()*entry.getValue();
        }
        return amount;
    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseObject<String> pay(Cart cart) {

        if (cart.getIdNhanVien() == null) {
            return new ResponseObject<>(true, null, "Không có thông tin nhân viên");
        } else if (cart.getIdNhanVien() <= 0) {
            return new ResponseObject<>(true, null, "Id nhân viên phải lớn hơn 0");
        }
        if (cart.getIdKhachHang() == null) {
            return new ResponseObject<>(true, null, "Không có thông tin khách hàng");
        } else if (cart.getIdKhachHang() <= 0) {
            return new ResponseObject<>(true, null, "Id khách hàng phải lớn hơn 0");
        }
        if (cart.getMapSanPham().isEmpty()) {
            return new ResponseObject<>(true, null, "Không có thông tin sản phẩm");
        }

        try {
            NhanVien nv = nhanVienService.findById(cart.getIdNhanVien()).data;
            KhachHang kh = khachHangService.findById(cart.idKhachHang).data;
            if (nv == null) {
                return new ResponseObject<>(true, null, "Không có thông tin nhân viên");
            }
            if (kh == null) {
                return new ResponseObject<>(true, null, "Không có thông tin khách hàng");
            }
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
