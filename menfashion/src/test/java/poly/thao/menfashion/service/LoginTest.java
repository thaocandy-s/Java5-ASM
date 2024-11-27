package poly.thao.menfashion.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.model.request.LoginReq;
import poly.thao.menfashion.repository.NhanVienRepository;

@ExtendWith(MockitoExtension.class)
class LoginTest {

    @Mock
    private NhanVienRepository nhanVienRepository;

    @InjectMocks
    private LoginService loginService;

    private LoginReq loginReq;
    private NhanVien nhanVien;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void login() {
    }
}