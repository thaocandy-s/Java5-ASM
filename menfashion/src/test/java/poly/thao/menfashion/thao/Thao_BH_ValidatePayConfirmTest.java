package poly.thao.menfashion.thao;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import poly.thao.menfashion.controller.BanHangController;
import poly.thao.menfashion.entity.KhachHang;
import poly.thao.menfashion.entity.SanPhamChiTiet;
import poly.thao.menfashion.model.request.Cart;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.model.response.ValidationResult;
import poly.thao.menfashion.service.KhachHangService;
import poly.thao.menfashion.service.SanPhamChiTietService;

import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class Thao_BH_ValidatePayConfirmTest {

    @Mock
    private SanPhamChiTietService sanPhamChiTietService;

    @Mock
    private KhachHangService khachHangService;

    @InjectMocks
    private BanHangController controller;

    private Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart();
    }

    @Test
    void validatePayConfirm1_success() {
        String[] selectedSP = {"1", "2"};
        Map<String, String> allParams = Map.of(
                "soLuongs[1]", "5",
                "soLuongs[2]", "3",
                "idKhachHang", "101"
        );
        Mockito.when(sanPhamChiTietService.findById(1))
                .thenReturn(new ResponseObject<>(false, new SanPhamChiTiet(1, 10), ""));
        Mockito.when(sanPhamChiTietService.findById(2))
                .thenReturn(new ResponseObject<>(false, new SanPhamChiTiet(2, 5), ""));
        Mockito.when(khachHangService.findById(101))
                .thenReturn(new ResponseObject<>(false, new KhachHang(101,"khach hang 101", "08472427445"), ""));
        ValidationResult result = controller.validatePayConfirm(selectedSP, allParams, cart, sanPhamChiTietService);

        Assertions.assertTrue(result.isValid());
        Assertions.assertNull(result.getMessage());
    }


    @Test
    void validatePayConfirm2_noProductsSelected() {
        String[] selectedSP = {};
        Map<String, String> allParams = Map.of("idKhachHang", "101");
        ValidationResult result = controller.validatePayConfirm(selectedSP, allParams, cart, sanPhamChiTietService);

        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals("Vui lòng chọn sản phẩm!", result.getMessage());
    }

    @Test
    void validatePayConfirm3_noQuantityProvided() {
        String[] selectedSP = {"1"};
        Map<String, String> allParams = Map.of("idKhachHang", "101");
        Mockito.when(sanPhamChiTietService.findById(1))
                .thenReturn(new ResponseObject<>(false, new SanPhamChiTiet(1, 10), ""));
        ValidationResult result = controller.validatePayConfirm(selectedSP, allParams, cart, sanPhamChiTietService);

        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals("Số lượng must not null!", result.getMessage());
    }

    @Test
    void validatePayConfirm4_noCustomerSelected() {
        String[] selectedSP = {"1"};
        Map<String, String> allParams = Map.of("soLuongs[1]", "5");
        Mockito.when(sanPhamChiTietService.findById(1))
                .thenReturn(new ResponseObject<>(false, new SanPhamChiTiet(1, 10), ""));
        ValidationResult result = controller.validatePayConfirm(selectedSP, allParams, cart, sanPhamChiTietService);

        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals("Vui lòng chọn khách hàng!", result.getMessage());
    }

    @Test
    void validatePayConfirm5_NoInfo() {
        String[] selectedSP = {};
        Map<String, String> allParams = Map.of();
        Mockito.when(sanPhamChiTietService.findById(1))
                .thenReturn(new ResponseObject<>(false, new SanPhamChiTiet(1, 10), ""));
        ValidationResult result = controller.validatePayConfirm(selectedSP, allParams, cart, sanPhamChiTietService);
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals("Vui lòng chọn sản phẩm!", result.getMessage());
    }

    @Test
    void validatePayConfirm6_invalidProductId() {
        String[] selectedSP = {"999"};
        Map<String, String> allParams = Map.of("soLuongs[999]", "5", "idKhachHang", "101");
        Mockito.when(sanPhamChiTietService.findById(999))
                .thenReturn(new ResponseObject<>(true, null, "SPCT not found"));
        ValidationResult result = controller.validatePayConfirm(selectedSP, allParams, cart, sanPhamChiTietService);

        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals("SPCT not found!", result.getMessage());
    }

    @Test
    void validatePayConfirm7_invalidCustomerId() {
        String[] selectedSP = {"1"};
        Map<String, String> allParams = Map.of("soLuongs[1]", "5", "idKhachHang", "999");
        Mockito.when(sanPhamChiTietService.findById(1))
                .thenReturn(new ResponseObject<>(false, new SanPhamChiTiet(1, 10), ""));
        Mockito.when(khachHangService.findById(999))
                .thenReturn(new ResponseObject<>(true, null, "KH not found"));
        ValidationResult result = controller.validatePayConfirm(selectedSP, allParams, cart, sanPhamChiTietService);

        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals("KH not found!", result.getMessage());
    }


    @Test
    void validatePayConfirm8_negativeQuantity() {
        String[] selectedSP = {"1"};
        Map<String, String> allParams = Map.of("soLuongs[1]", "-5", "idKhachHang", "101");
        Mockito.when(sanPhamChiTietService.findById(1))
                .thenReturn(new ResponseObject<>(false, new SanPhamChiTiet(1, 10), ""));
        ValidationResult result = controller.validatePayConfirm(selectedSP, allParams, cart, sanPhamChiTietService);
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals("Số lượng nhập không hợp lệ, vui lòng nhập lại!", result.getMessage());
    }

    @Test
    void validatePayConfirm9_quantityUnAvailableStock() {
        String[] selectedSP = {"1"};
        Map<String, String> allParams = Map.of("soLuongs[1]", "15", "idKhachHang", "101");
        Mockito.when(sanPhamChiTietService.findById(1))
                .thenReturn(new ResponseObject<>(false, new SanPhamChiTiet(1, 10), ""));
        ValidationResult result = controller.validatePayConfirm(selectedSP, allParams, cart, sanPhamChiTietService);

        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals("Số lượng nhập không hợp lệ, vui lòng nhập lại!", result.getMessage());
    }

    @Test
    void validatePayConfirm10_quantityUnAvailableStock() {
        String[] selectedSP = {"1"};
        Map<String, String> allParams = Map.of("soLuongs[1]", "string", "idKhachHang", "101");
        Mockito.when(sanPhamChiTietService.findById(1))
                .thenReturn(new ResponseObject<>(false, new SanPhamChiTiet(1, 10), ""));

        Assertions.assertThrows(NumberFormatException.class, () -> {
            controller.validatePayConfirm(selectedSP, allParams, cart, sanPhamChiTietService);
        });
    }


}
