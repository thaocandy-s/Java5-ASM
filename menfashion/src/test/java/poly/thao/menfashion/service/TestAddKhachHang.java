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
    void add() {

        KhachHang khachHang = new KhachHang(1, "KH002", EntityStatus.ACTIVE, "miaaa", "0947123970");
        Assertions.assertEquals("Thêm KH thành công", khachHangService.add(khachHang).getMessage());
//        Assertions.assertEquals("miaaa", khachHangRepository.save(khachHang).getTen());

    }



}