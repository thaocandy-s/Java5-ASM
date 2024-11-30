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
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.repository.KhachHangRepository;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestUpdateKhachHang {

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
    void updateKHThanhCong1() {
        KhachHang khachHang = khachHangService.findByCode("KH001").getData();
        if (khachHang == null) {
            Assertions.fail("Mã không tồn tại");
        } else {
            khachHang.setTen("Maria Ánh Quỳnh");
            String resultMessage = khachHangService.update(khachHang).getMessage();
            Assertions.assertEquals("Sửa KH thành công", resultMessage);
        }
    }

    @Test
    void updateKHThatBai2() { // Sửa thất bại khi sdt không có số 0
        KhachHang khachHang = khachHangService.findByCode("KH001").getData();
        if (khachHang == null) {
            Assertions.fail("Mã không tồn tại");
        } else {
            khachHang.setSoDienThoai("9345712940");
            String resultMessage = khachHangService.update(khachHang).getMessage();
            Assertions.assertEquals("Số điện thoại khách hàng cần 10-13 chữ số, số 0 ở đầu", resultMessage);
        }
    }

    @Test
    void updateKHThatBai3() { // Sửa thất bại khi sdt có 9 số
        KhachHang khachHang = khachHangService.findByCode("KH001").getData();
        if (khachHang == null) {
            Assertions.fail("Mã không tồn tại");
        } else {
            khachHang.setSoDienThoai("934571294");
            String resultMessage = khachHangService.update(khachHang).getMessage();
            Assertions.assertEquals("Số điện thoại khách hàng cần 10-13 chữ số, số 0 ở đầu", resultMessage);
        }
    }

    @Test
    void updateKHThatBai4() { // Sửa thất bại khi sdt có 14 số
        KhachHang khachHang = khachHangService.findByCode("KH001").getData();
        if (khachHang == null) {
            Assertions.fail("Mã không tồn tại");
        } else {
            khachHang.setSoDienThoai("03457129401230");
            String resultMessage = khachHangService.update(khachHang).getMessage();
            Assertions.assertEquals("Số điện thoại khách hàng cần 10-13 chữ số, số 0 ở đầu", resultMessage);
        }
    }

    @Test
    void updateKHThatBai5() { // Sửa thất bại khi sdt chứa chữ
        KhachHang khachHang = khachHangService.findByCode("KH001").getData();
        if (khachHang == null) {
            Assertions.fail("Mã không tồn tại");
        } else {
            khachHang.setSoDienThoai("934571293a");
            String resultMessage = khachHangService.update(khachHang).getMessage();
            Assertions.assertEquals("Số điện thoại khách hàng cần 10-13 chữ số, số 0 ở đầu", resultMessage);
        }
    }

    @Test
    void updateKHThatBai6() { // Sửa thất bại khi sdt chứa ký tự đặc biệt
        KhachHang khachHang = khachHangService.findByCode("KH001").getData();
        if (khachHang == null) {
            Assertions.fail("Mã không tồn tại");
        } else {
            khachHang.setSoDienThoai("934571294@2");
            String resultMessage = khachHangService.update(khachHang).getMessage();
            Assertions.assertEquals("Số điện thoại khách hàng cần 10-13 chữ số, số 0 ở đầu", resultMessage);
        }
    }

    @Test
    void updateKHThatBai7() { // Sửa thất bại khi tên chứa số
        KhachHang khachHang = khachHangService.findByCode("KH001").getData();
        if (khachHang == null) {
            Assertions.fail("Mã không tồn tại");
        } else {
            khachHang.setTen("Ánh Quỳnh 21");
            String resultMessage = khachHangService.update(khachHang).getMessage();
            Assertions.assertEquals("Tên kích thước cần 5-20 ký tự, không số và ký tự đặc biệt", resultMessage);
        }
    }

    @Test
    void updateKHThatBai8() { // Sửa thất bại khi tên chứa ký tự đặc biệt
        KhachHang khachHang = khachHangService.findByCode("KH001").getData();
        if (khachHang == null) {
            Assertions.fail("Mã không tồn tại");
        } else {
            khachHang.setTen("Ánh Quỳnh @ ");
            String resultMessage = khachHangService.update(khachHang).getMessage();
            Assertions.assertEquals("Tên kích thước cần 5-20 ký tự, không số và ký tự đặc biệt", resultMessage);
        }
    }

    @Test
    void updateKHThatBai9() { // Sửa thất bại khi tên chứa 4 ký tự
        KhachHang khachHang = khachHangService.findByCode("KH001").getData();
        if (khachHang == null) {
            Assertions.fail("Mã không tồn tại");
        } else {
            khachHang.setTen("Ánhh");
            String resultMessage = khachHangService.update(khachHang).getMessage();
            Assertions.assertEquals("Tên kích thước cần 5-20 ký tự, không số và ký tự đặc biệt", resultMessage);
        }
    }

    @Test
    void updateKHThatBai10() { // Sửa thất bại khi tên chứa 21 ký tự
        KhachHang khachHang = khachHangService.findByCode("KH001").getData();
        if (khachHang == null) {
            Assertions.fail("Mã không tồn tại");
        } else {
            khachHang.setTen("Không cóznhgvstebch a");
            String resultMessage = khachHangService.update(khachHang).getMessage();
            Assertions.assertEquals("Tên kích thước cần 5-20 ký tự, không số và ký tự đặc biệt", resultMessage);
        }
    }

}