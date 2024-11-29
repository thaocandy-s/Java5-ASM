package poly.thao.menfashion.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import poly.thao.menfashion.entity.KichThuoc;
import poly.thao.menfashion.entity.MauSac;
import poly.thao.menfashion.model.EntityStatus;
import poly.thao.menfashion.repository.KichThuocRepository;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class KichThuocServiceTest {
    private final Integer DEFAULT_ID_SIZE = 1;

    @Autowired
    private KichThuocRepository kichThuocRepository;
    private KichThuocService kichThuocService;
    @BeforeEach
    void setUp() {
        kichThuocService = new KichThuocService(kichThuocRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testAddKichThuocKT_31() { // Thêm màu sắc thành công
        KichThuoc kichThuoc = new KichThuoc(null, "KT001", EntityStatus.ACTIVE, "Lon");
        Assertions.assertEquals("Thêm KT thành công", kichThuocService.add(kichThuoc).message);
    }

    @Test
    public void testAddKichThuocKT_32() { // Thêm kích thước thất bại khi để trống trường mã kích thước
        KichThuoc kichThuoc = new KichThuoc(null, "", EntityStatus.ACTIVE, "Lon");
        Assertions.assertEquals("Mã kích thước cần 5 ký tự: KT+3 số bất kỳ", kichThuocService.add(kichThuoc).message);
    }

    @Test
    public void testAddKichThuocKT_33() { // Thêm kích thước thất bại khi để trống trường tên kích thước
        KichThuoc kichThuoc = new KichThuoc(null, "KT002", EntityStatus.ACTIVE, "");
        Assertions.assertEquals("Tên kích thước cần 2-10 ký tự, không số và ký tự đặc biệt", kichThuocService.add(kichThuoc).message);
    }

    @Test
    public void testAddKichThuocKT_34() { // Thêm kích thước thất bại khi trùng mã kích thước
        KichThuoc kichThuocDuplicateCode = new KichThuoc(null, "KT001", EntityStatus.ACTIVE, "Lon");
        Assertions.assertEquals("Mã bị trùng", kichThuocService.add(kichThuocDuplicateCode).message);
    }

    @Test
    public void testAddKichThuocKT_35() { // Thêm kích thước thất bại khi mã kích thước chứa 4 ký tự
        KichThuoc kichThuoc = new KichThuoc(null, "KT03", EntityStatus.ACTIVE, "Lon");
        Assertions.assertEquals("Mã kích thước cần 5 ký tự: KT+3 số bất kỳ", kichThuocService.add(kichThuoc).message);
    }

    @Test
    public void testAddKichThuocKT_36() { // Thêm màu sắc thất bại khi mã màu sắc chứa 6 ký tự
        KichThuoc kichThuoc = new KichThuoc(null, "KT0004", EntityStatus.ACTIVE, "Hong");
        Assertions.assertEquals("Mã kích thước cần 5 ký tự: KT+3 số bất kỳ", kichThuocService.add(kichThuoc).message);
    }

    @Test
    public void testAddKichThuocKT_37() { // Thêm kích thước thất bại khi tên kích thước chứa 1 ký tự
        KichThuoc kichThuoc = new KichThuoc(null, "KT005", EntityStatus.ACTIVE, "A");
        Assertions.assertEquals("Tên kích thước cần 2-10 ký tự, không số và ký tự đặc biệt", kichThuocService.add(kichThuoc).message);
    }

    @Test
    public void testAddKichThuocKT_38() { //Thêm kích thước thất bại khi tên kích thước chứa 11 ký tự
        KichThuoc kichThuoc = new KichThuoc(null, "KT006", EntityStatus.ACTIVE, "Rat rat lon");
        Assertions.assertEquals("Tên kích thước cần 2-10 ký tự, không số và ký tự đặc biệt", kichThuocService.add(kichThuoc).message);
    }



    @Test
    public void testAddKichThuocKT_39() { // Thêm màu sắc thất bại khi tên màu sắc số
        KichThuoc kichThuoc = new KichThuoc(null, "KT008", EntityStatus.ACTIVE, "Lon123");
        Assertions.assertEquals("Tên kích thước cần 2-10 ký tự, không số và ký tự đặc biệt", kichThuocService.add(kichThuoc).message);
    }

    @Test
    public void testAddKichThuocKT_40() { // Thêm màu sắc thất bại khi tên màu sắc chứa ký tự đặc biệt
        KichThuoc kichThuoc = new KichThuoc(null, "KT008", EntityStatus.ACTIVE, "Lon@@");
        Assertions.assertEquals("Tên kích thước cần 2-10 ký tự, không số và ký tự đặc biệt", kichThuocService.add(kichThuoc).message);
    }


    // UPDATE
    @Test
    void testUpdateKichThuocKY_41() { // Sửa kích thước thành công khi điền các trường hợp lệ
        KichThuoc kichThuoc = kichThuocService.findByCode("KT001").getData();
        kichThuoc.setTen("Rat lon");
        Assertions.assertEquals("Sửa KT thành công", kichThuocService.update(kichThuoc).getMessage());
    }

    @Test
    void testUpdateKichThuocKY_42() { // Sửa kichThuoc thành công khi tên kichThuoc chứa 2 ký tự tại biên dưới
        KichThuoc kichThuoc = kichThuocService.findByCode("KT001").getData();
        kichThuoc.setTen("Be");
        Assertions.assertEquals("Sửa KT thành công", kichThuocService.update(kichThuoc).getMessage());
    }

    @Test
    void testUpdateKichThuocKY_43() { // Sửa kichThuoc thành công khi tên kichThuoc chứa 3 ký tự tại biên dưới
        KichThuoc kichThuoc = kichThuocService.findByCode("KT001").getData();
        kichThuoc.setTen("Vua");
        Assertions.assertEquals("Sửa KT thành công", kichThuocService.update(kichThuoc).getMessage());
    }

    @Test
    void testUpdateKichThuocKY_44() { // Sửa kichThuoc thành công khi tên kichThuoc chứa 9 ký tự tại biên dưới
        KichThuoc kichThuoc = kichThuocService.findByCode("KT001").getData();
        kichThuoc.setTen("Sieu rong");
        Assertions.assertEquals("Sửa KT thành công", kichThuocService.update(kichThuoc).getMessage());
    }

    @Test
    void testUpdateKichThuocKY_45() { // Sửa kichThuoc thành công khi tên kichThuoc chứa 10 ký tự tại biên dưới
        KichThuoc kichThuoc = kichThuocService.findByCode("KT001").getData();
        kichThuoc.setTen("Lon nhat I");
        Assertions.assertEquals("Sửa KT thành công", kichThuocService.update(kichThuoc).getMessage());
    }


    @Test
    void testUpdateKichThuocKY_46() { // Sửa kichThuoc thất bại khi để trống trường tên kichThuoc
        KichThuoc kichThuoc = kichThuocService.findByCode("KT001").getData();
        kichThuoc.setTen("");
        Assertions.assertEquals("Tên kích thước cần 2-10 ký tự, không số và ký tự đặc biệt", kichThuocService.update(kichThuoc).getMessage());
    }

    @Test
    void testUpdateKichThuocKY_47() { // Sửa kichThuoc thất bại khi tên kichThuoc chứa 1 ký tự tại ngoài biên dưới
        KichThuoc kichThuoc = kichThuocService.findByCode("KT001").getData();
        kichThuoc.setTen("A");
        Assertions.assertEquals("Tên kích thước cần 2-10 ký tự, không số và ký tự đặc biệt", kichThuocService.update(kichThuoc).getMessage());
    }

    @Test
    void testUpdateKichThuocKY_48() { // Sửa kichThuoc thất bại khi tên kichThuoc chứa 11 ký tự tại ngoài biên dưới
        KichThuoc kichThuoc = kichThuocService.findByCode("KT001").getData();
        kichThuoc.setTen("Sieu to lon");
        Assertions.assertEquals("Tên kích thước cần 2-10 ký tự, không số và ký tự đặc biệt", kichThuocService.update(kichThuoc).getMessage());
    }

    @Test
    void testUpdateKichThuocKY_49() { // Sửa kichThuoc thất bại khi tên kichThuoc chứa số
        KichThuoc kichThuoc = kichThuocService.findByCode("KT001").getData();
        kichThuoc.setTen("Lom22");
        Assertions.assertEquals("Tên kích thước cần 2-10 ký tự, không số và ký tự đặc biệt", kichThuocService.update(kichThuoc).getMessage());
    }

    @Test
    void testUpdateKichThuocKY_50() { // Sửa kichThuoc thất bại khi tên kichThuoc chứa ký tự đặc biệt
        KichThuoc kichThuoc = kichThuocService.findByCode("KT001").getData();
        kichThuoc.setTen("Lon@@@");
        Assertions.assertEquals("Tên kích thước cần 2-10 ký tự, không số và ký tự đặc biệt", kichThuocService.update(kichThuoc).getMessage());
    }
}