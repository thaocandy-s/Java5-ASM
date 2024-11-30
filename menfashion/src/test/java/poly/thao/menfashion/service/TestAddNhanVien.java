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
import poly.thao.menfashion.model.EntityStatus;
import poly.thao.menfashion.repository.KhachHangRepository;
import poly.thao.menfashion.repository.NhanVienRepository;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestAddNhanVien {
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
    void addNVThanhCong1() { // Thêm thành công

        NhanVien nhanVien = new NhanVien(null, "NV001", "Ánh Quỳnhh", "NV21202@", "1234@", EntityStatus.ACTIVE);
        Assertions.assertEquals("Thêm NV thành công", nhanVienService.add(nhanVien).getMessage());
//        Assertions.assertEquals("miaaa", khachHangRepository.save(khachHang).getTen());

    }
    @Test
    void addTrungMa2() { // Thêm thất bại khi trùng mã
        NhanVien nhanVien = new NhanVien(null, "NV001", "Ánh Quỳnhh", "NV21202@", "1234@", EntityStatus.ACTIVE);
        Assertions.assertEquals("Mã bị trùng", nhanVienService.add(nhanVien).getMessage());
//        Assertions.assertEquals("miaaa", khachHangRepository.save(khachHang).getTen());
    }


    @Test
    void addMa3() { // Thêm thất bại khi mã kích thước chỉ có 2 ký tự
        NhanVien nhanVien = new NhanVien(null, "NV", "Ánh Quỳnhh", "NV21202@", "1234@", EntityStatus.ACTIVE);
        Assertions.assertEquals("Mã nhân viên cần 5 ký tự: NV+3 số bất kỳ", nhanVienService.add(nhanVien).getMessage());
    }
    @Test
    void addMa4() { // Thêm thất bại khi mã kích thước không theo định dạng: KH+3 ký tự

        NhanVien nhanVien = new NhanVien(null, "00002", "Ánh Quỳnhh", "NV21202@", "1234@", EntityStatus.ACTIVE);
        Assertions.assertEquals("Mã nhân viên cần 5 ký tự: NV+3 số bất kỳ", nhanVienService.add(nhanVien).getMessage());
//        Assertions.assertEquals("miaaa", khachHangRepository.save(khachHang).getTen());
    }

    @Test
    void addThatBaiVoiMa6KyTu5() { // Thêm thất bại với mã 6 ký tự
        NhanVien nhanVien = new NhanVien(null, "NV0006", "Ánh Quỳnhh", "NV21202@", "1234@", EntityStatus.ACTIVE);
        Assertions.assertEquals("Mã nhân viên cần 5 ký tự: NV+3 số bất kỳ", nhanVienService.add(nhanVien).getMessage());
    }

    @Test
    void addThatBai6() { // Thêm thất bại với mã chứa 4 ký tự
        NhanVien nhanVien = new NhanVien(null, "NV06", "Ánh Quỳnhh", "NV21202@", "1234@", EntityStatus.ACTIVE);
        Assertions.assertEquals("Mã nhân viên cần 5 ký tự: NV+3 số bất kỳ", nhanVienService.add(nhanVien).getMessage());
    }

    @Test
    void addThatBai7() { // Thêm thất bại với tên chứa 9 ký tự
        NhanVien nhanVien = new NhanVien(null, "NV002", "Ánh Quỳnh", "NV21202@", "1234@", EntityStatus.ACTIVE);
        Assertions.assertEquals("Tên nhân viên cần 10-20 ký tự, không số và ký tự đặc biệt", nhanVienService.add(nhanVien).getMessage());
    }

    @Test
    void addThatBai8() { // Thêm thất bại với tên chứa ký tự đặc biệt.
        NhanVien nhanVien = new NhanVien(null, "NV002", "Ánh Quỳnh$#", "NV21202@", "1234@", EntityStatus.ACTIVE);
        Assertions.assertEquals("Tên nhân viên cần 10-20 ký tự, không số và ký tự đặc biệt", nhanVienService.add(nhanVien).getMessage());
    }

    @Test
    void addThatBai9() { // Thêm thất bại với tên chứa số.
        NhanVien nhanVien = new NhanVien(null, "NV002", "Ánh Quỳnh212", "NV21202@", "1234@", EntityStatus.ACTIVE);
        Assertions.assertEquals("Tên nhân viên cần 10-20 ký tự, không số và ký tự đặc biệt", nhanVienService.add(nhanVien).getMessage());
    }
    @Test
    void addThatBai10() { // Thêm thất bại với mật khẩu chứa 4 ký tự
        NhanVien nhanVien = new NhanVien(null, "NV002", "Ánh Quỳnhab", "NV21202@", "1234", EntityStatus.ACTIVE);
        Assertions.assertEquals("Mật khẩu cần 5-10 ký tự, phải có ít nhất 1 số và 1 ký tự đặc biệt", nhanVienService.add(nhanVien).getMessage());
    }
}