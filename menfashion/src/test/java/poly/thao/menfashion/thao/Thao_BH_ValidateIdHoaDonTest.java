package poly.thao.menfashion.thao;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import poly.thao.menfashion.controller.HoaDonController;
import poly.thao.menfashion.entity.HoaDon;
import poly.thao.menfashion.entity.HoaDonChiTiet;
import poly.thao.menfashion.model.EntityStatus;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.service.HoaDonService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class Thao_BH_ValidateIdHoaDonTest {
    @Mock
    private HoaDonService service;

    @InjectMocks
    private HoaDonController controller;

    List<HoaDon> hoaDons;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        hoaDons = Arrays.asList(
                new HoaDon(1,null, null, LocalDateTime.now(), EntityStatus.ACTIVE),
                new HoaDon(2,null, null, LocalDateTime.now(), EntityStatus.ACTIVE),
                new HoaDon(3,null, null, LocalDateTime.now(), EntityStatus.ACTIVE),
                new HoaDon(5,null, null, LocalDateTime.now(), EntityStatus.ACTIVE)
                );
    }

    @AfterEach
    void tearDown() {
        hoaDons = null;
    }
    @Test
    void testValidateIdHoaDon1() {
        int validId = 1;
        List<HoaDonChiTiet> listHDCT = new ArrayList<>();
        listHDCT.add(new HoaDonChiTiet());
        boolean isExistIdHoaDon = hoaDons.stream()
                .anyMatch(e -> e.getId().equals(validId));
        Mockito.when(service.isExistById(validId)).thenReturn(isExistIdHoaDon);
        Mockito.when(service.getListHDCTByHoaDonId(validId)).thenReturn(listHDCT);

        ResponseObject<String> response = controller.validateIdHoaDon(validId);
        assertFalse(response.isHasError);
        assertEquals("Id hoa don OK", response.getMessage());
    }

    @Test
    void testValidateIdHoaDon2() {
        int validId = 2;
        List<HoaDonChiTiet> listHDCT = new ArrayList<>();
        listHDCT.add(new HoaDonChiTiet());
        boolean isExistIdHoaDon = hoaDons.stream()
                .anyMatch(e -> e.getId().equals(validId));
        Mockito.when(service.isExistById(validId)).thenReturn(isExistIdHoaDon);
        Mockito.when(service.getListHDCTByHoaDonId(validId)).thenReturn(listHDCT);

        ResponseObject<String> response = controller.validateIdHoaDon(validId);
        assertFalse(response.isHasError);
        assertEquals("Id hoa don OK", response.getMessage());
    }

    @Test
    void testValidateIdHoaDon3_ServerError() {
        int validId = 3;
        Mockito.when(service.isExistById(validId)).thenThrow(new RuntimeException("Server error"));

        ResponseObject<String> response = controller.validateIdHoaDon(validId);
        assertTrue(response.isHasError);
        assertEquals("Server error", response.getMessage());
    }

    @Test
    void testValidateIdHoaDon4() {
        int validId = 999;
        boolean isExistIdHoaDon = hoaDons.stream()
                .anyMatch(e -> e.getId().equals(validId));
        Mockito.when(service.isExistById(validId)).thenReturn(isExistIdHoaDon);
        ResponseObject<String> response = controller.validateIdHoaDon(validId);
        assertTrue(response.isHasError);
        assertEquals("Id not found", response.getMessage());
    }
    @Test
    void testValidateIdHoaDon5_IdIsNull() {
        ResponseObject<String> response = controller.validateIdHoaDon(null);
        assertTrue(response.isHasError);
        assertEquals("Id must not null", response.getMessage());
    }

    @Test
    void testValidateIdHoaDon6_IdIsNotInteger() {
        ResponseObject<String> response = controller.validateIdHoaDon("string");
        assertTrue(response.isHasError);
        assertEquals("Id must be Integer", response.getMessage());
    }


    @Test
    void testValidateIdHoaDon7_IdIsZero() {
        ResponseObject<String> response = controller.validateIdHoaDon(0);
        assertTrue(response.isHasError);
        assertEquals("Invalid Id", response.getMessage());
    }

    @Test
    void testValidateIdHoaDon8_IdIsGreaterThanMaxInt() {
        Long largeId = 2147483648L;
        ResponseObject<String> response = controller.validateIdHoaDon(largeId);
        assertTrue(response.isHasError);
        assertEquals("Invalid Id", response.getMessage());
    }


    @Test
    void testValidateIdHoaDon9_IdIsNegative() {
        ResponseObject<String> response = controller.validateIdHoaDon(-123);
        assertTrue(response.isHasError);
        assertEquals("Invalid Id", response.getMessage());
    }

    @Test
    void testValidateIdHoaDon10_IdIsValidButListHDCTEmpty() {
        int validId = 5;
        boolean isExistIdHoaDon = hoaDons.stream()
                .anyMatch(e -> e.getId().equals(validId));
        Mockito.when(service.isExistById(validId)).thenReturn(isExistIdHoaDon);
        Mockito.when(service.getListHDCTByHoaDonId(validId)).thenReturn(Collections.emptyList());

        ResponseObject<String> response = controller.validateIdHoaDon(validId);
        assertTrue(response.isHasError);
        assertEquals("list HDCT empty", response.getMessage());
    }


}
