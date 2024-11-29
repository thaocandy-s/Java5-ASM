package poly.thao.menfashion.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import poly.thao.menfashion.repository.SanPhamRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestTimKiemSanPham {

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

    // Tìm kiếm sản phẩm với id hợp lệ
    @Test
    void findByIdTC1() {
        Assertions.assertEquals("Lấy data SP thành công", sanPhamService.findById(1).getMessage());
    }

    // Tìm kiếm sản phẩm thành công với id = 8 tồn tại
    @Test
    void findByIdTC2() {
        Assertions.assertEquals("Lấy data SP thành công", sanPhamService.findById(8).getMessage());
    }

    // Tìm kiếm sản phẩm thành công với id = 2 tồn tại
    @Test
    void findByIdTC3() {
        Assertions.assertEquals("Lấy data SP thành công", sanPhamService.findById(2).getMessage());
    }

    // Tìm kiếm sản phẩm không thành công với id = 0 không hợp lệ
    @Test
    void findByIdTC4() {
        Assertions.assertEquals("Id phải là số lớn hơn 0", sanPhamService.findById(0).getMessage());
    }

    // Tìm kiếm sản phẩm không thành công với id âm
    @Test
    void findByIdTC5() {
        Assertions.assertEquals("Id phải là số lớn hơn 0", sanPhamService.findById(-100).getMessage());
    }

    // Tìm kiếm sản phẩm không thành công với id = -1
    @Test
    void findByIdTC6() {
        Assertions.assertEquals("Id phải là số lớn hơn 0", sanPhamService.findById(-1).getMessage());
    }

    // Tìm kiếm sản phẩm không thành công với id vượt quá giá trị int dương
    @Test
    void findByIdTC7() {
        Assertions.assertEquals("Id vượt quá giới hạn int", sanPhamService.findById(2147483648L).getMessage());
    }

    // Tìm kiếm sản phẩm không thành công với id không tồn tại
    @Test
    void findByIdTC8() {
        Assertions.assertEquals("SP không tồn tại", sanPhamService.findById(20).getMessage());
    }

    // Tìm kiếm sản phẩm không thành công với id vượt quá giá trị int âm
    @Test
    void findByIdTC9() {
        Assertions.assertEquals("Id vượt quá giới hạn int", sanPhamService.findById(-2147483649L).getMessage());
    }

    // Tìm kiếm sản phẩm không thành công với id = 100 không tồn tại
    @Test
    void findByIdTC10() {
        Assertions.assertEquals("SP không tồn tại", sanPhamService.findById(100).getMessage());
    }
}