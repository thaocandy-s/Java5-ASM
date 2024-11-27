package poly.thao.menfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.thao.menfashion.entity.NhanVien;
import poly.thao.menfashion.model.request.LoginReq;
import poly.thao.menfashion.model.response.ResponseObject;
import poly.thao.menfashion.repository.NhanVienRepository;

import java.util.Map;
import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    public NhanVienRepository nhanVienRepository;


    public ResponseObject<NhanVien> login(LoginReq loginReq) {
        String validate = validate(loginReq);
        if (validate != null) {
            return new ResponseObject<>(true, null, validate);
        }
        Optional<NhanVien> opUser = nhanVienRepository.findByTenDangNhapAndMatKhau(loginReq);
        NhanVien nv = opUser.orElse(null);

        ResponseObject<NhanVien> currentUser;
        if (nv == null) {
            currentUser = new ResponseObject<>(true, null, "Tài khoản không hợp lệ");
        } else {
            currentUser = new ResponseObject<>(false, nv, "Login thành công");
        }
        return currentUser;
    }

    public String validate(LoginReq e) {

        String regexTenDangNhap = "^(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,30}$";
        String regexMatKhau = "^(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{5,10}$";

        if (e.getUsername() == null || e.getPassword() == null) {
            return "Error: Tên đăng nhập & mật khẩu không is NULL";
        }

        if (!e.getUsername().matches(regexTenDangNhap)) {
            return "Tên đăng nhập cần 8-30 ký tự, phải có ít nhất 1 chữ hoa và 1 ký tự đặc biệt";
        }
        if (!e.getPassword().matches(regexMatKhau)) {
            return "Mật khẩu cần 5-10 ký tự, phải có ít nhất 1 số và 1 ký tự đặc biệt";
        }
        return null;
    }

}
