package poly.thao.menfashion.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.model.EntityStatus;
import poly.thao.menfashion.repository.NhanVienRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestSearchNhanVien {

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
    void search1() { // Kiểm tra với kết quả chính xác
        List<NhanVien> nhanViens = List.of(
                new NhanVien(1, "NV001", "Nguyen Van A", "nva", "123456", EntityStatus.ACTIVE),
                new NhanVien(2, "NV002", "Tran Van B", "tvb", "654321", EntityStatus.ACTIVE)
        );
        String key = "NV001";

        List<NhanVien> result = nhanVienService.search(nhanViens, key);

        assertEquals(1, result.size());
        assertEquals("NV001", result.get(0).getMa());
    }

    @Test
    void search2() { // Kiểm tra không phân biệt chữ hoa, chữ thường
        List<NhanVien> nhanViens = List.of(
                new NhanVien(1, "NV001", "Nguyen Van A", "nva", "123456", EntityStatus.ACTIVE),
                new NhanVien(2, "NV002", "Tran Van B", "tvb", "654321", EntityStatus.ACTIVE)
        );
        String key = "nv001";

        List<NhanVien> result = nhanVienService.search(nhanViens, key);

        assertEquals(1, result.size());
        assertEquals("NV001", result.get(0).getMa());
    }

    @Test
    void search3() { // Kiểm tra từ khoá tương đối
        List<NhanVien> nhanViens = List.of(
                new NhanVien(1, "NV001", "Nguyen Van A", "nva", "123456", EntityStatus.ACTIVE),
                new NhanVien(2, "NV002", "Tran Van B", "tvb", "654321", EntityStatus.ACTIVE)
        );
        String key = "Van";

        List<NhanVien> result = nhanVienService.search(nhanViens, key);

        assertEquals(2, result.size());
    }

    @Test
    void search4() { // Kiểm tra không có kết quả trùng khớp trong danh sách
        List<NhanVien> nhanViens = List.of(
                new NhanVien(1, "NV001", "Nguyen Van A", "nva", "123456", EntityStatus.ACTIVE),
                new NhanVien(2, "NV002", "Tran Van B", "tvb", "654321", EntityStatus.ACTIVE)
        );
        String key = "Le";

        List<NhanVien> result = nhanVienService.search(nhanViens, key);

        assertTrue(result.isEmpty());
    }

    @Test
    void search5() { // Kiểm tra từ khoá là chuỗi rỗng
        List<NhanVien> nhanViens = List.of(
                new NhanVien(1, "NV001", "Nguyen Van A", "nva", "123456", EntityStatus.ACTIVE),
                new NhanVien(2, "NV002", "Tran Van B", "tvb", "654321", EntityStatus.ACTIVE)
        );
        String key = "";

        List<NhanVien> result = nhanVienService.search(nhanViens, key);

        assertEquals(2, result.size());
    }

    @Test
    void search6() { // Kiểm tra nhân viên theo trạng thái
        List<NhanVien> nhanViens = List.of(
                new NhanVien(1, "NV001", "Nguyen Van A", "nva", "123456", EntityStatus.ACTIVE),
                new NhanVien(2, "NV002", "Tran Van B", "tvb", "654321", EntityStatus.INACTIVE)
        );
        String key = "NV002";

        List<NhanVien> result = nhanVienService.search(nhanViens, key);

        assertEquals(1, result.size());
        assertEquals("NV002", result.get(0).getMa());
        assertEquals(EntityStatus.INACTIVE, result.get(0).getTrangThai());
    }

    @Test
    void search7() { // Kiểm tra từ khoá chứa ký tự đặc biệt
        List<NhanVien> nhanViens = List.of(
                new NhanVien(1, "NV001", "Nguyen@VanA", "nva", "123456", EntityStatus.ACTIVE),
                new NhanVien(2, "NV002", "Tran Van B", "tvb", "654321", EntityStatus.ACTIVE)
        );
        String key = "@";

        List<NhanVien> result = nhanVienService.search(nhanViens, key);

        assertEquals(1, result.size());
        assertEquals("Nguyen@VanA", result.get(0).getTen());
    }

    @Test
    void search8() { // Kiểm tra từ khoá là chuỗi khoảng trắng
        List<NhanVien> nhanViens = List.of(
                new NhanVien(1, "NV001", "Nguyen Van A", "nva", "123456", EntityStatus.ACTIVE),
                new NhanVien(2, "NV002", "Tran Van B", "tvb", "654321", EntityStatus.ACTIVE)
        );
        String key = " ";

        List<NhanVien> result = nhanVienService.search(nhanViens, key);

        assertEquals(2, result.size());
    }

    @Test
    void search9() { // Kiểm tra nhiều kết quả khớp
        List<NhanVien> nhanViens = List.of(
                new NhanVien(1, "NV001", "Nguyen Van A", "nva", "123456", EntityStatus.ACTIVE),
                new NhanVien(2, "NV002", "Nguyen Van B", "nvb", "654321", EntityStatus.ACTIVE)
        );
        String key = "Nguyen";

        List<NhanVien> result = nhanVienService.search(nhanViens, key);

        assertEquals(2, result.size());
    }

    @Test
    void search10() { // Kiểm tra từ khoá trong danh sách rỗng
        List<NhanVien> nhanViens = List.of();
        String key = "NV001";

        List<NhanVien> result = nhanVienService.search(nhanViens, key);

        assertTrue(result.isEmpty());
    }

}