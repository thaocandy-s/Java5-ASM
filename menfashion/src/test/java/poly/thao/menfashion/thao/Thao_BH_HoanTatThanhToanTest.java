package poly.thao.menfashion.thao;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import poly.thao.menfashion.entity.HoaDon;
import poly.thao.menfashion.entity.HoaDonChiTiet;
import poly.thao.menfashion.entity.KhachHang;
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.entity.SanPhamChiTiet;
import poly.thao.menfashion.model.request.Cart;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.service.AppService;
import poly.thao.menfashion.service.BanHangService;
import poly.thao.menfashion.service.HoaDonChiTietService;
import poly.thao.menfashion.service.HoaDonService;
import poly.thao.menfashion.service.KhachHangService;
import poly.thao.menfashion.service.NhanVienService;
import poly.thao.menfashion.service.SanPhamChiTietService;

import java.util.HashMap;

@SpringBootTest
@AutoConfigureMockMvc
public class Thao_BH_HoanTatThanhToanTest {

    @Mock
    private KhachHangService khachHangService;
    @Mock
    private NhanVienService nhanVienService;
    @Mock
    private HoaDonService hoaDonService;
    @Mock
    private SanPhamChiTietService sanPhamChiTietService;
    @Mock
    private HoaDonChiTietService hoaDonChiTietService;

    @InjectMocks
    private BanHangService paymentService;

    private Cart validCart;
    private KhachHang mockKhachHang;
    private NhanVien mockNhanVien;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock dữ liệu khách hàng
        mockKhachHang = new KhachHang();
        mockKhachHang.setId(1);
        when(khachHangService.findById(1)).thenReturn(new ResponseObject<>(false, mockKhachHang, "OK"));

        // Mock dữ liệu nhân viên
        mockNhanVien = new NhanVien();
        mockNhanVien.setId(1);
        AppService.currentUser = mockNhanVien;

        // Cart hợp lệ
        validCart = new Cart();
        validCart.idKhachHang = 1;
        validCart.idNhanVien = 1;
        validCart.mapSanPham = new HashMap<>();
        validCart.mapSanPham.put(1, 2); // ID sản phẩm, số lượng

        // Mock sản phẩm chi tiết
        SanPhamChiTiet mockSpct = new SanPhamChiTiet();
        mockSpct.setId(1);
        mockSpct.setDonGia(100D);
        mockSpct.setSoLuong(10);
        when(sanPhamChiTietService.findById(1)).thenReturn(new ResponseObject<>(false, mockSpct, "OK"));
    }


    @Test
    void saveInvoice1_Successfully() {
        HoaDon mockHoaDon = new HoaDon();
        mockHoaDon.setId(100);
        when(khachHangService.findById(validCart.idKhachHang))
                .thenReturn(new ResponseObject<>(false, mockKhachHang, ""));
        when(nhanVienService.findById(validCart.idNhanVien))
                .thenReturn(new ResponseObject<>(false, mockNhanVien, ""));
        when(hoaDonService.add(any(HoaDon.class)))
                .thenReturn(new ResponseObject<>(false, mockHoaDon, "OK"));
        when(hoaDonChiTietService.add(any(HoaDonChiTiet.class)))
                .thenReturn(new ResponseObject<>(false, null, "OK"));
        ResponseObject<String> result = paymentService.pay(validCart);

        assertFalse(result.isHasError);
        assertEquals("Thanh toán thành công.", result.getMessage());
    }


    @Test
    void saveInvoice2_IdNhanVienNull() {
        validCart.idNhanVien = null;
        when(khachHangService.findById(validCart.idKhachHang))
                .thenReturn(new ResponseObject<>(false, null, "khachHang not found"));
        ResponseObject<String> result = paymentService.pay(validCart);
        assertTrue(result.isHasError);
        assertTrue(result.getMessage().contains("idNhanVien must not null"));
    }

    @Test
    void saveInvoice3_IdKhachHangNull() {
        validCart.idKhachHang = null;
        ResponseObject<String> result = paymentService.pay(validCart);

        assertTrue(result.isHasError);
        assertTrue(result.getMessage().contains("idKhachHang must not null"));
    }

    @Test
    void saveInvoice4_InvalidNhanVien() {
        when(nhanVienService.findById(validCart.idNhanVien))
                .thenReturn(new ResponseObject<>(true, null, "nhanvien not found"));
        ResponseObject<String> result = paymentService.pay(validCart);
        assertTrue(result.isHasError);
        assertTrue(result.getMessage().contains("nhanVien not found"));
    }

    @Test
    void saveInvoice5_InvalidKhachHang() {
        when(khachHangService.findById(validCart.idKhachHang))
                .thenReturn(new ResponseObject<>(true, null, "khachHang not found"));
        ResponseObject<String> result = paymentService.pay(validCart);
        assertTrue(result.isHasError);
        assertTrue(result.getMessage().contains("khachHang not found"));
    }


    @Test
    void saveInvoice6_InvalidIdNhanVien() {
        validCart.idNhanVien = -10;
        ResponseObject<String> result = paymentService.pay(validCart);
        assertTrue(result.isHasError);
        assertTrue(result.getMessage().contains("invalid idNhanVien"));
    }

    @Test
    void saveInvoice7_InvalidIdKhachHang() {
        validCart.idKhachHang = -20;
        ResponseObject<String> result = paymentService.pay(validCart);
        assertTrue(result.isHasError);
        assertTrue(result.getMessage().contains("invalid idKhachHang"));
    }

    @Test
    void saveInvoice8_SPIsNull() {
        validCart.mapSanPham = null;
        when(khachHangService.findById(validCart.idKhachHang))
                .thenReturn(new ResponseObject<>(false, null, ""));
        when(nhanVienService.findById(validCart.idNhanVien))
                .thenReturn(new ResponseObject<>(false, null, ""));
        ResponseObject<String> result = paymentService.pay(validCart);
        assertTrue(result.isHasError);
        assertTrue(result.getMessage().contains("SanPhams must not null"));
    }
    @Test
    void saveInvoice9_SPIsEmpty() {
        validCart.mapSanPham.clear();
        when(khachHangService.findById(validCart.idKhachHang))
                .thenReturn(new ResponseObject<>(false, null, ""));
        when(nhanVienService.findById(validCart.idNhanVien))
                .thenReturn(new ResponseObject<>(false, null, ""));
        ResponseObject<String> result = paymentService.pay(validCart);
        System.out.println(result);

        assertTrue(result.getMessage().contains("SanPhams must not empty"));
    }

    @Test
    void saveInvoice10_Successfully() {
        HoaDon mockHoaDon = new HoaDon();
        mockHoaDon.setId(100);
        when(khachHangService.findById(validCart.idKhachHang))
                .thenReturn(new ResponseObject<>(false, null, ""));
        when(nhanVienService.findById(validCart.idNhanVien))
                .thenReturn(new ResponseObject<>(false, null, ""));
        when(hoaDonService.add(any(HoaDon.class)))
                .thenReturn(new ResponseObject<>(false, mockHoaDon, "OK"));
        when(hoaDonChiTietService.add(any(HoaDonChiTiet.class)))
                .thenReturn(new ResponseObject<>(false, null, "OK"));
        ResponseObject<String> result = paymentService.pay(validCart);

        assertFalse(result.isHasError);
        assertEquals("Thanh toán thành công.", result.getMessage());
    }



}