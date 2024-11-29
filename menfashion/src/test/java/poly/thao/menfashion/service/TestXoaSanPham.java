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
class TestXoaSanPham {

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

    // Xóa thành công với mã hợp lệ 9 kí tự
    @Test
    void deleteByCodeTC1() {
        Assertions.assertEquals("Xóa thành công", sanPhamService.deleteByCode("SP0012332").getMessage());
    }

    // Xóa thành công với mã hợp lệ 5 kí tự
    @Test
    void deleteByCodeTC2() {
        Assertions.assertEquals("Xóa thành công", sanPhamService.deleteByCode("SP001").getMessage());
    }

    // Xóa không thành công với mã không tồn tại
    @Test
    void deleteByCodeTC3() {
        Assertions.assertEquals("SP không tồn tại", sanPhamService.deleteByCode("SP00133332").getMessage());
    }

    // Xóa không thành công với mã trống
    @Test
    void deleteByCodeTC4() {
        Assertions.assertEquals("Mã không được trống", sanPhamService.deleteByCode("").getMessage());
    }

    // Xóa không thành công với mã = 2 kí tự
    @Test
    void deleteByCodeTC5() {
        Assertions.assertEquals("Mã phải từ 5 đến 10 kí tự", sanPhamService.deleteByCode("SP").getMessage());
    }

    // Xóa không thành công với mã = 4 kí tự
    @Test
    void deleteByCodeTC6() {
        Assertions.assertEquals("Mã phải từ 5 đến 10 kí tự", sanPhamService.deleteByCode("SP00").getMessage());
    }

    // Xóa không thành công với mã = 11 kí tự
    @Test
    void deleteByCodeTC7() {
        Assertions.assertEquals("Mã phải từ 5 đến 10 kí tự", sanPhamService.deleteByCode("SP006543212").getMessage());
    }

    // Xóa không thành công với mã = 30 kí tự
    @Test
    void deleteByCodeTC8() {
        Assertions.assertEquals("Mã phải từ 5 đến 10 kí tự",
                sanPhamService.deleteByCode("SP0000000000000000000000000002").getMessage());
    }

    // Xóa không thành công với mã chứa kí tự đặc biệt
    @Test
    void deleteByCodeTC9() {
        Assertions.assertEquals("Mã không được chứa khoảng trắng và kí tự đặc biệt",
                sanPhamService.deleteByCode("SP003#").getMessage());
    }

    // Xóa không thành công với mã chứa khoảng trắng
    @Test
    void deleteByCodeTC10() {
        Assertions.assertEquals("Mã không được chứa khoảng trắng và kí tự đặc biệt",
                sanPhamService.deleteByCode("SP0  03").getMessage());
    }
}