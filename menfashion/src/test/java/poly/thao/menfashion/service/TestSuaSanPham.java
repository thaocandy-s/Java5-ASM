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
class TestSuaSanPham {

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

    // Sửa sản phẩm thành công
    @Test
    void updateSanPhamTC1() {
        SanPham sanPham = new SanPham(2,"SP00123", EntityStatus.ACTIVE, "Iphone");
        Assertions.assertEquals("Sửa SP thành công", sanPhamService.update(sanPham).getMessage());
    }

    // Sửa sản phẩm không thành công khi mã = 0 kí tự
    @Test
    void updateSanPhamTC2() {
        SanPham sanPham = new SanPham(2,"", EntityStatus.ACTIVE, "Iphone");
        Assertions.assertEquals("Mã không được trống", sanPhamService.update(sanPham).getMessage());
    }

    // Sửa sản phẩm không thành công khi mã = 4 kí tự
    @Test
    void updateSanPhamTC3() {
        SanPham sanPham = new SanPham(2,"SP00", EntityStatus.ACTIVE, "Iphone");
        Assertions.assertEquals("Mã sản phẩm 5 - 10 ký tự", sanPhamService.update(sanPham).getMessage());
    }

    // Sửa sản phẩm không thành công khi mã = 11 kí tự
    @Test
    void updateSanPhamTC4() {
        SanPham sanPham = new SanPham(2,"SP000000001", EntityStatus.ACTIVE, "Iphone");
        Assertions.assertEquals("Mã sản phẩm 5 - 10 ký tự", sanPhamService.update(sanPham).getMessage());
    }

    // Sửa sản phẩm không thành công khi mã = 50 kí tự
    @Test
    void updateSanPhamTC5() {
        SanPham sanPham = new SanPham(2,"SP000000000000000000000000000000000000000000000001",
                EntityStatus.ACTIVE, "Iphone");
        Assertions.assertEquals("Mã sản phẩm 5 - 10 ký tự", sanPhamService.update(sanPham).getMessage());
    }

    // Sửa sản phẩm không thành công khi tên = 0 kí tự
    @Test
    void updateSanPhamTC6() {
        SanPham sanPham = new SanPham(2,"SP00123",
                EntityStatus.ACTIVE, "");
        Assertions.assertEquals("Tên sản phẩm cần 5-50 ký tự, không số và ký tự đặc biệt", sanPhamService.update(sanPham).getMessage());
    }

    // Sửa sản phẩm không thành công khi tên = 4 kí tự
    @Test
    void updateSanPhamTC7() {
        SanPham sanPham = new SanPham(2,"SP00123",
                EntityStatus.ACTIVE, "Ipad");
        Assertions.assertEquals("Tên sản phẩm cần 5-50 ký tự, không số và ký tự đặc biệt", sanPhamService.update(sanPham).getMessage());
    }

    // Sửa sản phẩm không thành công khi tên = 51 kí tự
    @Test
    void updateSanPhamTC8() {
        SanPham sanPham = new SanPham(2,"SP00123",
                EntityStatus.ACTIVE, "Ipadddddddddddddddddddddddddddddddddddddddddddddddd");
        Assertions.assertEquals("Tên sản phẩm cần 5-50 ký tự, không số và ký tự đặc biệt", sanPhamService.update(sanPham).getMessage());
    }

    // Sửa sản phẩm không thành công khi tên chứa số
    @Test
    void updateSanPhamTC9() {
        SanPham sanPham = new SanPham(2,"SP00123",
                EntityStatus.ACTIVE, "Ipad 10");
        Assertions.assertEquals("Tên sản phẩm cần 5-50 ký tự, không số và ký tự đặc biệt", sanPhamService.update(sanPham).getMessage());
    }

    // Sửa sản phẩm không thành công khi tên chứa kí tự đặc biệt
    @Test
    void updateSanPhamTC10() {
        SanPham sanPham = new SanPham(2,"SP00123",
                EntityStatus.ACTIVE, "Ipad#@@");
        Assertions.assertEquals("Tên sản phẩm cần 5-50 ký tự, không số và ký tự đặc biệt", sanPhamService.update(sanPham).getMessage());
    }

}