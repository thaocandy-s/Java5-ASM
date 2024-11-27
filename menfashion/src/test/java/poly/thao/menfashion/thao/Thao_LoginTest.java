package poly.thao.menfashion.thao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.model.request.LoginReq;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.service.LoginService;

@SpringBootTest
class Thao_LoginTest {

    @Autowired
    private LoginService loginService;
    private LoginReq loginReq;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        loginReq = null;
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/thao/loginTest.csv", numLinesToSkip = 1)
    void login(String username, String password, Boolean expectIsHasError,String expectMess) {
        loginReq = new LoginReq(username, password);
        ResponseObject<NhanVien> reOb = loginService.login(loginReq);
        Assertions.assertEquals(expectIsHasError, reOb.isHasError);
        Assertions.assertEquals(expectMess, reOb.getMessage());
    }

}