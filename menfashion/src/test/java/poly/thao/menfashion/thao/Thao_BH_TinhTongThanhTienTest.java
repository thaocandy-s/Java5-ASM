package poly.thao.menfashion.thao;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import poly.thao.menfashion.entity.SanPhamChiTiet;
import poly.thao.menfashion.service.BanHangService;

import static org.junit.jupiter.api.Assertions.*;
        import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class Thao_BH_TinhTongThanhTienTest {

    @Autowired
    private BanHangService banHangService;
    private Map<SanPhamChiTiet, Integer> mapSP;
    @BeforeEach
    public void setUp() {
        banHangService = new BanHangService();
        mapSP = new HashMap<>();
    }

    @AfterEach
    public void tearDown() {
        mapSP.clear();
    }

    @Test
    public void testGetAmountWithValidSPCT() {
        // Kiểm tra tính đúng tổng tiền hóa đơn khi dữ liệu hợp lệ
        SanPhamChiTiet sp = new SanPhamChiTiet();
        sp.setDonGia(200.0);
        mapSP.put(sp, 5); // 200 * 5 = 1000

        Double result = banHangService.getAmount(mapSP);
        assertEquals(1000.0, result, 0.01);
    }

    @Test
    public void testGetAmountWithNullMap() {
        Assertions.assertThrows(NullPointerException.class,()-> banHangService.getAmount(null));
    }

    @Test
    public void testGetAmountWithEmptyMap() {
        // Kiểm tra hàm khi mapSP is Empty
        Double result = banHangService.getAmount(mapSP);
        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void testGetAmountWithSingleSPCTQuantity1() {
        // Kiểm tra hàm khi mapSP có một SPCT với số lượng = 1
        SanPhamChiTiet sp = new SanPhamChiTiet();
        sp.setDonGia(150.0);
        mapSP.put(sp, 1); // 150 * 1 = 150

        Double result = banHangService.getAmount(mapSP);
        assertEquals(150.0, result, 0.01);
    }

    @Test
    public void testGetAmountWithMultipleSPCT() {
        // Kiểm tra hàm khi mapSP có nhiều SPCT với số lượng khác nhau
        SanPhamChiTiet sp1 = new SanPhamChiTiet();
        sp1.setDonGia(100.0);
        SanPhamChiTiet sp2 = new SanPhamChiTiet();
        sp2.setDonGia(200.0);
        mapSP.put(sp1, 3); // 100 * 3 = 300
        mapSP.put(sp2, 2); // 200 * 2 = 400

        Double result = banHangService.getAmount(mapSP);
        assertEquals(700.0, result, 0.01);
    }

    @Test
    public void testGetAmountWithNegativeQuantity() {
        // Kiểm tra hàm khi mapSP có số lượng = -1
        SanPhamChiTiet sp = new SanPhamChiTiet();
        sp.setDonGia(200.0);
        mapSP.put(sp, -1); // 200 * -1 = -200

        Double result = banHangService.getAmount(mapSP);
        assertEquals(-200.0, result, 0.01);
    }

    @Test
    public void testGetAmountWithZeroPrice() {
        // Kiểm tra hàm khi SPCT có giá = 0
        SanPhamChiTiet sp = new SanPhamChiTiet();
        sp.setDonGia(0.0);
        mapSP.put(sp, 5); // 0 * 5 = 0

        Double result = banHangService.getAmount(mapSP);
        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void testGetAmountWithMixedZeroPrice() {
        // Kiểm tra hàm khi có 3 SPCT, trong đó 1 SPCT có giá = 0
        SanPhamChiTiet sp1 = new SanPhamChiTiet();
        sp1.setDonGia(300.0);
        SanPhamChiTiet sp2 = new SanPhamChiTiet();
        sp2.setDonGia(0.0);
        SanPhamChiTiet sp3 = new SanPhamChiTiet();
        sp3.setDonGia(100.0);

        mapSP.put(sp1, 2); // 300 * 2 = 600
        mapSP.put(sp2, 3); // 0 * 3 = 0
        mapSP.put(sp3, 1); // 100 * 1 = 100

        Double result = banHangService.getAmount(mapSP);
        assertEquals(700.0, result, 0.01);
    }

    @Test
    public void testGetAmountWithNegativePrice() {
        // Kiểm tra hàm khi SPCT có giá là số âm
        SanPhamChiTiet sp = new SanPhamChiTiet();
        sp.setDonGia(-200.0);
        mapSP.put(sp, 5); // -200 * 5 = -1000

        Double result = banHangService.getAmount(mapSP);
        assertEquals(-1000.0, result, 0.01);
    }

    @Test
    public void testGetAmountWithHighPriceBoundary() {
        // Kiểm tra hàm khi SPCT có giá gần biên trên
        SanPhamChiTiet sp = new SanPhamChiTiet();
        sp.setDonGia(Double.MAX_VALUE);
        mapSP.put(sp, 1); // MAX_VALUE * 1 = MAX_VALUE

        Double result = banHangService.getAmount(mapSP);
        assertEquals(Double.MAX_VALUE, result, 0.01);
    }
}

