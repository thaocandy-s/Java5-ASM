package poly.thao.menfashion.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import poly.thao.menfashion.entity.KhachHang;
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.model.EntityStatus;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.repository.KhachHangRepository;
import poly.thao.menfashion.repository.NhanVienRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestUpdateNhanVien {
    @Autowired
    NhanVienService nhanVienService;

    @Autowired
    NhanVienRepository nhanVienRepository;

    @BeforeEach
    void setUp() {
        nhanVienService = new NhanVienService(nhanVienRepository);
    }

    @AfterEach
    void tearDown() {
        nhanVienService = null;
    }

    @Test
    void updateNVThanhCong1() {
        NhanVien nhanVien = nhanVienService.findByCode("NV001").getData();

        if (nhanVien == null) {
            Assertions.fail("Mã không tồn tại");
        } else {
            nhanVien.setTen("Maria Ánh Quỳnh");
            String resultMessage = nhanVienService.update(nhanVien).getMessage();
            Assertions.assertEquals("Sửa NV thành công", resultMessage);
        }
    }

    @Test
    void updateNVThatBai2() { // Sửa thất bại khi mật khẩu có 4 ký tự
        NhanVien nhanVien = nhanVienService.findByCode("NV001").getData();

        if (nhanVien == null) {
            Assertions.fail("Mã không tồn tại");
        } else {
            nhanVien.setMatKhau("mi1@");
            String resultMessage = nhanVienService.update(nhanVien).getMessage();
            Assertions.assertEquals("Mật khẩu cần 5-10 ký tự, phải có ít nhất 1 số và 1 ký tự đặc biệt", resultMessage);
        }
    }

    @Test
    void updateNVThatBai3() { // Sửa thất bại khi mật khẩu chứa 11 ký tự
        NhanVien nhanVien = nhanVienService.findByCode("NV001").getData();

        if (nhanVien == null) {
            Assertions.fail("Mã không tồn tại");
        } else {
            nhanVien.setMatKhau("mia5678911@");
            String resultMessage = nhanVienService.update(nhanVien).getMessage();
            Assertions.assertEquals("Mật khẩu cần 5-10 ký tự, phải có ít nhất 1 số và 1 ký tự đặc biệt", resultMessage);
        }
    }

    @Test
    void updateNVThatBai4() { // Sửa thất bại khi mật khẩu không chứa số
        NhanVien nhanVien = nhanVienService.findByCode("NV001").getData();

        if (nhanVien == null) {
            Assertions.fail("Mã không tồn tại");
        } else {
            nhanVien.setMatKhau("miaaaaa@");
            String resultMessage = nhanVienService.update(nhanVien).getMessage();
            Assertions.assertEquals("Mật khẩu cần 5-10 ký tự, phải có ít nhất 1 số và 1 ký tự đặc biệt", resultMessage);
        }
    }

    @Test
    void updateNVThatBai5() { // Sửa thất bại khi mật khẩu không chứa ký tự đặc biệt
        NhanVien nhanVien = nhanVienService.findByCode("NV001").getData();

        if (nhanVien == null) {
            Assertions.fail("Mã không tồn tại");
        } else {
            nhanVien.setMatKhau("miaaaaa21");
            String resultMessage = nhanVienService.update(nhanVien).getMessage();
            Assertions.assertEquals("Mật khẩu cần 5-10 ký tự, phải có ít nhất 1 số và 1 ký tự đặc biệt", resultMessage);
        }
    }
    @Test
    void updateNVThatBai6() { // Sửa thất bại khi tên chứa 9 ký tự
        NhanVien nhanVien = nhanVienService.findByCode("NV001").getData();

        if (nhanVien == null) {
            Assertions.fail("Mã không tồn tại");
        } else {
            nhanVien.setTen("miaaaa ab");
            String resultMessage = nhanVienService.update(nhanVien).getMessage();
            Assertions.assertEquals("Tên nhân viên cần 10-20 ký tự, không số và ký tự đặc biệt", resultMessage);
        }
    }
    @Test
    void updateNVThatBai7() { // Sửa thất bại khi tên chứa 21 ký tự
        NhanVien nhanVien = nhanVienService.findByCode("NV001").getData();

        if (nhanVien == null) {
            Assertions.fail("Mã không tồn tại");
        } else {
            nhanVien.setTen("miaaaaaaaaaaaaaaaa ab");
            String resultMessage = nhanVienService.update(nhanVien).getMessage();
            Assertions.assertEquals("Tên nhân viên cần 10-20 ký tự, không số và ký tự đặc biệt", resultMessage);
        }
    }
    @Test
    void updateNVThatBai8() { // Sửa thất bại khi tên chứa số
        NhanVien nhanVien = nhanVienService.findByCode("NV001").getData();

        if (nhanVien == null) {
            Assertions.fail("Mã không tồn tại");
        } else {
            nhanVien.setTen("miaaaaaaaa 21");
            String resultMessage = nhanVienService.update(nhanVien).getMessage();
            Assertions.assertEquals("Tên nhân viên cần 10-20 ký tự, không số và ký tự đặc biệt", resultMessage);
        }
    }
    @Test
    void updateNVThatBai9() { // Sửa thất bại khi tên chứa ký tự đặc biệt
        NhanVien nhanVien = nhanVienService.findByCode("NV001").getData();

        if (nhanVien == null) {
            Assertions.fail("Mã không tồn tại");
        } else {
            nhanVien.setTen("miaaaaaaaa @#");
            String resultMessage = nhanVienService.update(nhanVien).getMessage();
            Assertions.assertEquals("Tên nhân viên cần 10-20 ký tự, không số và ký tự đặc biệt", resultMessage);
        }
    }
    @Test
    void updateNVThatBai10() { // Sửa thất bại khi tên đăng nhập không chứa chữ hoa
        NhanVien nhanVien = nhanVienService.findByCode("NV001").getData();

        if (nhanVien == null) {
            Assertions.fail("Mã không tồn tại");
        } else {
            nhanVien.setTenDangNhap("trần ánh quỳnh");
            String resultMessage = nhanVienService.update(nhanVien).getMessage();
            Assertions.assertEquals("Tên đăng nhập cần 10-20 ký tự, phải có ít nhất 1 chữ hoa và 1 ký tự đặc biệt", resultMessage);
        }
    }
}