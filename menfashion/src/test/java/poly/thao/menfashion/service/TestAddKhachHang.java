package poly.thao.menfashion.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import poly.thao.menfashion.entity.KhachHang;
import poly.thao.menfashion.model.EntityStatus;
import poly.thao.menfashion.repository.KhachHangRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestAddKhachHang {

    @Autowired
    KhachHangService khachHangService;

    @Autowired
    KhachHangRepository khachHangRepository;

    @BeforeEach
    void setUp() {
        khachHangService = new KhachHangService(khachHangRepository);
    }

    @AfterEach
    void tearDown() {
        khachHangService = null;
    }

    @Test
    void addKHThanhCong() { // Thêm thành công

        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "miaaa", "0947123970");
        Assertions.assertEquals("Thêm KH thành công", khachHangService.add(khachHang).getMessage());
//        Assertions.assertEquals("miaaa", khachHangRepository.save(khachHang).getTen());

    }
    @Test
    void addTrungMa2() { // Thêm thất bại khi trùng mã
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "miaaa", "0947123970");
        Assertions.assertEquals("Mã bị trùng", khachHangService.add(khachHang).getMessage());
//        Assertions.assertEquals("miaaa", khachHangRepository.save(khachHang).getTen());
    }
    @Test
    void addMa3() { // Thêm thất bại khi mã kích thước chỉ có 2 ký tự
        KhachHang khachHang = new KhachHang(null, "KH", EntityStatus.ACTIVE, "miaaa", "0947123970");
        Assertions.assertEquals("Mã kích thước cần 5 ký tự: KH+3 số bất kỳ", khachHangService.add(khachHang).getMessage());
//        Assertions.assertEquals("miaaa", khachHangRepository.save(khachHang).getTen());
    }

    @Test
    void addMa4() { // Thêm thất bại khi mã kích thước không theo định dạng: KH+3 ký tự

        KhachHang khachHang = new KhachHang(null, "00003", EntityStatus.ACTIVE, "miaaa", "0947123970");
        Assertions.assertEquals("Mã kích thước cần 5 ký tự: KH+3 số bất kỳ", khachHangService.add(khachHang).getMessage());
//        Assertions.assertEquals("miaaa", khachHangRepository.save(khachHang).getTen());
    }

    @Test
    void addThatBaiVoiMa6KyTu5() { // Thêm thất bại với mã 6 ký tự
        KhachHang khachHang = new KhachHang(null, "KH0003", EntityStatus.ACTIVE, "miaaa", "0947123970");
        Assertions.assertEquals("Mã kích thước cần 5 ký tự: KH+3 số bất kỳ", khachHangService.add(khachHang).getMessage());
    }

    @Test
    void addThatBai6() { // Thêm thất bại với tên chứa 4 ký tự
        KhachHang khachHang = new KhachHang(null, "KH009", EntityStatus.ACTIVE, "miaa", "0947123970");
        Assertions.assertEquals("Tên kích thước cần 5-20 ký tự, không số và ký tự đặc biệt", khachHangService.add(khachHang).getMessage());
    }

    @Test
    void addThatBai7() { // Thêm thất bại với tên chứa 20 ký tự
        KhachHang khachHang = new KhachHang(null, "KH007", EntityStatus.ACTIVE, "mia adtfg cghutyb cgfdrt", "0947123970");
        Assertions.assertEquals("Tên kích thước cần 5-20 ký tự, không số và ký tự đặc biệt", khachHangService.add(khachHang).getMessage());
    }

    @Test
    void addThatBai8() { // Thêm thất bại với tên chứa ký tự đặc biệt.


        KhachHang khachHang = new KhachHang(null, "KH008", EntityStatus.ACTIVE, "mia$!", "0947123970");
        Assertions.assertEquals("Tên kích thước cần 5-20 ký tự, không số và ký tự đặc biệt", khachHangService.add(khachHang).getMessage());
    }

    @Test
    void addThatBai9() { // Thêm thất bại với tên chứa số.
        KhachHang khachHang = new KhachHang(null, "KH008", EntityStatus.ACTIVE, "mia21", "0947123970");
        Assertions.assertEquals("Tên kích thước cần 5-20 ký tự, không số và ký tự đặc biệt", khachHangService.add(khachHang).getMessage());
    }

    @Test
    void addThatBai10() { // Thêm thất bại với số điện thoại không có 0 ở đầu
        KhachHang khachHang = new KhachHang(null, "KH010", EntityStatus.ACTIVE, "miaaaa", "9947123970");
        Assertions.assertEquals("Số điện thoại khách hàng cần 10-13 chữ số, số 0 ở đầu", khachHangService.add(khachHang).getMessage());
    }

}