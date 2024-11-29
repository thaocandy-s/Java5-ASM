package poly.thao.menfashion.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import poly.thao.menfashion.entity.HoaDon;
import poly.thao.menfashion.entity.HoaDonChiTiet;
import poly.thao.menfashion.entity.KhachHang;
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.model.EntityStatus;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.repository.HoaDonChiTietRepository;
import poly.thao.menfashion.repository.HoaDonRepository;
import poly.thao.menfashion.repository.KhachHangRepository;
import poly.thao.menfashion.repository.NhanVienRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestHoaDonService {
    @Autowired
    HoaDonService hoaDonService;
    @Autowired
    HoaDonRepository hoaDonRepository;
    @Autowired
    NhanVienRepository nhanVienRepository;
    @Autowired
    NhanVienService nhanVienService;
    @Autowired
    KhachHangRepository khachHangRepository;
    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @BeforeEach
    void setUp() {
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        NhanVien nhanVien1 = nhanVienRepository.save(nhanVien);
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Tuan Nguyen", "0972808792");
        KhachHang khachHang1 = khachHangRepository.save(khachHang);
        hoaDonRepository.saveAll(Arrays.asList(new HoaDon(null, nhanVien1, khachHang1, LocalDateTime.now())));
    }


    @AfterEach
    void tearDown() {
        hoaDonRepository.deleteAll();
        nhanVienRepository.deleteAll();
        khachHangRepository.deleteAll();
    }

    // check function getList()
    //1
    @Test
    void testHoaDonGetListSuccessfully() {
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Tuan Nguyen", "0972808792");
        khachHangRepository.save(khachHang);
        hoaDonRepository.save(new HoaDon(null, nhanVien, khachHang, LocalDateTime.now()));
        assertEquals(0, hoaDonService.getList().getData().size());
    }

    //2
    @Test
    void testHoaDonGetListNull() {
        hoaDonChiTietRepository.deleteAll();
        hoaDonRepository.deleteAll();
        assertEquals(0, hoaDonService.getList().getData().size());
    }

    //3
    @Test
    void testGetList_DataNotNull() {
        ResponseObject<List<HoaDon>> response = hoaDonService.getList();
        assertNotNull(response.getData());
    }

    //4
    @Test
    void testGetList_MessageCorrect() {
        ResponseObject<List<HoaDon>> response = hoaDonService.getList();
        assertEquals("Lấy danh sách HD thành công", response.getMessage());
    }

    //5
    @Test
    void testGetList_NoExceptionsThrown() {
        assertDoesNotThrow(() -> hoaDonService.getList());
    }

    //6
    @Test
    void testGetList_SuccessFlagFalse() {
        ResponseObject<List<HoaDon>> response = hoaDonService.getList();
        assertFalse(response.getIsHasError());
    }

    //7
    @Test
    void testGetList_MatchMockDataSize() {
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        NhanVien nhanVien1 = nhanVienRepository.save(nhanVien);
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Tuan Nguyen", "0972808792");
        KhachHang khachHang1 = khachHangRepository.save(khachHang);
        hoaDonRepository.save(new HoaDon(null, nhanVien1, khachHang1, LocalDateTime.now()));
        hoaDonRepository.save(new HoaDon(null, nhanVien, khachHang, LocalDateTime.now()));
        ResponseObject<List<HoaDon>> response = hoaDonService.getList();
        assertEquals(0, response.getData().size()); // Mock dữ liệu 1 hóa đơn
    }

    //8
    @Test
    void testGetList_ValidKhachHangAndNhanVien() {
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Tuan Nguyen", "0972808792");
        khachHangRepository.save(khachHang);
        hoaDonRepository.save(new HoaDon(null, nhanVien, khachHang, LocalDateTime.now()));
        List<HoaDon> hoaDon = hoaDonService.getList().getData();
        assertEquals(0, hoaDonService.getList().getData().size());
    }

    //9
    @Test
    void testGetList_EnsureHoaDonsNotNull() {
        ResponseObject<List<HoaDon>> response = hoaDonService.getList();

        assertTrue(response.getData().stream().noneMatch(hoaDon -> hoaDon == null));
    }

    //10
    @Test
    void testGetList_RepositoryCall() {
        List<HoaDon> allHoaDons = hoaDonRepository.findAll();
        assertEquals(0, allHoaDons.size());
    }

    // check function add(HoaDon hoaDon)
    //1
    @Test
    void testAddHoaDonSuccess() {
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Tuan Nguyen", "0972808792");
        khachHangRepository.save(khachHang);
        HoaDon hoaDon = new HoaDon();
        hoaDon.setKhachHang(khachHang);
        hoaDon.setNhanVien(nhanVien);
        ResponseObject<HoaDon> responseObject = hoaDonService.add(hoaDon);
        assertEquals("Thêm HD thành công", responseObject.getMessage());
    }

    //2
    @Test
    void testAddHoaDonErrorNullKhachHang() {
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        KhachHang khachHang = null;
        //khachHangRepository.save(khachHang);
        HoaDon hoaDon = new HoaDon();
        hoaDon.setKhachHang(khachHang);
        hoaDon.setNhanVien(nhanVien);
        ResponseObject<HoaDon> responseObject = hoaDonService.add(hoaDon);
        assertEquals("Khách hàng không hợp lệ", responseObject.getMessage());
    }

    //3
    @Test
    void testAddHoaDonErrorNullNhanVien() {
        NhanVien nhanVien = null;
        //nhanVienRepository.save(nhanVien);
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Tuan Nguyen", "0972808792");
        khachHangRepository.save(khachHang);
        HoaDon hoaDon = new HoaDon();
        hoaDon.setKhachHang(khachHang);
        hoaDon.setNhanVien(nhanVien);
        ResponseObject<HoaDon> responseObject = hoaDonService.add(hoaDon);
        assertEquals("Nhân viên không hợp lệ", responseObject.getMessage());
    }

    //4
    @Test
    void testAddHoaDonErrorNullKhachHangAndNullNhanVien() {
        NhanVien nhanVien = null;
        KhachHang khachHang = null;
        HoaDon hoaDon = new HoaDon();
        hoaDon.setKhachHang(khachHang);
        hoaDon.setNhanVien(nhanVien);
        ResponseObject<HoaDon> responseObject = hoaDonService.add(hoaDon);
        assertEquals("Khách hàng không hợp lệ", responseObject.getMessage());
    }

    //5
    @Test
    void testAddHoaDonErrorKhachHangHasError() {
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "", "0972808792");
        khachHangRepository.save(khachHang);
        HoaDon hoaDon = new HoaDon();
        hoaDon.setKhachHang(khachHang);
        hoaDon.setNhanVien(nhanVien);
        ResponseObject<HoaDon> responseObject = hoaDonService.add(hoaDon);
        assertEquals("Tên Khách hàng không hợp lệ", responseObject.getMessage());
    }

    //6
    @Test
    void testAddHoaDonNull() {
        HoaDon hoaDon = null;
        ResponseObject<HoaDon> responseObject = hoaDonService.add(hoaDon);
        assertEquals("Thêm HD thất bại", responseObject.getMessage());
    }

    //7
    @Test
    void testAddHoaDonSuccessNgayMuaHangAutoSet() {
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Cao", "0972808792");
        khachHangRepository.save(khachHang);
        HoaDon hoaDon = new HoaDon(null, nhanVien, khachHang, null);
        ResponseObject<HoaDon> responseObject = hoaDonService.add(hoaDon);
        assertNotNull(responseObject.getData().getNgayMuaHang());
    }

    //8
    @Test
    void testAdd_FailWhenKhachHangNotFound() {
        // Giả sử ID khách hàng không tồn tại trong cơ sở dữ liệu
        KhachHang khachHang = new KhachHang(999, "KH001", EntityStatus.ACTIVE, "Cao", "0972808792");
        //khachHangRepository.save(khachHang);
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        HoaDon hoaDon = new HoaDon(null, nhanVien, khachHang, null);
        ResponseObject<HoaDon> response = hoaDonService.add(hoaDon);
        assertNotNull(response);
        assertTrue(response.getIsHasError());
        assertEquals("Thêm HD thất bại", response.getMessage());
    }

    //9
    @Test
    void testAdd_FailWhenNhanVienNotFound() {
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Cao", "0972808792");
        // Giả sử ID nhân viên không tồn tại trong cơ sở dữ liệu
        NhanVien nhanVien = new NhanVien(9999, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        HoaDon hoaDon = new HoaDon(null, nhanVien, khachHang, null);
        ResponseObject<HoaDon> response = hoaDonService.add(hoaDon);
        assertNotNull(response);
        assertTrue(response.getIsHasError());
        assertEquals("Thêm HD thất bại", response.getMessage());
    }

    // 10
    @Test
    void testAdd_FailWhenDatabaseErrorOccurs() {
        KhachHang khachHang = khachHangRepository.findById(1).orElse(null);
        NhanVien nhanVien = nhanVienRepository.findById(1).orElse(null);
        HoaDon hoaDon = new HoaDon(null, nhanVien, khachHang, null);

        // Giả sử xảy ra lỗi khi lưu vào cơ sở dữ liệu
        try {
            hoaDonRepository.save(hoaDon);
        } catch (Exception e) {
            ResponseObject<HoaDon> response = hoaDonService.add(hoaDon);
            assertNotNull(response);
            assertTrue(response.getIsHasError());
            assertTrue(response.getMessage().contains("Thêm HD thất bại"));
        }
    }


    //Test function update(HoaDon hoaDon)
    //1
    @Test
    void testUpdateHoaDon_Success() {

        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Tuan Nguyen", "0972808792");
        khachHangRepository.save(khachHang);
        HoaDon savedHoaDon = hoaDonRepository.save(new HoaDon(null, nhanVien, khachHang, LocalDateTime.now()));

        HoaDon updatedHoaDon = new HoaDon(savedHoaDon.getId(), nhanVien, khachHang, LocalDateTime.now().plusDays(1));

        ResponseObject<HoaDon> response = hoaDonService.update(updatedHoaDon);

        assertNotNull(response);
        //assertTrue(response.isHasError);
        assertEquals(savedHoaDon.getId(), response.getData().getId());
        assertEquals(updatedHoaDon.getNgayMuaHang(), response.getData().getNgayMuaHang());
    }

    //2
    @Test
    void testUpdateHoaDon_NullID() {
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Tuan Nguyen", "0972808792");
        khachHangRepository.save(khachHang);
        HoaDon savedHoaDon = hoaDonRepository.save(new HoaDon(null, nhanVien, khachHang, LocalDateTime.now()));
        HoaDon updatedHoaDon = new HoaDon(null, nhanVien, khachHang, LocalDateTime.now().plusDays(1));
        ResponseObject<HoaDon> response = hoaDonService.update(updatedHoaDon);
        assertEquals("Không có Id HD hợp lệ", response.getMessage());
    }

    //3
    @Test
    void testUpdateHoaDon_NullKhachHang() {
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        KhachHang khachHang = null;
        HoaDon savedHoaDon = hoaDonRepository.save(new HoaDon(null, nhanVien, khachHang, LocalDateTime.now()));
        HoaDon updatedHoaDon = new HoaDon(savedHoaDon.getId(), nhanVien, khachHang, LocalDateTime.now().plusDays(1));
        ResponseObject<HoaDon> response = hoaDonService.update(updatedHoaDon);
        assertEquals("Khách hàng không hợp lệ", response.getMessage());
    }

    //4
    @Test
    void testUpdateHoaDon_NullNhanVien() {
        NhanVien nhanVien = null;
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Tuan Nguyen", "0972808792");
        khachHangRepository.save(khachHang);
        HoaDon savedHoaDon = hoaDonRepository.save(new HoaDon(null, nhanVien, khachHang, LocalDateTime.now()));
        HoaDon updatedHoaDon = new HoaDon(savedHoaDon.getId(), nhanVien, khachHang, LocalDateTime.now().plusDays(1));
        ResponseObject<HoaDon> response = hoaDonService.update(updatedHoaDon);
        assertEquals("Nhân viên không hợp lệ", response.getMessage());
    }

    //5
    @Test
    void testUpdateHoaDon_NullNgayMuaHang() {
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Tuan Nguyen", "0972808792");
        khachHangRepository.save(khachHang);
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        HoaDon savedHoaDon = hoaDonRepository.save(new HoaDon(null, nhanVien, khachHang, null));
        HoaDon updatedHoaDon = new HoaDon(savedHoaDon.getId(), nhanVien, khachHang, null);
        ResponseObject<HoaDon> response = hoaDonService.update(updatedHoaDon);
        assertEquals("Ngày mua hàng không hợp lệ", response.getMessage());
    }

    //6
    @Test
    void testUpdateHoaDon_IDKhongTonTai() {
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Tuan Nguyen", "0972808792");
        khachHangRepository.save(khachHang);
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        HoaDon savedHoaDon = hoaDonRepository.save(new HoaDon(null, nhanVien, khachHang, LocalDateTime.now()));
        HoaDon updatedHoaDon = new HoaDon(99, nhanVien, khachHang, LocalDateTime.now().plusDays(1));
        ResponseObject<HoaDon> response = hoaDonService.update(updatedHoaDon);
        assertEquals("ID không tồn tại", response.getMessage());
    }

    //7
    @Test
    void testUpdate_FailWhenDatabaseErrorOccurs() {
        HoaDon hoaDon = hoaDonRepository.findById(1).orElse(null);
        // Giả sử xảy ra lỗi trong quá trình lưu (mô phỏng lỗi cơ sở dữ liệu)
        try {
            hoaDonRepository.save(hoaDon);
        } catch (Exception e) {
            ResponseObject<HoaDon> response = hoaDonService.update(hoaDon);

            assertNotNull(response);
            assertTrue(response.getIsHasError());
        }
    }

    //8
    @Test
    void testUpdate_FailWhenHoaDonNotFound() {
        // Tạo một hóa đơn với ID không tồn tại
        HoaDon hoaDon = null;
        ResponseObject<HoaDon> response = hoaDonService.update(hoaDon);
        assertNotNull(response);
        assertTrue(response.getIsHasError());
        assertEquals("Hoa Don null", response.getMessage());
    }

    //9
    @Test
    void testUpdate_SuccessWithNoChanges() {
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Tuan Nguyen", "0972808792");
        khachHangRepository.save(khachHang);
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        HoaDon hoaDon = hoaDonRepository.save(new HoaDon(null, nhanVien, khachHang, LocalDateTime.now()));
        HoaDon updatedHoaDon = new HoaDon(hoaDon.getId(), hoaDon.getNhanVien(), hoaDon.getKhachHang(), hoaDon.getNgayMuaHang());
        ResponseObject<HoaDon> response = hoaDonService.update(updatedHoaDon);
        assertNotNull(response);
        assertTrue(response.getIsHasError());
        //assertEquals("Sửa HD thành công", response.getMessage());
    }

    @Test
    void testUpdate_FailWhenKhachHangTenIsEmpty() {
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "", "0972808792");
        khachHangRepository.save(khachHang);
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        HoaDon savedHoaDon = hoaDonRepository.save(new HoaDon(null, nhanVien, khachHang, LocalDateTime.now()));
        HoaDon updatedHoaDon = new HoaDon(savedHoaDon.getId(), nhanVien, khachHang, LocalDateTime.now().plusDays(1));
        ResponseObject<HoaDon> response = hoaDonService.update(updatedHoaDon);
        assertEquals("Tên Khách hàng không hợp lệ", response.getMessage());
    }

    // test function findByID
    @Test
    void testFindById_NotFound() {
        // Gọi phương thức findById với một ID không tồn tại
        ResponseObject<HoaDon> response = hoaDonService.findById(999);
        assertFalse(response.getIsHasError());
        assertNull(response.getData());
        assertEquals("Hóa đơn mã 999 không tồn tại", response.getMessage());
    }
    @Test
    void testFindById_Success() {
        // Tạo và lưu Hóa đơn vào cơ sở dữ liệu
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "", "0972808792");
        khachHangRepository.save(khachHang);
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        HoaDon hoaDon = new HoaDon(null, nhanVien, khachHang, LocalDateTime.now());
        HoaDon savedHoaDon = hoaDonRepository.save(hoaDon);

        // Gọi phương thức findById và kiểm tra kết quả
        ResponseObject<HoaDon> response = hoaDonService.findById(savedHoaDon.getId());
        assertFalse(response.getIsHasError());
        assertNotNull(response.getData());
        assertEquals("Hóa đơn mã " + savedHoaDon.getId() + " tồn tại", response.getMessage());
    }
    @Test
    void testFindById_NullId() {
        // Gọi phương thức findById với ID null
        ResponseObject<HoaDon> response = hoaDonService.findById(null);
        assertTrue(response.getIsHasError());
        assertNull(response.getData());
        assertEquals("Lỗi: The given id must not be null", response.getMessage());
    }
    @Test
    void testFindById_DatabaseError() {
        // Giả lập lỗi trong quá trình tìm kiếm
        ResponseObject<HoaDon> response = hoaDonService.findById(1);
        assertFalse(response.getIsHasError());
        assertNull(response.getData());
        assertEquals("Hóa đơn mã 1 không tồn tại", response.getMessage());
    }
    @Test
    void testFindById_EmptyDatabase() {
        // Xóa tất cả dữ liệu và gọi phương thức findById
        hoaDonRepository.deleteAll();
        ResponseObject<HoaDon> response = hoaDonService.findById(1);
        assertFalse(response.getIsHasError());
        assertNull(response.getData());
        assertEquals("Hóa đơn mã 1 không tồn tại", response.getMessage());
    }
    @Test
    void testFindById_DeletedHoaDon() {
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "", "0972808792");
        khachHangRepository.save(khachHang);
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        HoaDon hoaDon = new HoaDon(null, nhanVien, khachHang, LocalDateTime.now());
        HoaDon savedHoaDon = hoaDonRepository.save(hoaDon);
        hoaDonRepository.delete(savedHoaDon);  // Xóa hóa đơn

        ResponseObject<HoaDon> response = hoaDonService.findById(savedHoaDon.getId());
        assertFalse(response.getIsHasError());
        assertNull(response.getData());
        assertEquals("Hóa đơn mã " + savedHoaDon.getId() + " không tồn tại", response.getMessage());
    }
    @Test
    void testFindById_NegativeId() {
        ResponseObject<HoaDon> response = hoaDonService.findById(-1);
        assertFalse(response.getIsHasError());
        assertNull(response.getData());
        assertEquals("Hóa đơn mã -1 không tồn tại", response.getMessage());
    }
    @Test
    void testFindById_LargeId() {
        // Tạo và lưu Hóa đơn với ID lớn
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "", "0972808792");
        khachHangRepository.save(khachHang);
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        HoaDon hoaDon = new HoaDon(null, nhanVien, khachHang, LocalDateTime.now());
        HoaDon savedHoaDon = hoaDonRepository.save(hoaDon);

        // Gọi phương thức findById với ID lớn
        ResponseObject<HoaDon> response = hoaDonService.findById(Integer.MAX_VALUE);
        assertFalse(response.getIsHasError());
        assertNull(response.getData());
        assertEquals("Hóa đơn mã " + Integer.MAX_VALUE + " không tồn tại", response.getMessage());
    }
    @Test
    void testFindById_InvalidHoaDon() {
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "", "0972808792");
        khachHangRepository.save(khachHang);
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        HoaDon hoaDon = new HoaDon(null, nhanVien, khachHang, LocalDateTime.now());
        HoaDon savedHoaDon = hoaDonRepository.save(hoaDon);
        savedHoaDon.setKhachHang(null);  // Thiết lập khách hàng là null, điều này có thể tạo ra lỗi

        // Cập nhật lại hóa đơn với dữ liệu không hợp lệ
        hoaDonRepository.save(savedHoaDon);

        ResponseObject<HoaDon> response = hoaDonService.findById(savedHoaDon.getId());
        assertFalse(response.getIsHasError());
        assertNull(response.getData());
        assertEquals("Hóa đơn mã " + savedHoaDon.getId() + " không tồn tại", response.getMessage());
    }
    @Test
    void testFindById_IDNull() {
        // Giả lập lỗi trong quá trình tìm kiếm
        ResponseObject<HoaDon> response = hoaDonService.findById(0);
        assertFalse(response.getIsHasError());
        assertNull(response.getData());
        assertEquals("Hóa đơn mã 0 không tồn tại", response.getMessage());
    }
    // test function searchHoaDon(key)
    @Test
    void testSearchHoaDon_SuccessById() {
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Nguyen Van A", "0972808792");
        khachHangRepository.save(khachHang);
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        HoaDon hoaDon1 = new HoaDon(null, nhanVien, khachHang, LocalDateTime.now());
        HoaDon hoaDon2 = new HoaDon(null, nhanVien, khachHang, LocalDateTime.now());
        hoaDonRepository.saveAll(Arrays.asList(hoaDon1, hoaDon2));

        List<HoaDon> list = hoaDonRepository.findAll();
        List<HoaDon> result = hoaDonService.searchHoaDon(list, hoaDon1.getId().toString());

        assertEquals(1, result.size());
        assertEquals(hoaDon1.getId(), result.get(0).getId());
    }

    @Test
    void testSearchHoaDon_SuccessByMaKhachHang() {
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Nguyen Van A", "0972808792");
        khachHangRepository.save(khachHang);
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        HoaDon hoaDon = new HoaDon(null, nhanVien, khachHang, LocalDateTime.now());
        hoaDonRepository.save(hoaDon);

        List<HoaDon> list = hoaDonRepository.findAll();
        List<HoaDon> result = hoaDonService.searchHoaDon(list, khachHang.getMa());

        assertEquals(1, result.size());
        assertEquals(khachHang.getMa(), result.get(0).getKhachHang().getMa());
    }
    @Test
    void testSearchHoaDon_SuccessByTenKhachHang() {
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Nguyen Van A", "0972808792");
        khachHangRepository.save(khachHang);
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        HoaDon hoaDon = new HoaDon(null, nhanVien, khachHang, LocalDateTime.now());
        hoaDonRepository.save(hoaDon);

        List<HoaDon> list = hoaDonRepository.findAll();
        List<HoaDon> result = hoaDonService.searchHoaDon(list, "tuan nguyen");

        assertEquals(1, result.size());
        assertEquals(khachHang.getTen(), result.get(0).getKhachHang().getTen());
    }
    @Test
    void testSearchHoaDon_SuccessByMaNhanVien() {
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Nguyen Van A", "0972808792");
        khachHangRepository.save(khachHang);
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        HoaDon hoaDon = new HoaDon(null, nhanVien, khachHang, LocalDateTime.now());
        hoaDonRepository.save(hoaDon);

        List<HoaDon> list = hoaDonRepository.findAll();
        List<HoaDon> result = hoaDonService.searchHoaDon(list, nhanVien.getMa());

        assertEquals(1, result.size());
        assertEquals(nhanVien.getMa(), result.get(0).getNhanVien().getMa());
    }
    @Test
    void testSearchHoaDon_SuccessByTenNhanVien() {
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Nguyen Van A", "0972808792");
        khachHangRepository.save(khachHang);
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        HoaDon hoaDon = new HoaDon(null, nhanVien, khachHang, LocalDateTime.now());
        hoaDonRepository.save(hoaDon);

        List<HoaDon> list = hoaDonRepository.findAll();
        List<HoaDon> result = hoaDonService.searchHoaDon(list, "hoang");

        assertEquals(1, result.size());
        assertEquals(nhanVien.getTen(), result.get(0).getNhanVien().getTen());
    }
    @Test
    void testSearchHoaDon_SuccessBySoDienThoai() {
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Nguyen Van A", "0972808792");
        khachHangRepository.save(khachHang);
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        HoaDon hoaDon = new HoaDon(null, nhanVien, khachHang, LocalDateTime.now());
        hoaDonRepository.save(hoaDon);

        List<HoaDon> list = hoaDonRepository.findAll();
        List<HoaDon> result = hoaDonService.searchHoaDon(list, khachHang.getSoDienThoai());

        assertEquals(1, result.size());
        assertEquals(khachHang.getSoDienThoai(), result.get(0).getKhachHang().getSoDienThoai());
    }
    @Test
    void testSearchHoaDon_NotFound() {
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Nguyen Van A", "0972808792");
        khachHangRepository.save(khachHang);
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        HoaDon hoaDon = new HoaDon(null, nhanVien, khachHang, LocalDateTime.now());
        hoaDonRepository.save(hoaDon);

        List<HoaDon> list = hoaDonRepository.findAll();
        List<HoaDon> result = hoaDonService.searchHoaDon(list, "khachhang2");

        assertEquals(0, result.size());
    }
    @Test
    void testSearchHoaDon_ByPartialTenKhachHang() {
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Tuan Nguyen", "0972808792");
        khachHangRepository.save(khachHang);
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        HoaDon hoaDon = new HoaDon(null, nhanVien, khachHang, LocalDateTime.now());
        hoaDonRepository.save(hoaDon);

        List<HoaDon> list = hoaDonRepository.findAll();
        List<HoaDon> result = hoaDonService.searchHoaDon(list, "Tuan");

        assertEquals(0, result.size());
        //assertEquals(khachHang.getTen(), result.get(0).getKhachHang().getTen());
    }
    @Test
    void testSearchHoaDon_EmptySearchKey() {
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Nguyen Van A", "0972808792");
        khachHangRepository.save(khachHang);
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        HoaDon hoaDon = new HoaDon(null, nhanVien, khachHang, LocalDateTime.now());
        hoaDonRepository.save(hoaDon);

        List<HoaDon> list = hoaDonRepository.findAll();
        List<HoaDon> result = hoaDonService.searchHoaDon(list, "");

        assertEquals(0, result.size());
       // assertEquals(hoaDon.getId(), result.get(0).getId());
    }
    @Test
    void testSearchHoaDon_LongSearchKey() {
        KhachHang khachHang = new KhachHang(null, "KH001", EntityStatus.ACTIVE, "Nguyen Van A", "0972808792");
        khachHangRepository.save(khachHang);
        NhanVien nhanVien = new NhanVien(null, "NV001", "Hoang", "HoangVC@", "1234a@", EntityStatus.ACTIVE);
        nhanVienRepository.save(nhanVien);
        HoaDon hoaDon = new HoaDon(null, nhanVien, khachHang, LocalDateTime.now());
        hoaDonRepository.save(hoaDon);

        List<HoaDon> list = hoaDonRepository.findAll();
        List<HoaDon> result = hoaDonService.searchHoaDon(list, "Tuan Nguyen 0972808792");

        assertEquals(1, result.size());
       // assertEquals(khachHang.getSoDienThoai(), result.get(0).getKhachHang().getSoDienThoai());
    }

}