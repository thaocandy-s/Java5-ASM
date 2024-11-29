package poly.thao.menfashion.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import poly.thao.menfashion.entity.KichThuoc;
import poly.thao.menfashion.entity.MauSac;
import poly.thao.menfashion.entity.SanPham;
import poly.thao.menfashion.entity.SanPhamChiTiet;
import poly.thao.menfashion.model.EntityStatus;
import poly.thao.menfashion.repository.KichThuocRepository;
import poly.thao.menfashion.repository.MauSacRepository;
import poly.thao.menfashion.repository.SanPhamChiTietRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestThemSanPhamChiTiet {
    MauSac ms;
    KichThuoc kt;
    SanPham sp;

    @Autowired
    SanPhamChiTietService sanPhamChiTietService;
    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;
    @Autowired
    MauSacRepository mauSacRepository;
    @Autowired
    KichThuocRepository kichThuocRepository;
    @Autowired
    SanPhamService sanPhamService;

    @BeforeEach
    void setUp() {
        sanPhamChiTietService = new SanPhamChiTietService(sanPhamChiTietRepository, mauSacRepository, kichThuocRepository);
        ms = sanPhamChiTietService.getMauSac(1);
        kt = sanPhamChiTietService.getKichThuoc(1);
        sp = sanPhamService.findById(1).getData();
    }

    @AfterEach
    void tearDown() {
        sanPhamChiTietService = null;
    }

    // Thêm SPCT thành công
    @Test
    void addSanPhamChiTietTC1() {
        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet(null, "SPCT005", EntityStatus.ACTIVE, kt, ms, sp, 20, 50000.0);
        Assertions.assertEquals("Thêm SP thành công", sanPhamChiTietService.add(sanPhamChiTiet).getMessage());
    }

    // Thêm sản phẩm chi tiết không thành công khi mã = 0 kí tự
    @Test
    void addSanPhamChiTietTC2() {
        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet(null, "", EntityStatus.ACTIVE, kt, ms, sp, 20, 50000.0);
        Assertions.assertEquals("Không được để trống các trường mã-đơn giá-số lượng", sanPhamChiTietService.add(sanPhamChiTiet).getMessage());
    }

    // Thêm sản phẩm chi tiết không thành công khi đơn giá = null
    @Test
    void addSanPhamChiTietTC3() {
        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet(null, "SPCT006", EntityStatus.ACTIVE, kt, ms, sp, 20, null);
        Assertions.assertEquals("Không được để trống các trường mã-đơn giá-số lượng", sanPhamChiTietService.add(sanPhamChiTiet).getMessage());
    }

    // Thêm sản phẩm chi tiết không thành công khi số lượng = null
    @Test
    void addSanPhamChiTietTC4() {
        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet(null, "SPCT007", EntityStatus.ACTIVE, kt, ms, sp, null, 50000.0);
        Assertions.assertEquals("Không được để trống các trường mã-đơn giá-số lượng", sanPhamChiTietService.add(sanPhamChiTiet).getMessage());
    }

    // Thêm sản phẩm chi tiết không thành công khi số lượng < 0
    @Test
    void addSanPhamChiTietTC5() {
        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet(null, "SPCT008", EntityStatus.ACTIVE, kt, ms, sp, -10, 50000.0);
        Assertions.assertEquals("So luong, don gia phai lon hon 0", sanPhamChiTietService.add(sanPhamChiTiet).getMessage());
    }

    // Thêm sản phẩm chi tiết không thành công khi đơn giá < 0
    @Test
    void addSanPhamChiTietTC6() {
        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet(null, "SPCT009", EntityStatus.ACTIVE, kt, ms, sp, -10, 50000.0);
        Assertions.assertEquals("So luong, don gia phai lon hon 0", sanPhamChiTietService.add(sanPhamChiTiet).getMessage());
    }

    // Thêm sản phẩm chi tiết không thành công khi mã trùng = SPCT005
    @Test
    void addSanPhamChiTietTC7() {
        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet(null, "SPCT005", EntityStatus.ACTIVE, kt, ms, sp, 10, 50000.0);
        Assertions.assertEquals("Mã bị trùng", sanPhamChiTietService.add(sanPhamChiTiet).getMessage());
    }

    // Thêm sản phẩm chi tiết không thành công khi mã chứa kí tự có dấu
    @Test
    void addSanPhamChiTietTC8() {
        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet(null, "ÂSPCT005", EntityStatus.ACTIVE, kt, ms, sp, 10, 50000.0);
        Assertions.assertEquals("Mã chỉ chứa các chữ không dấu", sanPhamChiTietService.add(sanPhamChiTiet).getMessage());
    }

    // Thêm sản phẩm chi tiết không thành công khi mã = 4 kí tự
    @Test
    void addSanPhamChiTietTC9() {
        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet(null, "SPCT", EntityStatus.ACTIVE, kt, ms, sp, 20, 50000.0);
        Assertions.assertEquals("Mã phải từ 5-10 kí tự", sanPhamChiTietService.add(sanPhamChiTiet).getMessage());
    }

    // Thêm sản phẩm chi tiết không thành công khi mã = 11 kí tự
    @Test
    void addSanPhamChiTietTC10() {
        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet(null, "SPCT0050055", EntityStatus.ACTIVE, kt, ms, sp, 20, Double.MAX_VALUE + 2.0);
        Assertions.assertEquals("Mã phải từ 5-10 kí tự", sanPhamChiTietService.add(sanPhamChiTiet).getMessage());
    }
}