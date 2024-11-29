package poly.thao.menfashion.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import poly.thao.menfashion.entity.SanPham;
import poly.thao.menfashion.model.EntityStatus;
import poly.thao.menfashion.repository.SanPhamRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestThemSanPham {

    @Autowired
    SanPhamService sanPhamService;

    @Autowired
    SanPhamRepository sanPhamRepository;

    @BeforeEach
    void setUp() {
        sanPhamService = new SanPhamService(sanPhamRepository);
    }

    @AfterEach
    void tearDown() {
        sanPhamService = null;
    }

    // Thêm sản phẩm thành công
    @Test
    void addSanPhamTC1() {
        SanPham sp = new SanPham(null,"SP00132", EntityStatus.ACTIVE, "Iphone");
        Assertions.assertEquals("Thêm SP thành công", sanPhamService.add(sp).getMessage());
    }

    // Thêm sản phẩm không thành công mã = 0 kí tự
    @Test
    void addSanPhamTC2() {
        SanPham sp = new SanPham(null,"", EntityStatus.ACTIVE, "Iphone");
        Assertions.assertEquals("Mã không được trống", sanPhamService.add(sp).getMessage());
    }

    // Thêm sản phẩm không thành công mã = 4 kí tự
    @Test
    void addSanPhamTC3() {
        SanPham sp = new SanPham(null,"SP00", EntityStatus.ACTIVE, "Iphone");
        Assertions.assertEquals("Mã sản phẩm 5 - 10 ký tự", sanPhamService.add(sp).getMessage());
    }

    // Thêm sản phẩm không thành công mã = 11 kí tự
    @Test
    void addSanPhamTC4() {
        SanPham sp = new SanPham(null,"SP000000001", EntityStatus.ACTIVE, "Iphone");
        Assertions.assertEquals("Mã sản phẩm 5 - 10 ký tự", sanPhamService.add(sp).getMessage());
    }

    // Thêm sản phẩm không thành công mã = 50 kí tự
    @Test
    void addSanPhamTC5() {
        SanPham sp = new SanPham(null,"SP000000000000000000000000000000000000000000000001", EntityStatus.ACTIVE, "Iphone");
        Assertions.assertEquals("Mã sản phẩm 5 - 10 ký tự", sanPhamService.add(sp).getMessage());
    }

    // Thêm sản phẩm không thành công tên = 0 kí tự
    @Test
    void addSanPhamTC6() {
        SanPham sp = new SanPham(null,"SP0012", EntityStatus.ACTIVE, "");
        Assertions.assertEquals("Tên không được trống", sanPhamService.add(sp).getMessage());
    }

    // Thêm sản phẩm không thành công tên = 4 kí tự
    @Test
    void addSanPhamTC7() {
        SanPham sp = new SanPham(null,"SP0012", EntityStatus.ACTIVE, "Ipad");
        Assertions.assertEquals("Tên sản phẩm cần 5-50 ký tự, không số và ký tự đặc biệt", sanPhamService.add(sp).getMessage());
    }

    // Thêm sản phẩm không thành công tên = 51 kí tự
    @Test
    void addSanPhamTC8() {
        SanPham sp = new SanPham(null,"SP0013", EntityStatus.ACTIVE, "Ipadddddddddddddddddddddddddddddddddddddddddddddddd");
        Assertions.assertEquals("Tên sản phẩm cần 5-50 ký tự, không số và ký tự đặc biệt", sanPhamService.add(sp).getMessage());
    }

    // Thêm sản phẩm không thành công tên chứa số
    @Test
    void addSanPhamTC9() {
        SanPham sp = new SanPham(null,"SP0012", EntityStatus.ACTIVE, "Ipad10");
        Assertions.assertEquals("Tên sản phẩm cần 5-50 ký tự, không số và ký tự đặc biệt", sanPhamService.add(sp).getMessage());
    }

    // Thêm sản phẩm không thành công tên chứa kí tự đặc biệt
    @Test
    void addSanPhamTC10() {
        SanPham sp = new SanPham(null,"SP0012", EntityStatus.ACTIVE, "Ipad#@");
        Assertions.assertEquals("Tên sản phẩm cần 5-50 ký tự, không số và ký tự đặc biệt", sanPhamService.add(sp).getMessage());
    }
}